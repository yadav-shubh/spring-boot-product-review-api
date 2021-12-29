package com.miniproject.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.miniproject.dto.GlobalPageResponseFormat;
import com.miniproject.dto.ProductReviewDTO;
import com.miniproject.entity.Product;
import com.miniproject.entity.ProductReview;
import com.miniproject.entity.Reviewer;
import com.miniproject.exception.EntityNotExistException;
import com.miniproject.exception.InvalidInputException;
import com.miniproject.exception.RecordNotFoundException;
import com.miniproject.repository.ProductRepository;
import com.miniproject.repository.ProductReviewRepository;
import com.miniproject.repository.ReviewerRepository;
import com.miniproject.services.ProductReviewService;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

	@Autowired
	private ProductReviewRepository productReviewRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ReviewerRepository reviewerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductReviewDTO save(ProductReviewDTO productReviewDTO) {
		if (productReviewDTO.getReviewer().getId() == null && productReviewDTO.getProduct().getProductID() == null) {
			throw new InvalidInputException("Product Id and Reviewer Id should not be null");
		} else if (productReviewDTO.getReviewer().getId() == null) {
			throw new InvalidInputException("Reviewer Id should not be null");
		} else if (productReviewDTO.getProduct().getProductID() == null) {
			throw new InvalidInputException("Product Id should not be null");
		}
		Product product = this.productRepository.getById(productReviewDTO.getProduct().getProductID());
		Reviewer reviewer = this.reviewerRepository.getById(productReviewDTO.getReviewer().getId());
		if (product == null && reviewer == null) {
			throw new InvalidInputException("Given Product and Reviewer are not exist !");
		} else if (product == null) {
			throw new InvalidInputException("Reviewer is not exist");
		} else if (reviewer == null) {
			throw new InvalidInputException("Product is not exist");
		}
		ProductReview review = convertDTOToEntity(productReviewDTO);
		review = this.productReviewRepository.save(review);

		return convertEntityToDTO(review);

	}

	@Override
	public ProductReviewDTO findOne(Long id) {

		ProductReview review = this.productReviewRepository.getById(id);
		if (review == null) {
			throw new EntityNotExistException("Review not Exist with given ID: " + id);
		}
		return convertEntityToDTO(review);
	}

	@Override
	public ProductReviewDTO update(ProductReviewDTO productReviewDTO) {
		if (productReviewDTO.getReviewId() == null) {
			throw new InvalidInputException("Review ID should not be null !");
		}

		ProductReview review = this.productReviewRepository.getById(productReviewDTO.getReviewId());
		if (review == null) {
			throw new EntityNotExistException("Review not Exist with given ID: " + productReviewDTO.getReviewId());
		}
		review.setRating(productReviewDTO.getRating());
		review.setComment(productReviewDTO.getComment());
		review.setIsActive(productReviewDTO.getIsActive());
		review = this.productReviewRepository.save(review);
		return convertEntityToDTO(review);

	}

	@Override
	public ProductReviewDTO changeStatus(Long id, Boolean status) {
		ProductReview review = this.productReviewRepository.getById(id);
		if (review == null) {
			throw new EntityNotExistException("Review not Exist with given ID: " + id);
		}
		review.setIsActive(status);
		this.productReviewRepository.save(review);
		return convertEntityToDTO(review);

	}

	@Override
	public GlobalPageResponseFormat<List<ProductReviewDTO>> findAllReviews(Integer rating, int page, int size,
			String[] sort) {
		List<Order> orders = new ArrayList<Order>();
		if (sort[0].contains(",")) {
			// will sort more than 2 fields
			// sortOrder="field, direction"
			for (String sortOrder : sort) {
				String[] _sort = sortOrder.split(",");
//				Direction.ASC,Direction.DESC
				orders.add(new Order(Direction.valueOf(Direction.class, _sort[1].toUpperCase()), _sort[0]));
			}
		} else {
			// sort=[field, direction]
			orders.add(new Order(Direction.valueOf(Direction.class, sort[1].toUpperCase()), sort[0]));
		}
		Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

		Page<ProductReview> pages;
		if (rating == 0) {
			pages = this.productReviewRepository.findAll(pagingSort);
		} else {
			pages = this.productReviewRepository.findByRating(rating, pagingSort);
		}
		List<ProductReview> listProductReview = (pages.getContent().isEmpty()) ? null : pages.getContent();
		if (listProductReview == null) {
			throw new RecordNotFoundException("No Review Exists in page: " + page);
		}
		List<ProductReviewDTO> listProductReviewDTO = listProductReview.stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());

		GlobalPageResponseFormat<List<ProductReviewDTO>> response = new GlobalPageResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setCurrentPage(page);
		response.setTotalPages(pages.getTotalPages());
		response.setTotalRecords(pages.getTotalElements());
		response.setData(listProductReviewDTO);
		return response;

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
