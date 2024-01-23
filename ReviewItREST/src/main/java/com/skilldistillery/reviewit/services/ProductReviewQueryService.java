package com.skilldistillery.reviewit.services;

import java.util.List;

import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductReviewQueryService {
	ProductReview getReview(int reviewId) throws EntityDoesNotExistException;

	List<ProductReview> getAllForProduct(int productId) throws EntityDoesNotExistException;
}
