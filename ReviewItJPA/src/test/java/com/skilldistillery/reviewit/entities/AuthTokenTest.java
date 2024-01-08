package com.skilldistillery.reviewit.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthTokenTest extends EntityTest {

	@Test
	void test_token_is_correct_for_AuthToken_of_valid_id() {
		AuthToken auth = em.find(AuthToken.class, 1);
		assertNotNull(auth);
		String controlToken = "a";
		assertEquals(controlToken, auth.getToken());
	}

}
