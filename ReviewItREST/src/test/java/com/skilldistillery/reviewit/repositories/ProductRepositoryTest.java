package com.skilldistillery.reviewit.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.skilldistillery.reviewit.entities.Product;

@SpringBootTest
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepo;

	@Test
	void test_pageByPopularitys_sorts_DESC_correctly() {
		Pageable pageReq = PageRequest.of(0, 5);
		Page<Product> page = productRepo.getPage(pageReq, "POPULARITY", "DESC", null);
		assertNotNull(page);
		assertEquals(3, page.iterator().next().getId());
	}

//	@Test
//	void test_pageByPopularity_sorts_ASC_correctly() {
//		Pageable pageReq = PageRequest.of(0, 5);
//		Page<Product> page = productRepo.getPage(pageReq, "POPULARITY", "ASC", null);
//		assertNotNull(page);
//		assertEquals(11, page.iterator().next().getId());
//	}
//
//	@Test
//	void test_pageByMostReviews_orders_by_DESC_correctly() {
//		Pageable pageReq = PageRequest.of(0, 5);
//		Page<Product> page = productRepo.getPage(pageReq, "M_REVIEWS", "DESC", null);
//		assertNotNull(page);
//		assertEquals(3, page.iterator().next().getId());
//	}
//
//	@Test
//	void test_pageByMostReviews_orders_by_ASC_correctly() {
//		Pageable pageReq = PageRequest.of(0, 5);
//		Page<Product> page = productRepo.getPage(pageReq, "M_REVIEWS", "ASC", null);
//		assertNotNull(page);
//		assertEquals(11, page.iterator().next().getId());
//	}

}
