package com.skilldistillery.reviewit.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepo;


	@Test
	void test_existsByUsernameAndPassword_returns_true_for_valid_username_and_password() {
		String username = "joeschmoe11";
		String password = "password123";
		boolean result = userRepo.existsByUsernameAndPassword(username, password);
		assertTrue(result);
	}

}
