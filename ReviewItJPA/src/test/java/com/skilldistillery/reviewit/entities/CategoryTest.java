package com.skilldistillery.reviewit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class CategoryTest extends EntityTest {

	@Test
	void test_category_name_mapping() throws Exception {
		Category category = em.find(Category.class, 1);
		assertNotNull(category);
		assertEquals("Food And Beverage", category.getName());
	}

	@Test
	void test_category_products_mapping() throws Exception {
		Category category = em.find(Category.class, 1);
		assertNotNull(category);
		assertNotNull(category.getProducts());
		assertFalse(category.getProducts().isEmpty());
	}

}
