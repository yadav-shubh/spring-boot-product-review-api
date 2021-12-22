package com.miniproject.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miniproject.dto.ProductReviewDTO;
import com.miniproject.entity.ProductReview;

public interface RatingService {
	public ProductReviewDTO updateRating(Long id, Integer rating);

	public Page<ProductReview> getReviewByPagination(Pageable pageable);
	
	public Page<ProductReview> getReviewByPagination(Integer rating,Pageable pageable);
}
