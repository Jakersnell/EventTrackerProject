package com.skilldistillery.reviewit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.ProductRepository;
import com.skilldistillery.reviewit.repositories.ProductReviewRepository;

@Service
public class ProductReviewQueryServiceImpl implements ProductReviewQueryService {

	@Autowired
	private ProductReviewRepository reviewRepo;

	@Autowired
	private ProductRepository productRepo;

	@Override
	public ProductReview getReview(int productId, int reviewId) throws EntityDoesNotExistException {
		if (!productRepo.existsById(productId)) {
			throw new EntityDoesNotExistException();
		}
		return reviewRepo.findById(reviewId).filter(ProductReview::isEnabled)
				.orElseThrow(EntityDoesNotExistException::new);
	}

	@Override
	public List<ProductReview> getAllForProduct(int productId) throws EntityDoesNotExistException {
		if (!productRepo.existsById(productId)) {
			throw new EntityDoesNotExistException();
		}
		return reviewRepo.findAll().stream().filter(ProductReview::isEnabled).toList();
	}
}
