package com.miniproject.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.dto.GlobalResponseFormat;
import com.miniproject.dto.ProductReviewDTO;
import com.miniproject.services.ProductReviewService;

@RestController
@RequestMapping("/review")
public class ReviewResource {

	@Autowired
	private ProductReviewService productReviewService;

	@PostMapping("/")
	public ResponseEntity<?> addReview(@RequestBody ProductReviewDTO productReviewDTO) {
		return ResponseEntity.ok(new GlobalResponseFormat(false, "",
				List.of(this.productReviewService.addProductReview(productReviewDTO))));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getReview(@PathVariable("id") Long id) {
		return ResponseEntity
				.ok(new GlobalResponseFormat(false, "", List.of(this.productReviewService.getProductReviewById(id))));
	}

	@PutMapping("/")
	public ResponseEntity<?> updateReview(@RequestBody ProductReviewDTO productReviewDTO) {
		return ResponseEntity.ok(new GlobalResponseFormat(false, "",
				List.of(this.productReviewService.updateProductReview(productReviewDTO))));
	}

	@PutMapping("/{id}/{status}")
	public ResponseEntity<?> updateReviewStatus(@PathVariable("id") Long id, @PathVariable("status") Boolean status) {

		return ResponseEntity.ok(new GlobalResponseFormat(false, "",
				List.of(this.productReviewService.changeProductReviewStatus(id, status))));

	}

	@GetMapping("/all/{status}")
	public ResponseEntity<?> listAllReviewByStatus(@PathVariable("status") Boolean status) {
		List<ProductReviewDTO> listProductReviewDTO = this.productReviewService.getAllProductReviewsByStatus(status);
		if (listProductReviewDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GlobalResponseFormat(true, "No Data Available is Database",
					List.of()));
		}
		return ResponseEntity.ok(new GlobalResponseFormat(false, "",listProductReviewDTO));
	}

	@GetMapping("/all")
	public ResponseEntity<?> listALlReview() {
		List<ProductReviewDTO> listProductReviewDTO = this.productReviewService.getAllProductReviews();
		if (listProductReviewDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new GlobalResponseFormat(true, "No Data Available is Database",
					List.of()));
		}
		return ResponseEntity.ok(new GlobalResponseFormat(false, "",listProductReviewDTO));
	}

}
