package com.skilldistillery.reviewit.services;

import java.util.List;

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductReviewQueryService {
	ProductReviewDTO getReview(int productId, int reviewId) throws EntityDoesNotExistException;

	List<ProductReviewDTO> getAllForProduct(int productId) throws EntityDoesNotExistException;

	boolean userIsOwner(int productId, int reviewId, String username) throws EntityDoesNotExistException;
}
