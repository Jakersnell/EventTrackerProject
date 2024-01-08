package com.skilldistillery.reviewit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.services.CategoryService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping({ "api" })
@RestController
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping({ "categories" })
	private List<Category> getAll(@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return categoryService.getAll(auth);
		}, res);

	}

	@GetMapping({ "categories/{id}" })
	private Category getById(@PathVariable("id") int id, @RequestParam(name = "auth", required = false) String auth,
			HttpServletResponse res) {

		return tryFailableAction(() -> {
			return categoryService.getCategoryById(id, auth);

		}, res);

	}

	@PostMapping("categories")
	private Category createCategory(@RequestBody Category category, @RequestParam("auth") String auth,
			HttpServletResponse res) {

		return tryFailableAction(() -> {
			return categoryService.createCategory(category, auth);
		}, res);

	}

	@DeleteMapping({ "categories/{id}" })
	private void deleteCategory(@PathVariable("id") int id, @RequestParam("auth") String auth,
			HttpServletResponse res) {
		
		tryFailableAction(() -> {
			categoryService.disableCategory(id, auth);
		}, res);

	}

	@PutMapping({ "categories/{id}" })
	private Category updateCategory(@PathVariable("id") int id, @RequestParam("auth") String auth,
			@RequestBody Category category, HttpServletResponse res) {
		
		return tryFailableAction(() -> {
			return categoryService.updateCategory(id, category, auth);
		}, res);
		
	}

}
