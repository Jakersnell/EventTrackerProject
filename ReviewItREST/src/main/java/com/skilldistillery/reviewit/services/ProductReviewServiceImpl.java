package com.skilldistillery.reviewit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.AuthToken;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.entities.ProductReview;
import com.skilldistillery.reviewit.entities.User;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;
import com.skilldistillery.reviewit.repositories.AuthTokenRepository;
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
	private AuthTokenRepository authRepo;

	@Autowired
	private UserRepository userRepo;

	private void verifyProductExists(int productId) throws EntityDoesNotExistException {
		if (!productRepo.existsById(productId)) {
			throw new EntityDoesNotExistException();
		}
	}

	private boolean isAdmin(String auth) {
		boolean isValid = false;
		AuthToken token = null;

		if (auth != null) {
			token = authRepo.findByToken(auth).orElse(null);
		}

		if (token != null) {
			isValid = token.getUser().isAdmin();
		}

		return isValid;
	}

	@Override
	public ProductReview getReviewByProductIdAndId(int productId, int reviewId, String auth)
			throws RestServerException {
		verifyProductExists(productId);

		ProductReview review = reviewRepo.findById(reviewId).orElse(null);

		if ((review == null) || (!review.isEnabled() && !isAdmin(auth))) {
			throw new EntityDoesNotExistException();
		}

		return review;
	}

	@Override
	public List<ProductReview> getAllForProduct(int productId, String auth) throws RestServerException {
		verifyProductExists(productId);
		return reviewRepo.findAllByProductId(productId);
	}

	@Override
	public ProductReview createReview(int productId, ProductReview review, String auth) throws RestServerException {
		Product product = productRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);

		AuthToken token = authRepo.findByToken(auth).orElseThrow(TokenInvalidException::new);

		User user = userRepo.findById(token.getUser().getId()).get();

		review.setProduct(product);
		review.setUser(user);

		return reviewRepo.saveAndFlush(review);
	}

	@Override
	public ProductReview updateReview(int productId, int reviewId, ProductReview review, String auth)
			throws RestServerException {
		verifyProductExists(productId);

		ProductReview managed = reviewRepo.findById(reviewId).orElseThrow(EntityDoesNotExistException::new);

		AuthToken token = authRepo.findByToken(auth).orElseThrow(TokenInvalidException::new);

		if (!token.getUser().equals(token.getUser())) {
			throw new TokenInvalidException();
		}

		if (token.getUser().isAdmin()) {
			managed.setEnabled(review.isEnabled());
		}

		managed.setContent(review.getContent());
		managed.setRating(review.getRating());

		return reviewRepo.saveAndFlush(managed);
	}

	@Override
	public void setReviewStatus(int productId, int reviewId, String auth, boolean enabled) throws RestServerException {
		verifyProductExists(productId);

		ProductReview review = reviewRepo.findById(reviewId).orElseThrow(EntityDoesNotExistException::new);

		if (auth == null) {
			throw new TokenInvalidException();
		}

		AuthToken token = authRepo.findByToken(auth).orElseThrow(TokenInvalidException::new);

		User user = userRepo.findById(token.getUser().getId()).get();

		if (!user.equals(review.getUser())) {
			throw new TokenInvalidException();
		}

		review.setEnabled(enabled);
		reviewRepo.saveAndFlush(review);

	}

}
