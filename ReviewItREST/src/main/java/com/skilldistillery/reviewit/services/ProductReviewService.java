package com.skilldistillery.reviewit.services;

import java.util.List;

import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.exceptions.RestServerException;

public interface ProductReviewService {
	ProductReview getReviewByProductIdAndId(int productId, int reviewId, String auth) throws RestServerException;

	List<ProductReview> getAllForProduct(int productId, String auth) throws RestServerException;

	ProductReview createReview(int productId, ProductReview review, String auth) throws RestServerException;

	ProductReview updateReview(int productId, int reviewId, ProductReview review, String auth) throws RestServerException;

	void setReviewStatus(int productId, int reviewId, String auth, boolean enabled) throws RestServerException;
}
