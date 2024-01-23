package com.skilldistillery.reviewit.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.exceptions.AuthException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.services.AuthenticationService;
import com.skilldistillery.reviewit.services.ProductReviewQueryService;
import com.skilldistillery.reviewit.services.ProductReviewService;

import jakarta.validation.Valid;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping({ "api/products" })
public class ProductReviewController {

	@Autowired
	private ProductReviewQueryService pqrs;

	@Autowired
	private ProductReviewService prs;

	@Autowired
	private AuthenticationService authService;

	/// GET /api/products/{productId}/reviews Returns: [ProductReview{**}] Errors:
	/// 404
	@GetMapping({ "{productId}/reviews" })
	private ResponseEntity<List<ProductReviewDTO>> getAllForProduct(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		
		return ResponseEntity.ok(pqrs.getAllForProduct(productId));
	}

	/// GET /api/products/{productId}/reviews/{reviewId} Returns ProductReview
	/// Errors 404
	@GetMapping({ "{productId}/reviews/{reviewId}" })
	private ResponseEntity<ProductReviewDTO> getReviewForProductById(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId) throws EntityDoesNotExistException {
		
		return ResponseEntity.ok(pqrs.getReview(productId, reviewId));
	}

	// POST /api/products/{productId}/reviews; Body: ProductReview; Returns:
	// ProductReview Errors 404, 400
	@PostMapping({ "{productId}/reviews" })
	private ResponseEntity<ProductReviewDTO> createReview(@Valid @RequestBody ProductReviewDTO reviewDto,
			@PathVariable("productId") int productId, Principal principal)
			throws EntityDoesNotExistException, AuthException {
		
		UserDTO user = authService.getUserByUsername(principal.getName());
		return ResponseEntity.ok(prs.createReview(reviewDto, productId, user.getId()));
	}

	@DeleteMapping({ "{productId}/reviews/{reviewId}" })
	private ResponseEntity<Void> disableReview(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId, Principal principal)
			throws EntityDoesNotExistException, AuthException {

		if (!pqrs.userIsOwner(productId, reviewId, principal.getName()))
			throw new AuthException();

		prs.setStatus(productId, reviewId, false);
		return ResponseEntity.noContent().build();
	}

	@PutMapping({ "{productId}/reviews/{reviewId}" })
	private ResponseEntity<ProductReviewDTO> updateReview(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId, @Valid @RequestBody ProductReviewDTO reviewDTO, Principal principal)
			throws AuthException, EntityDoesNotExistException {
		
		if (!pqrs.userIsOwner(productId, reviewId, principal.getName()))
			throw new AuthException();
		return ResponseEntity.ok(prs.updateReview(reviewDTO, productId, reviewId));
	}

}
