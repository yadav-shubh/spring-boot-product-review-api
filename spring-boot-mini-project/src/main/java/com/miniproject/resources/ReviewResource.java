package com.miniproject.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.dto.GlobalPageResponseFormat;
import com.miniproject.dto.GlobalResponseFormat;
import com.miniproject.dto.ProductReviewDTO;
import com.miniproject.services.ProductReviewService;

@RestController
@RequestMapping("/api")
public class ReviewResource {

	@Autowired
	private ProductReviewService productReviewService;

	@PostMapping("/review")
	public ResponseEntity<GlobalResponseFormat<ProductReviewDTO>> createReview(
			@Valid @RequestBody ProductReviewDTO productReviewDTO) {
		GlobalResponseFormat<ProductReviewDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.productReviewService.save(productReviewDTO));
		return new ResponseEntity<GlobalResponseFormat<ProductReviewDTO>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/review/{id}")
	public ResponseEntity<GlobalResponseFormat<ProductReviewDTO>> getReview(@PathVariable("id") Long id) {
		GlobalResponseFormat<ProductReviewDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.productReviewService.findOne(id));
		return new ResponseEntity<GlobalResponseFormat<ProductReviewDTO>>(response, HttpStatus.OK);
	}

	@PutMapping("/review")
	public ResponseEntity<GlobalResponseFormat<ProductReviewDTO>> updateReview(
			@Valid @RequestBody ProductReviewDTO productReviewDTO) {
		GlobalResponseFormat<ProductReviewDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.productReviewService.update(productReviewDTO));
		return new ResponseEntity<GlobalResponseFormat<ProductReviewDTO>>(response, HttpStatus.OK);
	}

	@PutMapping("/review/{id}/{status}")
	public ResponseEntity<GlobalResponseFormat<ProductReviewDTO>> updateReviewStatus(@PathVariable("id") Long id,
			@PathVariable("status") Boolean status) {
		GlobalResponseFormat<ProductReviewDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.productReviewService.changeStatus(id, status));
		return new ResponseEntity<GlobalResponseFormat<ProductReviewDTO>>(response, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<GlobalPageResponseFormat<List<ProductReviewDTO>>> listAllReview(
			@RequestParam(required = false, defaultValue = "0") int rating, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size, @RequestParam(defaultValue = "reviewId,asc") String[] sort) {

		return new ResponseEntity<GlobalPageResponseFormat<List<ProductReviewDTO>>>(
				this.productReviewService.findAllReviews(rating, page, size, sort), HttpStatus.OK);
	}

}
