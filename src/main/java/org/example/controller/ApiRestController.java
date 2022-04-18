package org.example.controller;

import org.example.exceptions.ItemNotFoundClientException;
import org.example.model.Request;
import org.example.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

/**
 * This class handles client's requests. To save user's request not from browser please use POST request to "/request" endpoint.
 * When application starts database will be initialize with request types from migration sql script (Flyway migration tool).
 */
@Controller
public class ApiRestController {
	@Autowired
	private RequestService service;

	@GetMapping
	public String showForm(Request request, Model model) {
		List<String> requests = service.getRequestTypes();
		model.addAttribute("requests", requests);
		return "request";
	}

	@PostMapping
	public String submitForm(@Valid Request request, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			List<String> requests = service.getRequestTypes();
			model.addAttribute("requests", requests);
			return "request";
		}
		service.saveRequest(request);
		return "success";
	}

	@PostMapping("/request")
	public ResponseEntity<Request> saveRequest(@Valid @RequestBody Request request) {
		if (!service.getRequestTypes().contains(request.getRequestType())) {
			throw new ItemNotFoundClientException(String.format("Incorrect 'requestType'=%s", request.getRequestType()));
		}
		return new ResponseEntity<>(service.saveRequest(request), HttpStatus.OK);
	}
}
