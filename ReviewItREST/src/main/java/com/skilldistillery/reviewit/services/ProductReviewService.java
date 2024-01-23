package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductReviewService {
	ProductReview createReview(ProductReviewDTO reviewData, int productId, int userId)
			throws EntityDoesNotExistException;

	ProductReview updateReview(ProductReviewDTO data, int productId, int reviewId) throws EntityDoesNotExistException;

	void setStatus(int productId, int reviewId, boolean status) throws EntityDoesNotExistException;
}
