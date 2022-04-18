package org.example.model;

import lombok.*;
import org.example.repository.entries.RequestEntity;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Request {
	@NotEmpty(message = "'requestType' is missed or empty")
	private String requestType;

	@NotEmpty(message = "'policyNumber' is missed or empty")
	@Pattern(regexp = "[A-Za-z0-9]+", message = "'policyNumber' must contain only letters and digits")
	private String policyNumber;

	@NotEmpty(message = "'name' is missed or empty")
    @Pattern(regexp = "[A-Za-z]+", message = "'name' must contain only letters")
	private String name;

	@NotEmpty(message = "'surname' is missed or empty")
    @Pattern(regexp = "^[A-Za-z]+", message = "'surname' must contain only letters")
	private String surname;

	@NotEmpty(message = "'note' is missed or empty")
	@Size(max = 5000, message = "'note' must be no more than 5000 characters")
	private String note;

	public Request(RequestEntity entity) {
		requestType = entity.getRequestType();
		policyNumber = entity.getPolicyNumber();
		name = entity.getName();
		surname = entity.getSurname();
		note = entity.getNote();
	}
}
