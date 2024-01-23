package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping({ "api/admin/products" })
public class AdminProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@PostMapping
	private Product createProduct(@RequestBody Product product, HttpServletResponse res) {
		return tryFailableAction(() -> {
			return productService.createProduct(product);
		}, res);
	}

	@PutMapping({ "{productId}" })
	private Product updateProduct(@PathVariable("productId") int productId, @RequestBody Product product,
			HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.updateProduct(productId, product);
		}, res);

	}

	@DeleteMapping({ "{productId}" })
	private void deleteProduct(@PathVariable("productId") int productId, HttpServletResponse res) {
		tryFailableAction(() -> {
			productService.disableProduct(productId);
			res.setStatus(204);
		}, res);

	}

}
