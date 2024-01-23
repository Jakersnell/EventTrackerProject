package com.skilldistillery.reviewit.controllers;

import java.security.Principal;
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

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.dtos.UserDTO;
import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.services.AuthenticationService;
import com.skilldistillery.reviewit.services.ProductReviewQueryService;
import com.skilldistillery.reviewit.services.ProductReviewService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping({ "api" })
public class ProductReviewController {
	@Autowired
	private ProductReviewQueryService pqrs;

	@Autowired
	private ProductReviewService prs;

	@Autowired
	private AuthenticationService authService;

	@GetMapping({ "products/{productId}/reviews" })
	private List<ProductReview> getAllForProduct(@PathVariable("productId") int productId)
			throws EntityDoesNotExistException {
		return pqrs.getAllForProduct(productId);
	}

	@GetMapping({ "products/{productId}/reviews/{reviewId}" })
	private ProductReview getReviewForProductById(@PathVariable("productId") int productId,
			@PathVariable("reviewId") int reviewId) throws EntityDoesNotExistException {
		return pqrs.getReview(productId, reviewId);
	}

	@PostMapping({ "products/{productId}/reviews" })
	private ProductReview createReview(@RequestBody ProductReviewDTO reviewDto,
			@PathVariable("productId") int productId, Principal principal) throws EntityDoesNotExistException {
		UserDTO user = authService.getUserByUsername(principal.getName());
		return prs.createReview(reviewDto, productId, user.getId());
	}

	@DeleteMapping({ "products/{productId}/reviews/{reviewId}" })
	private void disableReview(@PathVariable("productId") int productId, @PathVariable("reviewId") int reviewId) {
		prs.setReviewStatus(productId, reviewId, false);
	}

	@PutMapping({ "products/{productId}/reviews/{reviewId}" })
	private ProductReview updateReview(@PathVariable("productId") int productId, @PathVariable("reviewId") int reviewId,
			@RequestBody ProductReview review, @RequestParam("auth") String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return reviewService.updateReview(productId, reviewId, review, auth);
		}, res);

	}

}
