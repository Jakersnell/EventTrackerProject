package com.skilldistillery.reviewit.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.services.ProductReviewQueryService;
import com.skilldistillery.reviewit.services.ProductReviewService;

@RestController
@RequestMapping({ "api/admin/products/{productId}/reviews" })
@CrossOrigin("*")
public class AdminProductReviewController {

	@Autowired
	private ProductReviewService prs;

	@Autowired
	private ProductReviewQueryService pqrs;

	@GetMapping
	public ResponseEntity<List<ProductReview>> getAllForProduct(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		return ResponseEntity.ok(pqrs.getAllForProduct(productId));
	}

	@PutMapping({ "{reviewId}" })
	public ResponseEntity<ProductReviewDTO> setStatusUpdate(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId, @RequestParam("status") boolean status)
			throws EntityDoesNotExistException {
		return ResponseEntity.ok(prs.setStatus(productId, reviewId, status));
	}

	@DeleteMapping({ "{reviewId}" })
	public ResponseEntity<Void> deleteReview(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId, Principal principal) throws EntityDoesNotExistException {
		prs.setStatus(productId, reviewId, false);
		return ResponseEntity.noContent().build();
	}
}
