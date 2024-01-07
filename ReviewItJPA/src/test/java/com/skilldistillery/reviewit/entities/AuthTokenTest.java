package com.skilldistillery.reviewit.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AuthTokenTest extends EntityTest {

	@Test
	void test_token_is_correct_for_AuthToken_of_valid_id() {
		AuthToken auth = em.find(AuthToken.class, 1);
		assertNotNull(auth);
		String controlToken = "503233fd-2690-4c35-a176-5905727484c9";
		assertEquals(controlToken, auth.getToken());
	}

}
