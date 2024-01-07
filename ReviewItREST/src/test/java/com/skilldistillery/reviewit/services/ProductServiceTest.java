package com.skilldistillery.reviewit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.skilldistillery.reviewit.entities.Product;

@SpringBootTest
public class ProductServiceTest {
	
	@Autowired
	private ProductService productService;
	
	@Test
	void test_find_by_id_returns_valid_object() throws Exception {
		Product product = productService.getProductById(1);
		assertNotNull(product);
		assertEquals("Monster Energy Drink", product.getName());
	}

}
