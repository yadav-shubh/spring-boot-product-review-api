package com.miniproject.services;

import java.util.List;

import com.miniproject.dto.ProductReviewDTO;

public interface ProductReviewService {

	public ProductReviewDTO addProductReview(ProductReviewDTO productReviewDTO);

	public ProductReviewDTO updateProductReview(ProductReviewDTO productReviewDTO);

	public ProductReviewDTO getProductReviewById(Long id);

	public ProductReviewDTO changeProductReviewStatus(Long id, Boolean status);

	public List<ProductReviewDTO> getAllProductReviews();

	public List<ProductReviewDTO> getAllProductReviewsByStatus(Boolean status);
}
