package com.skilldistillery.reviewit.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.services.CategoryService;
import com.skilldistillery.reviewit.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping({ "api" })
@RestController
public class CategoryController extends BaseController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private ProductService productService;

	@GetMapping({ "categories" })
	private List<Category> getAll(@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return categoryService.getAll(auth);
		}, res);

	}

	@GetMapping({ "categories/{categoryId}" })
	private Category getById(@PathVariable("categoryId") int categoryId,
			@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return categoryService.getCategoryById(categoryId, auth);

		}, res);

	}

	@PostMapping("categories")
	private Category createCategory(@RequestBody Category category, @RequestParam("auth") String auth,
			HttpServletResponse res) {

		return tryFailableAction(() -> {
			return categoryService.createCategory(category, auth);
		}, res);

	}

	@DeleteMapping({ "categories/{categoryId}" })
	private void deleteCategory(@PathVariable("categoryId") int categoryId, @RequestParam("auth") String auth,
			HttpServletResponse res) {

		tryFailableAction(() -> {
			categoryService.disableCategory(categoryId, auth);
		}, res);

	}

	@PutMapping({ "categories/{categoryId}" })
	private Category updateCategory(@PathVariable("categoryId") int categoryId, @RequestParam("auth") String auth,
			@RequestBody Category category, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return categoryService.updateCategory(categoryId, category, auth);
		}, res);

	}

	@GetMapping({ "categories/{categoryId}/products" })
	private List<Product> getProductsByCategory(@PathVariable("categoryId") int categoryId,
			@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.getProductsByCategoryId(categoryId, auth);
		}, res);

	}

}
