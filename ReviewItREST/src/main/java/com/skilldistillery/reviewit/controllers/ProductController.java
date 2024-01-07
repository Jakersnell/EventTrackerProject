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
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping({ "api/products" })
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping
	private List<Product> getAllProducts() {
		return productService.getAll();
	}

	@GetMapping({ "{productId}" })
	private Product getProductById(@PathVariable("productId") int productId, HttpServletResponse res) {
		Product product = productService.getProductById(productId);
		if (product == null) {
			res.setStatus(404);
		}
		return product;
	}

	@PostMapping
	private Product createProduct(@RequestBody Product product, HttpServletResponse res) {
		try {
			product = productService.createProduct(product);
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			product = null;
		}
		return product;
	}

	@PutMapping({"{productId}"})
	private Product updateProduct(@PathVariable("productId")int productId, @RequestBody Product product, HttpServletResponse res) {
		try {
			product = productService.updateProduct(productId, product);
			if (product == null) {
				res.setStatus(404);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(400);
			product = null;
		}
		
		return product;
	}
	
	@DeleteMapping({"{productId}"})
	private void deleteProduct(@PathVariable("productId")int productId, HttpServletResponse res ) {
		if (productService.deleteProduct(productId)) {
			res.setStatus(204);
		} else {
			res.setStatus(404);
		}
	}
	
}
