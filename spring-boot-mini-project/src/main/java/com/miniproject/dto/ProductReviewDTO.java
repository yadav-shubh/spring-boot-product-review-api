package com.miniproject.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.miniproject.entity.Product;
import com.miniproject.entity.Reviewer;

public class ProductReviewDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long reviewId;
	@Positive(message = "Rating should not be 0")
	private Integer rating;
	@NotNull
	@Size(min = 5, max = 150, message = "comment should be in range of 5 to 200")
	private String comment;
	private Boolean isActive;
	@NotNull
	private Product product;
	@NotNull
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
