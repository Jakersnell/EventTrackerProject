package com.skilldistillery.reviewit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.repositories.CategoryRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CategoryWebIntegrationTest {

	private static String url = "/api/categories";

	private static String adminAuthToken = "admin-auth-token";

	@Autowired
	private TestRestTemplate restTest;

	@Autowired
	private CategoryRepository catRepo;

	private Category newCat;

	void makeCategory() {
		newCat = new Category();
		newCat.setName(Math.random() + "-TEST-CATEGORY-" + Math.random()); // random to help prevent name collision
		newCat = catRepo.save(newCat);
	}

	void deleteCategory() {
		catRepo.delete(newCat);
		newCat = null;
	}

	@Test
	void test_get_all_returns_non_null_non_empty_list() {

		@SuppressWarnings("rawtypes")
		ResponseEntity<List> entity = restTest.getForEntity(url, List.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		@SuppressWarnings("unchecked")
		List<Category> categories = entity.getBody();
		assertNotNull(categories);
		assertFalse(categories.isEmpty());
	}

	@Test
	void test_get_category_by_id_returns_valid_result_for_valid_input() {
		ResponseEntity<Category> entity = restTest.getForEntity(url + "/1", Category.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());

		Category category = entity.getBody();
		assertNotNull(category);
		assertEquals("Food", category.getName());
	}

	@Test
	void test_disable_category_by_id_with_auth_is_correct() {
		makeCategory();
		restTest.delete(url + "/" + newCat.getId() + "?auth=" + adminAuthToken);
		newCat = catRepo.findById(newCat.getId()).get();
		newCat.getClass();
		assertFalse(newCat.isEnabled());
		deleteCategory();
	}

	@Test
	void test_disable_category_by_id_with_no_auth_does_not_disable_category() {
		makeCategory();
		restTest.delete(url + "/" + newCat.getId());
		newCat = catRepo.findById(newCat.getId()).get();
		assertTrue(newCat.isEnabled());
		deleteCategory();
	}
	
	@Test
	void test_update_category_with_admin_auth_token_updates_category_name() {
		makeCategory();
		String newName = Math.random() + "_UPDATED_CATEGORY_" + Math.random();
		newCat.setName(newName);
		restTest.put(url + "/" + newCat.getId() + "?auth=" + adminAuthToken, newCat);
		newCat = catRepo.findById(newCat.getId()).get();
		assertEquals(newName, newCat.getName());
		deleteCategory();
	}
}
