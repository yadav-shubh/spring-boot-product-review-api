package com.miniproject.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miniproject.dto.ProductReviewDTO;
import com.miniproject.entity.ProductReview;
import com.miniproject.repository.ProductReviewRepository;
import com.miniproject.services.ProductReviewService;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

	@Autowired
	private ProductReviewRepository productReviewRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductReviewDTO addProductReview(ProductReviewDTO productReviewDTO) {
		return convertEntityToDTO(this.productReviewRepository.save(convertDTOToEntity(productReviewDTO)));
	}

	@Override
	public ProductReviewDTO updateProductReview(ProductReviewDTO productReviewDTO) {
		ProductReview review = this.productReviewRepository.findById(productReviewDTO.getReviewId()).get();
		review.setRating(productReviewDTO.getRating());
		review.setComment(productReviewDTO.getComment());
		review.setIsActive(productReviewDTO.getIsActive());
		return convertEntityToDTO(this.productReviewRepository.saveAndFlush(review));

	}

	@Override
	public ProductReviewDTO getProductReviewById(Long id) {
		return convertEntityToDTO(this.productReviewRepository.findById(id).get());
	}

	@Override
	public ProductReviewDTO changeProductReviewStatus(Long id, Boolean status) {
		ProductReview productReview = this.productReviewRepository.findById(id).get();
		productReview.setIsActive(status);
		return convertEntityToDTO(this.productReviewRepository.save(productReview));
	}

	@Override
	public List<ProductReviewDTO> getAllProductReviews() {
		return this.productReviewRepository.findAll().stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductReviewDTO> getAllProductReviewsByStatus(Boolean status) {
		return this.productReviewRepository.findByIsActive(status).stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
	}

	private ProductReviewDTO convertEntityToDTO(ProductReview review) {
		ProductReviewDTO productReviewDTO = new ProductReviewDTO();
		productReviewDTO = modelMapper.map(review, ProductReviewDTO.class);
		return productReviewDTO;
	}

	private ProductReview convertDTOToEntity(ProductReviewDTO productReviewDTO) {
		ProductReview review = new ProductReview();
		review = modelMapper.map(productReviewDTO, ProductReview.class);
		return review;
	}

}
