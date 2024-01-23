package com.skilldistillery.reviewit.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.services.ProductQueryService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api/products")
public class PublicProductController {

	@Autowired
	private ProductQueryService pqs;

	@GetMapping
	private ResponseEntity<PageDTO<ProductDTO>> requestPageOfProducts(@RequestParam(name = "pageNum") int pageNum,
			@RequestParam(name = "pageSize") int pageSize,
			@RequestParam(name = "searchQuery", required = false) String searchQuery,
			@RequestParam(name = "groupBy", required = false) String groupBy,
			@RequestParam(name = "orderBy", required = false) String orderBy,
			@RequestParam(name = "discontinued", required = false) Boolean discontinued,
			@RequestParam(name = "minRating", required = false) Double minRating,
			@RequestParam(name = "categories", required = false) Set<Category> categories, HttpServletResponse res) {

		PageDTO<ProductDTO> page = pqs.getPageOfProducts(pageNum, pageSize, searchQuery, groupBy, orderBy, discontinued,
				minRating, categories, true);
		return ResponseEntity.ok(page);

	}

	@GetMapping({ "{productId}" })
	private ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		return ResponseEntity.ok(pqs.getProductById(productId));
	}

	@GetMapping({ "{productId}/categories" })
	private ResponseEntity<List<CategoryDTO>> getCategoriesForProduct(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		List<CategoryDTO> categories = pqs.getCategoriesForProduct(productId);
		return ResponseEntity.ok(categories);
	}
}
