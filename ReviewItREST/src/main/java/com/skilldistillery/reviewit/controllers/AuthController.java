package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.services.AuthService;
import com.skilldistillery.reviewit.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping("api/auth")
public class AuthController extends BaseController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public AuthToken login(@RequestBody User user, HttpServletResponse res) {
		return tryFailableAction(() -> {

			return authService.authenticate(user.getUsername(), user.getPassword());

		}, res);
	}

	@PostMapping("/signup")
	public AuthToken signup(@RequestBody User user, HttpServletResponse res) {
		return tryFailableAction(() -> {

			user.setRole("user");
			user.setPassword(authService.encryptPassword(user.getPassword()));
			userService.createUser(user);
			return authService.authenticate(user.getUsername(), user.getPassword());

		}, res);
	}

}
