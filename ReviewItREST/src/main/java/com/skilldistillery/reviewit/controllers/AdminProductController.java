package com.skilldistillery.reviewit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.services.ProductService;

import jakarta.validation.Valid;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping({ "api/admin/products" })
public class AdminProductController {

	@Autowired
	private ProductService productService;

	@PostMapping
	private ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO productDto)
			throws DuplicateEntityException {
		return ResponseEntity.ok(productService.createProduct(productDto));
	}

	@PutMapping({ "{productId}" })
	private ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDto,
			@PathVariable("productId") int productId) throws EntityDoesNotExistException {
		Product product = productService.updateProduct(productDto, productId);
		return ResponseEntity.ok(new ProductDTO(product));
	}

	@DeleteMapping({ "{productId}" })
	private ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		productService.setStatus(productId, false);
		return ResponseEntity.noContent().build();
	}

}
