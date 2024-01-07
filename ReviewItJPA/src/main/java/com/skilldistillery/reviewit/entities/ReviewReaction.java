package com.skilldistillery.reviewit.entities;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "review_reaction")
public class ReviewReaction {
	@EmbeddedId
	private ReviewReactionId id;

	@Column(name = "is_like")
	private boolean isLike;

	public ReviewReaction() {

	}

	public ReviewReaction(boolean isLike) {
		this.isLike = isLike;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isLike);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReviewReaction other = (ReviewReaction) obj;
		return Objects.equals(id, other.id) && isLike == other.isLike;
	}

	@Override
	public String toString() {
		return "RewiewReaction [id=" + id + ", isLike=" + isLike + "]";
	}

	public ReviewReactionId getId() {
		return id;
	}

	public void setId(ReviewReactionId id) {
		this.id = id;
	}

	public boolean isLike() {
		return isLike;
	}

	public void setLike(boolean isLike) {
		this.isLike = isLike;
	}

}
