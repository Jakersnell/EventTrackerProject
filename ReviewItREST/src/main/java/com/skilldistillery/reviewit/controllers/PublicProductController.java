package com.skilldistillery.reviewit.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.skilldistillery.reviewit.entities.Product;
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

		Page<Product> page = pqs.getPageOfProducts(pageNum, pageSize, searchQuery, groupBy, orderBy, discontinued,
				minRating, categories, true);
		List<ProductDTO> content = page.stream().map((product) -> new ProductDTO(product)).toList();
		PageDTO<ProductDTO> dto = new PageDTO<>(page, content, searchQuery);
		return ResponseEntity.ok(dto);

	}

	@GetMapping({ "{productId}" })
	private ResponseEntity<ProductDTO> getProductById(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		Product product = pqs.getProductById(productId);
		return ResponseEntity.ok(new ProductDTO(product));
	}

	@GetMapping({ "{productId}/categories" })
	private ResponseEntity<List<CategoryDTO>> getCategoriesForProduct(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		List<Category> categories = pqs.getCategoriesForProduct(productId);
		return ResponseEntity.ok(categories.stream().map(CategoryDTO::new).toList());
	}
}
