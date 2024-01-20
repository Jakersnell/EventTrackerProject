package com.skilldistillery.reviewit.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.repositories.ProductRepository.PageSort;

@SpringBootTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepo;

	@Test
	void test_getPage_returns_non_empty_page_for_valid_result() throws Exception {
		Pageable pageable = PageRequest.of(0, 2, PageSort.byPopularity());
		Page<Product> products = productRepo.getPage(null, null, null, pageable);
		assertNotNull(products);
		assertFalse(products.isEmpty());
	}
}
