package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping({ "api" }) // NOTE: create user is in AuthController as /api/auth/signup
public class UserController extends BaseController {
	@Autowired
	private UserService userService;
	

	@GetMapping({ "users/{userId}" })
	private User getUserById(@PathVariable("userId") int userId,
			@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {
		
		return tryFailableAction(() -> {
			return userService.getUserById(userId, auth);
		}, res);
		
	}

	@PutMapping({ "users/{userId}" })
	private User updateUser(@PathVariable("userId") int userId, @RequestParam(name = "auth") String auth,
			@RequestBody User user, HttpServletResponse res) {
		
		return tryFailableAction(()-> {
			return userService.updateUser(userId, user, auth);
		}, res);
		
	}

	@DeleteMapping({ "users/{userId}" })
	private void disableUser(@PathVariable("userId") int userId, @RequestParam(name = "auth") String auth,
			HttpServletResponse res) {
		
		tryFailableAction(()->{
			User user = userService.getUserById(userId, auth);
			user.setEnabled(false);
			userService.updateUser(userId, user, auth);
		}, res);
		
	}
	

}
