package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductReviewService {
	ProductReviewDTO createReview(ProductReviewDTO reviewData, int productId, int userId)
			throws EntityDoesNotExistException;

	ProductReviewDTO updateReview(ProductReviewDTO data, int productId, int reviewId)
			throws EntityDoesNotExistException;

	ProductReviewDTO setStatus(int productId, int reviewId, boolean status)
			throws EntityDoesNotExistException;
}
