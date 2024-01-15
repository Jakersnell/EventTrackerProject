package com.skilldistillery.reviewit.repositories;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.skilldistillery.reviewit.entities.Category;

@SpringBootTest
class CategoryRepositoryTest {
	
	@Autowired
	private CategoryRepository catRepo;
	
	@Test
	void test_getByProductsId_returns_valid_list_for_valid_id() {
		List<Category> categories = catRepo.getByProductsId(1);
		assertFalse(categories.isEmpty());
	}

}
