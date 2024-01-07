package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.services.AuthService;
import com.skilldistillery.reviewit.services.UserService;
import com.skilldistillery.reviewit.util.UserDoesNotExistException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public AuthToken login(@RequestBody User user, HttpServletResponse res) {
		AuthToken token = null;
		try {
			token = authService.authenticate(user.getUsername(), user.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof UserDoesNotExistException) {
				res.setStatus(401);
			} else {
				res.setStatus(400);
			}
		}

		return token;
	}

	@PostMapping("/signup")
	public AuthToken signup(@RequestBody User user, HttpServletResponse res) {
		AuthToken token = null;
		try {
			user = userService.createUser(user);
			token = authService.authenticate(user.getUsername(), user.getPassword());
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
		}
		return token;
	}

}
