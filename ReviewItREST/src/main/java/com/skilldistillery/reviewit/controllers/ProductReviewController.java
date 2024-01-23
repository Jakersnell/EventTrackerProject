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

import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.services.ProductReviewService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping({ "api" })
public class ProductReviewController {
	@Autowired
	private ProductReviewService reviewService;

	@GetMapping({ "products/{productId}/reviews/{reviewId}" })
	private ProductReview getReviewForProductById(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId) throws EntityDoesNotExistException {
		return reviewService.getReview(productId, reviewId);
	}

	@GetMapping({ "products/{productId}/reviews" })
	private List<ProductReview> getAllForProduct(@PathVariable("productId") int productId, HttpServletResponse res) {
		return reviewService.getAllForProduct(productId);
	}

	@PostMapping({ "products/{productId}/reviews" })
	private ProductReview createReview(@PathVariable("productId") int productId, @RequestBody ProductReview review,
			@RequestParam("auth") String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return reviewService.createReview(productId, review, auth);
		}, res);

	}

	@DeleteMapping({ "products/{productId}/reviews/{reviewId}" })
	private void disableReview(@PathVariable("productId") int productId, @PathVariable("reviewId") int reviewId,
			@RequestParam("auth") String auth, HttpServletResponse res) {

		tryFailableAction(() -> {
			reviewService.setReviewStatus(productId, reviewId, auth, false);
			return true;
		}, res);

	}

	@PutMapping({ "products/{productId}/reviews/{reviewId}" })
	private ProductReview updateReview(@PathVariable("productId") int productId, @PathVariable("reviewId") int reviewId,
			@RequestBody ProductReview review, @RequestParam("auth") String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return reviewService.updateReview(productId, reviewId, review, auth);
		}, res);

	}

}
