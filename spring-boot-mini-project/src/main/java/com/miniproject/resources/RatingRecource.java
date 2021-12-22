package com.miniproject.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.dto.GlobalResponseFormat;
import com.miniproject.dto.ProductReviewDTO;
import com.miniproject.entity.ProductReview;
import com.miniproject.services.RatingService;

@RestController
@RequestMapping("/rating")
public class RatingRecource {

	@Autowired
	private RatingService ratingService;
	
	@Autowired
	private ModelMapper modelMapper;

	@PutMapping("/{productReviewId}/{rating}")
	public ResponseEntity<?> updateProductRating(@PathVariable("productReviewId") Long productReviewId,
			@PathVariable("rating") Integer rating) {
		return ResponseEntity.ok(
				new GlobalResponseFormat(false, "", List.of(this.ratingService.updateRating(productReviewId, rating))));
	}
	
	@GetMapping("/page")
	public ResponseEntity<?> listAllReviewWithPagination(@RequestParam(required = false) int rating,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "reviewId,asc") String[] sort) {

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
		Page<ProductReview> listProductReview;
		if (rating == 0) {
			listProductReview = this.ratingService.getReviewByPagination(pagingSort);
		} else {
			listProductReview = this.ratingService.getReviewByPagination(rating,pagingSort);
		}
		
		List<ProductReviewDTO> listProductReviewDTO = listProductReview.stream().map(this::convertEntityToDTO).collect(Collectors.toList());

		if (listProductReviewDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available In Page :" + page, List.of()));
		}
		return ResponseEntity.ok(Map.of("error", false, "message", "", "data", listProductReviewDTO, "totalPages",
				listProductReview.getTotalPages(), "currentPage", page, "totalRecords", listProductReview.getTotalElements()));
	}

	private ProductReviewDTO convertEntityToDTO(ProductReview productReview) {
		ProductReviewDTO productReviewDTO = new ProductReviewDTO();
		productReviewDTO = modelMapper.map(productReview, ProductReviewDTO.class);
		return productReviewDTO;
	}
}
