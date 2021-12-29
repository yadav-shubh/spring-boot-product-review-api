package com.miniproject.services;

import java.util.List;

import com.miniproject.dto.GlobalPageResponseFormat;
import com.miniproject.dto.ProductReviewDTO;

public interface ProductReviewService {

	public ProductReviewDTO save(ProductReviewDTO productReviewDTO);

	public ProductReviewDTO update(ProductReviewDTO productReviewDTO);

	public ProductReviewDTO findOne(Long id);

	public ProductReviewDTO changeStatus(Long id, Boolean status);

	public GlobalPageResponseFormat<List<ProductReviewDTO>> findAllReviews(Integer rating, int page, int size,
			String[] sort);
}
