package com.skilldistillery.reviewit.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ProductReviewTest extends EntityTest {
	@Test
	void test_rating_mapping_for_id_of_1() throws Exception {
		ProductReview review = em.find(ProductReview.class, 1);
		assertNotNull(review);
		assertEquals(0, review.getRating());
	}

	@Test
	void test_content_is_not_null_and_not_empty() throws Exception {
		ProductReview review = em.find(ProductReview.class, 1);
		assertNotNull(review);
		assertNotNull(review.getContent());
		assertFalse(review.getContent().isBlank());
	}

	@Test
	void test_createdOn_is_not_null() throws Exception {
		ProductReview review = em.find(ProductReview.class, 1);
		assertNotNull(review);
		assertNotNull(review.getCreatedOn());
	}

	@Test
	void test_lastUpdated_is_not_null() throws Exception {
		ProductReview review = em.find(ProductReview.class, 1);
		assertNotNull(review);
		assertNotNull(review.getLastUpdated());
	}
	
	@Test
	void test_product_mapping_is_correct() throws Exception {
		ProductReview review = em.find(ProductReview.class, 1);
		assertNotNull(review);
		Product product = review.getProduct();
		assertNotNull(product);
		assertEquals(1, product.getId());
	}
	
	@Test
	void test_user_mapping_is_correct() throws Exception {
		ProductReview review = em.find(ProductReview.class, 1);
		assertNotNull(review);
		User user = review.getUser();
		assertNotNull(user);
		assertEquals(1, user.getId());
	}

}
