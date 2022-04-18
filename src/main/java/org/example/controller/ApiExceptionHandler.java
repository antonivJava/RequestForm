package org.example.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.extern.slf4j.Slf4j;
import org.example.exceptions.ErrorResponse;
import org.example.exceptions.ItemNotFoundClientException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for handles exceptions.
 */
@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler({Exception.class})
    public final ResponseEntity<ErrorResponse> handleInternalServerError(Exception exception, HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        final String timestamp = Instant.now().toString();
        final int status = httpStatus.value();
        final String message = exception.getMessage();
        final String path = request.getServletPath();
        final String method = request.getMethod();
        ErrorResponse errorResponse = new ErrorResponse(message, timestamp, status, path);
        log.error("Request '{} {}' failed with status '{}'", method, path, httpStatus, exception);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            ServletRequestBindingException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class,
            ConstraintViolationException.class,
            HttpMessageNotReadableException.class,
            HttpMessageConversionException.class,
            JsonMappingException.class,
            NumberFormatException.class,
            InvalidDefinitionException.class,
            ItemNotFoundClientException.class
    })
    public final ResponseEntity<ErrorResponse> handleBadRequests(Exception exception, HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final String timestamp = Instant.now().toString();
        final int status = httpStatus.value();
        final String path = request.getServletPath();
        final String method = request.getMethod();
        final String message = extractMessage(exception);
        ErrorResponse errorResponse = new ErrorResponse(message, timestamp, status, path);
        log.warn("Request '{} {}' failed with status '{}'. Reason: '{}'", method, path, httpStatus, message);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    private String extractMessage(Exception exception) {
        String message = exception.getMessage();
        if (exception instanceof MethodArgumentNotValidException) {
            message = toCombinedMessage((MethodArgumentNotValidException) exception);
        } else if (exception instanceof ConstraintViolationException) {
            message = toCombinedMessage((ConstraintViolationException) exception);
        } else if (exception instanceof MethodArgumentTypeMismatchException) {
            message = String.format("'%s' argument type is mismatched. Type '%s' is required",
                    ((MethodArgumentTypeMismatchException) exception).getName(),
                    ((MethodArgumentTypeMismatchException) exception).getRequiredType()
            );
        }
        return message;
    }

    private String toCombinedMessage(MethodArgumentNotValidException exception) {
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        return errors.size() == 1 ? errors.get(0).getDefaultMessage() : errors.stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; ", "Several errors found: [", "]"));
    }

    private String toCombinedMessage(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violation = exception.getConstraintViolations();

        return violation.size() == 1 ? violation.iterator().next().getMessage() : violation.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; ", "Several errors found: [", "]"));
    }
}
