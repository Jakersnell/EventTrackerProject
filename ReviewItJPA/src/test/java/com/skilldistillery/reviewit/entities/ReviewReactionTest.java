package com.skilldistillery.reviewit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class ReviewReactionTest extends EntityTest {
	@Test
	void test_isLike_is_valid_for_valid_entry() throws Exception {
		ReviewReactionId id = new ReviewReactionId(1, 2);
		ReviewReaction reaction = em.find(ReviewReaction.class, id);
		assertNotNull(reaction);
		assertEquals(true, reaction.isLike());
	}
}
