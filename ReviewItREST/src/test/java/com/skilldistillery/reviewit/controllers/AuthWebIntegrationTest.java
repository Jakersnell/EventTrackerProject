package com.skilldistillery.reviewit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.repositories.AuthTokenRepository;
import com.skilldistillery.reviewit.repositories.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthWebIntegrationTest {

	private static final String url = "/api/auth";

	@Autowired
	private TestRestTemplate restTest;

	@Autowired
	private AuthTokenRepository authRepo;

	@Autowired
	private UserRepository userRepo;

	private User testUser = null;


	@Test
	void test_login_is_success_for_valid_user() {
		User loginUser = new User();
		loginUser.setUsername("joeschmoe11");
		loginUser.setPassword("password123");
		ResponseEntity<AuthToken> response = restTest.postForEntity(url + "/login", loginUser, AuthToken.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		AuthToken token = response.getBody();

		assertEquals(1, token.getUser().getId());
		authRepo.delete(token);
	}

	@Test
	void test_login_is_failure_for_invalid_user() {
		User loginUser = new User();
		loginUser.setPassword("password123");
		ResponseEntity<AuthToken> response = restTest.postForEntity(url + "/login", loginUser, AuthToken.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void test_signup_is_success_for_valid_user() {
		String username = Math.random() + "username" + Math.random();
		String password = Math.random() + "password" + Math.random();
		String email = Math.random() + "email" + Math.random();

		testUser = new User();
		testUser.setUsername(username);
		testUser.setPassword(password);
		testUser.setEmail(email);

		ResponseEntity<AuthToken> response = restTest.postForEntity(url + "/signup", testUser, AuthToken.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		AuthToken token = response.getBody();
		testUser = userRepo.findByUsernameAndPassword(username, password).get();
		assertEquals(token.getUser(), testUser);
	}
}
