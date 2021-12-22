package com.miniproject.dto;

import java.io.Serializable;

import com.miniproject.entity.Product;
import com.miniproject.entity.Reviewer;

public class ProductReviewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long reviewId;
	private Integer rating;
	private String comment;
	private Boolean isActive;
	private Product product;
	private Reviewer reviewer;

	public Long getReviewId() {
		return reviewId;
	}

	public void setReviewId(Long reviewId) {
		this.reviewId = reviewId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Reviewer getReviewer() {
		return reviewer;
	}

	public void setReviewer(Reviewer reviewer) {
		this.reviewer = reviewer;
	}

	@Override
	public String toString() {
		return "ProductReviewDTO [reviewId=" + reviewId + ", rating=" + rating + ", comment=" + comment + ", isActive="
				+ isActive + ", product=" + product + ", reviewer=" + reviewer + "]";
	}

}
