package com.skilldistillery.reviewit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.skilldistillery.reviewit.entities.Product;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ProductWebIntegegrationTest {

	private static String url = "/api/products";

	@Autowired
	private TestRestTemplate restTest;

	@Test
	void test_getProductById_returns_valid_entity_for_valid_id() {
		int id = 1;
		ResponseEntity<Product> entity = restTest.getForEntity(url + "/" + id, Product.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		Product product = entity.getBody();
		assertEquals("Monster Energy Drink", product.getName());
	}
	
	@Test
	void test_createProduct_returns_200_for_valid_product_entity() {
		Product testProduct = new Product();
		testProduct.setName("TEST PRODUCT DUMMY " + (int)(Math.random() * 100000.0));
		ResponseEntity<Product> response = restTest.postForEntity(url, testProduct, Product.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().getId() != 0);
	}

}
