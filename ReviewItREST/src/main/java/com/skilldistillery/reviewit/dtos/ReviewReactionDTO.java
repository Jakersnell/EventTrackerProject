package com.skilldistillery.reviewit.dtos;

import java.util.Objects;

import com.skilldistillery.reviewit.entities.ReviewReaction;
import com.skilldistillery.reviewit.entities.ReviewReactionId;

public class ReviewReactionDTO implements IntoEntity<ReviewReaction> {
	// ==== Begin Private Fields =========

	private int productReviewId;

	private int userId;

	private boolean isLike;

	// ==== End Private Fields ===========
	// ===================================
	// ==== Begin Constructors ===========

	public ReviewReactionDTO() {
	}

	public ReviewReactionDTO(ReviewReaction reviewReaction) {
		productReviewId = reviewReaction.getId().getProductReviewId();
		userId = reviewReaction.getId().getUserId();
		isLike = reviewReaction.isLike();
	}
	

	// ==== End Constructors =============
	// ===================================
	// ==== Begin Override Methods ======= 

	@Override
	public ReviewReaction intoEntity() {
		ReviewReactionId reactionId = new ReviewReactionId(productReviewId, userId);
		ReviewReaction reaction = new ReviewReaction();
		reaction.setId(reactionId);
		reaction.setLike(isLike);
		return reaction;
	}
	
	// ==== End Override Methods =========
	// ===================================
	// ==== Begin Boilerplate Methods ====

	public int getProductReviewId() {
		return productReviewId;
	}

	public void setProductReviewId(int productReviewId) {
		this.productReviewId = productReviewId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

	@Override
	public int hashCode() {
		return Objects.hash(isLike, productReviewId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewReactionDTO other = (ReviewReactionDTO) obj;
		return isLike == other.isLike && productReviewId == other.productReviewId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "ReviewReactionDTO [productReviewId=" + productReviewId + ", userId=" + userId + ", isLike=" + isLike
				+ "]";
	}

	// ==== End Boilerplate Methods ======

}
