package com.miniproject.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.miniproject.dto.ProductReviewDTO;
import com.miniproject.entity.ProductReview;
import com.miniproject.repository.ProductReviewRepository;
import com.miniproject.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private ProductReviewRepository productReviewRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductReviewDTO updateRating(Long id, Integer rating) {
		ProductReview review = this.productReviewRepository.findById(id).get();
		review.setRating(rating);
		return convertEntityToDTO(this.productReviewRepository.save(review));
	}

	@Override
	public Page<ProductReview> getReviewByPagination(Pageable pageable) {
		return this.productReviewRepository.findAll(pageable);
	}
	
	@Override
	public Page<ProductReview> getReviewByPagination(Integer rating, Pageable pageable) {
		return this.productReviewRepository.findByRating(rating,pageable);
	}

	private ProductReviewDTO convertEntityToDTO(ProductReview productReview) {
		ProductReviewDTO productReviewDTO = new ProductReviewDTO();
		productReviewDTO = modelMapper.map(productReview, ProductReviewDTO.class);
		return productReviewDTO;
	}

//	private ProductReview convertDTOToEntity(ProductReviewDTO productReviewDTO) {
//		ProductReview review = new ProductReview();
//		review = modelMapper.map(productReviewDTO, ProductReview.class);
//		return review;
//	}

	
}
