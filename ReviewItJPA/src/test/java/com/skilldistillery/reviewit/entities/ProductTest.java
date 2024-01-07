package com.skilldistillery.reviewit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

class ProductTest extends EntityTest {
	@Test
	void test_name_mapping() {
		Product product = em.find(Product.class, 1);
		assertNotNull(product);
		assertEquals("Monster Energy Drink", product.getName());
	}

	@Test
	void test_description_is_not_empty_not_null() throws Exception {
		Product product = em.find(Product.class, 1);
		assertNotNull(product);
		String description = product.getDescription();
		assertNotNull(description);
		assertFalse(description.isBlank());
	}

	@Test
	void test_reviews_is_not_null_not_empty() {
		Product product = em.find(Product.class, 1);
		assertNotNull(product);
		List<ProductReview> reviews = product.getReviews();
		assertNotNull(reviews);
		assertFalse(reviews.isEmpty());
	}
}
