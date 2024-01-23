package com.skilldistillery.reviewit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.ProductRepository;
import com.skilldistillery.reviewit.repositories.ProductReviewRepository;
import com.skilldistillery.reviewit.repositories.UserRepository;

@Service
public class ProductReviewQueryServiceImpl implements ProductReviewQueryService {

	@Autowired
	private ProductReviewRepository reviewRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public ProductReviewDTO getReview(int productId, int reviewId) throws EntityDoesNotExistException {
		if (!productRepo.existsById(productId)) {
			throw new EntityDoesNotExistException();
		}
		return reviewRepo.findById(reviewId).filter(ProductReview::isEnabled).map(ProductReviewDTO::new)
				.orElseThrow(EntityDoesNotExistException::new);
	}

	@Override
	public List<ProductReviewDTO> getAllForProduct(int productId) throws EntityDoesNotExistException {
		if (!productRepo.existsById(productId)) {
			throw new EntityDoesNotExistException();
		}
		return reviewRepo.findAll().stream().filter(ProductReview::isEnabled).map(ProductReviewDTO::new).toList();
	}

	@Override
	public boolean userIsOwner(int productId, int reviewId, String username) throws EntityDoesNotExistException {
		User user = userRepo.findByUsername(username).orElseThrow(EntityDoesNotExistException::new);
		ProductReview review = reviewRepo.findById(reviewId).orElseThrow(EntityDoesNotExistException::new);
		return review.getUser().equals(user);
	}
}
