package com.skilldistillery.reviewit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class UserTest extends EntityTest {
	@Test
	void test_username_mapping_for_valid_user() {
		User user = em.find(User.class, 1);
		assertNotNull(user);
		assertEquals("joeschmoe11", user.getUsername());
	}

	@Test
	void test_reviews_is_not_null_not_empty() throws Exception {
		User user = em.find(User.class, 1);
		assertNotNull(user);
		List<ProductReview> reviews = user.getReviews();
		assertNotNull(reviews);
		assertFalse(reviews.isEmpty());
	}

}
