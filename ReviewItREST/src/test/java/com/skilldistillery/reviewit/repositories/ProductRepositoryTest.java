package com.skilldistillery.reviewit.repositories;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.repositories.ProductRepository.PageSort;

@SpringBootTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepo;

	@Test
	void testName() throws Exception {
		Pageable pageable = PageRequest.of(0, 10, PageSort.byPopularity().descending());
		Page<Product> products = productRepo.getPage("a", null, null, null, pageable);
		assertNotNull(products);
		assertFalse(products.isEmpty());
		ObjectMapper mapr = new ObjectMapper();
		
		System.out.println("\n\n\n");
		try {
			String json = mapr.writeValueAsString(products);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("\n\n\n");
	}
}
