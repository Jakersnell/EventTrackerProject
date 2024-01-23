package com.skilldistillery.reviewit.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.services.AuthenticationService;

import jakarta.validation.Valid;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping("auth")
public class AuthController {

	@Autowired
	private AuthenticationService authService;

	/// POST /auth/register, Body: User{**}, Response: User, Errors: 400
	@PostMapping("register")
	public ResponseEntity<UserDTO> signup(@Valid @RequestBody UserDTO userDto) throws DuplicateEntityException {
		userDto = authService.register(userDto);
		return ResponseEntity.ok(userDto);
	}

	/// GET /auth/authorize, Auth: Basic b64 encoded, Response User
	@GetMapping("/authorize")
	public ResponseEntity<UserDTO> authorize(Principal principal) throws EntityDoesNotExistException {
		UserDTO userDto = authService.getUserByUsername(principal.getName());
		return ResponseEntity.ok(userDto);
	}

}
