package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.services.AuthenticationService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping("api/auth")
public class AuthController extends BaseController {

	@Autowired
	private AuthenticationService authService;

	@PostMapping("/signup")
	public UserDTO signup(@RequestBody UserDTO userDto, HttpServletResponse res) {
		userDto = authService.register(userDto);
		userDto.setPassword(null);
		userDto.setPassword2(null);
		return userDto;
	}

}
