package com.skilldistillery.reviewit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.reviewit.entities.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Integer> {
	List<ProductReview> findAllByProductId(int productId);

	boolean existsByTitle(String title);
}
