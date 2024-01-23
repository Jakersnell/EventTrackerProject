package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.services.AuthenticationService;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping("api/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping("/signup")
	public ResponseEntity<UserDTO> signup(@RequestBody UserDTO userDto) {
		userDto = authService.register(userDto);
		return ResponseEntity.ok(userDto);
	}

}
