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

import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({ "api" })
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@GetMapping({ "products" })
	private List<Product> getAllProducts(@RequestParam(name = "auth", required = false) String auth,
			HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.getAll(auth);
		}, res);

	}

	@GetMapping({ "products/{productId}" })
	private Product getProductById(@PathVariable("productId") int productId,
			@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.getProductById(productId, auth);
		}, res);

	}

	@PostMapping({ "products" })
	private Product createProduct(@RequestBody Product product, @RequestParam("auth") String auth,
			HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.createProduct(product, auth);
		}, res);

	}

	@PutMapping({ "products/{productId}" })
	private Product updateProduct(@PathVariable("productId") int productId, @RequestBody Product product,
			@RequestParam("auth") String auth, HttpServletResponse res) {
		
		return tryFailableAction(() -> {
			return productService.updateProduct(productId, product, auth);
		}, res);
		
	}

	@DeleteMapping({ "products/{productId}" })
	private void deleteProduct(@PathVariable("productId") int productId, @RequestParam("auth") String auth,
			HttpServletResponse res) {
		
		tryFailableAction(() -> {
			productService.disableProduct(productId, auth);
		}, res);
		
	}

	@GetMapping({ "categories/{categoryId}/products" })
	private List<Product> getProductsByCategory(@PathVariable("categoryId") int categoryId,
			@RequestParam(name="auth", required=false) String auth, HttpServletResponse res) {
		
		return tryFailableAction(() -> {
			return productService.getProductsByCategoryId(categoryId, auth);
		}, res);
		
	}

}
