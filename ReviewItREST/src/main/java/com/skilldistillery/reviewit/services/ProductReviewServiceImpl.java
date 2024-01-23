package com.skilldistillery.reviewit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.ProductReviewDTO;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.ProductRepository;
import com.skilldistillery.reviewit.repositories.ProductReviewRepository;
import com.skilldistillery.reviewit.repositories.UserRepository;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

	@Autowired
	private ProductReviewRepository reviewRepo;

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public ProductReview createReview(ProductReviewDTO reviewData, int productId, int userId)
			throws EntityDoesNotExistException {
		User user = userRepo.findById(userId).orElseThrow(EntityDoesNotExistException::new);
		Product product = productRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);
		ProductReview review = reviewData.intoEntity();
		review.setProduct(product);
		review.setUser(user);
		review.setId(0);
		return reviewRepo.saveAndFlush(review);
	}

	@Override
	public ProductReview updateReview(ProductReviewDTO data, int productId) throws EntityDoesNotExistException {
		ProductReview review = reviewRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);
		review.setTitle(data.getTitle());
		review.setContent(data.getContent());
		review.setRating(data.getRating());
		return reviewRepo.saveAndFlush(review);
	}

	@Override
	public void setStatus(int reviewId, boolean status) throws EntityDoesNotExistException {
		ProductReview review = reviewRepo.findById(reviewId).orElseThrow(EntityDoesNotExistException::new);
		review.setEnabled(status);
		reviewRepo.saveAndFlush(review);
	}

}
