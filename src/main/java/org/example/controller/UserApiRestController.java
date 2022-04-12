package org.example.controller;

import org.example.model.User;
import org.example.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@org.springframework.stereotype.Controller
public class UserApiRestController {
	@Autowired
	private Service service;
	
	@GetMapping
	public String showForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);

		List<String> requests = service.getAll();
		model.addAttribute("requests", requests);
		
		return "request";
	}

	@PostMapping
	public String submitForm(@ModelAttribute("user") User user) {
		service.saveUser(user);
		return "success";
	}
}
