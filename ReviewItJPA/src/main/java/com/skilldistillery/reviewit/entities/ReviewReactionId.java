package com.skilldistillery.reviewit.entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ReviewReactionId implements Serializable {
	private static final long serialVersionUID = 204851330997132377L;

	@Column(name = "product_review_id")
	private int productReviewId;

	@Column(name = "user_id")
	private int userId;

	public ReviewReactionId() {
	}

	public ReviewReactionId(int productReviewId, int userId) {
		this.productReviewId = productReviewId;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(productReviewId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewReactionId other = (ReviewReactionId) obj;
		return productReviewId == other.productReviewId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "ReviewReactionId [productReviewId=" + productReviewId + ", userId=" + userId + "]";
	}

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

}
