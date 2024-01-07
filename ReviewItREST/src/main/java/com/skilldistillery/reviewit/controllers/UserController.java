package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.services.AuthService;
import com.skilldistillery.reviewit.services.UserService;
import com.skilldistillery.reviewit.util.TokenInvalidException;
import com.skilldistillery.reviewit.util.UserDoesNotExistException;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({ "api/users" }) // NOTE: create user is in AuthController as /api/auth/signup
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@GetMapping({ "/{userId}" })
	private User getUserById(@PathVariable("userId") int userId,
			@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {
		User user;
		try {
			user = userService.getUserById(userId);
			if (user == null) {
				throw new UserDoesNotExistException();
			}
			if (!user.isEnabled()) {
				AuthToken token = authService.getByToken(auth);
				if (!(token.isEnabled() && token.getUser().isAdmin())) {
					throw new TokenInvalidException();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(404);
			user = null;
		}
		return user;
	}

	@PutMapping({ "/{userId}" })
	private User updateUser(@PathVariable("userId") int userId, @RequestParam(name = "auth") String auth,
			@RequestBody User user, HttpServletResponse res) {
		try {
			if (authService.canEditUser(userId, auth)) {
				user = userService.updateUserDetails(userId, user);
			} else {
				throw new TokenInvalidException();
			}
		} catch (Exception e) {
			e.printStackTrace();

			if (e instanceof UserDoesNotExistException) {
				res.setStatus(404);
			} else if (e instanceof TokenInvalidException) {
				res.setStatus(401);
			} else {
				res.setStatus(400);
			}
			user = null;
		}

		return user;
	}

	@DeleteMapping({ "/{userId}" })
	private void deleteUser(@PathVariable("userId") int userId, @RequestParam(name = "auth") String auth,
			HttpServletResponse res) {
		try {
			if (!authService.canEditUser(userId, auth)) {
				User user = new User();
				user.setEnabled(false);
				userService.updateUserStatuses(userId, user);
				res.setStatus(204);
			} else {
				throw new TokenInvalidException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof UserDoesNotExistException) {
				res.setStatus(404);
			} else if (e instanceof TokenInvalidException) {
				res.setStatus(401);
			} else {
				res.setStatus(400);
			}
		}
	}

}
