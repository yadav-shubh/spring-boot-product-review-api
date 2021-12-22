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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.miniproject.dto.GlobalResponseFormat;
import com.miniproject.dto.ReviewerDTO;
import com.miniproject.entity.Reviewer;
import com.miniproject.services.ReviewerService;

@RestController
@RequestMapping("/reviewer")
public class ReviewerResource {
	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/")
	public ResponseEntity<?> addReviewer(@RequestBody ReviewerDTO reviewerDTO) {
		return ResponseEntity
				.ok(new GlobalResponseFormat(false, "", List.of(this.reviewerService.addReviewer(reviewerDTO))));
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getReviewer(@PathVariable("id") Long id) {
		return ResponseEntity.ok(new GlobalResponseFormat(false, "", List.of(this.reviewerService.getReviewer(id))));

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateReviewerByReviewerEmail(@RequestBody ReviewerDTO reviewerDTO,
			@PathVariable("id") Long id) {
		return ResponseEntity
				.ok(new GlobalResponseFormat(false, "", List.of(this.reviewerService.updateReviewer(reviewerDTO, id))));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteReviewer(@PathVariable("id") Long id) {
		this.reviewerService.deleteReviewer(id);
		return ResponseEntity.ok(new GlobalResponseFormat(false, "Reviewer Deleted", List.of()));

	}

	@GetMapping("/all")
	public ResponseEntity<?> listAllReviewers() {
		List<ReviewerDTO> listReviewerDTO = this.reviewerService.getAllReviewer();
		if (listReviewerDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available In Database", listReviewerDTO));

		}
		return ResponseEntity.ok(new GlobalResponseFormat(false, "", listReviewerDTO));
	}

	@GetMapping("/bystatus/{status}")
	public ResponseEntity<?> listAllReviewersByStatus(@PathVariable("status") Boolean status) {
		List<ReviewerDTO> listReviewerDTO = this.reviewerService.getReviewerByStatus(status);
		if (listReviewerDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available In Database", listReviewerDTO));
		}
		return ResponseEntity.ok(new GlobalResponseFormat(false, "", listReviewerDTO));
	}

	@PutMapping("/deactivate/{id}")
	public ResponseEntity<?> deactivateReviewer(@PathVariable("id") Long id) {
		if (this.reviewerService.deactivateReviewer(id)) {
			return ResponseEntity.ok(new GlobalResponseFormat(false, "Reviewer is Deactivated", List.of()));
		}
		return ResponseEntity.ok(new GlobalResponseFormat(true, "Reviewer is Already Deactivated", List.of()));
	}

	@PutMapping("/activate/{id}")
	public ResponseEntity<?> activateReviewer(@PathVariable("id") Long id) {

		if (this.reviewerService.activateReviewer(id)) {
			return ResponseEntity.ok(new GlobalResponseFormat(false, "Reviewer is Activated", List.of()));
		}
		return ResponseEntity.ok(new GlobalResponseFormat(true, "Reviewer is Already Activated", List.of()));
	}

	@GetMapping("/page")
	public ResponseEntity<?> listAllReviewwerWithPagination(@RequestParam(required = false) String name,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "id,asc") String[] sort) {

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
		Page<Reviewer> listProductReview;
		if (name == null) {
			listProductReview = this.reviewerService.getReviewersWithPagination(pagingSort);
		} else {
			listProductReview = this.reviewerService.getReviewersWithPagination(name,pagingSort);
		}
		
		List<ReviewerDTO> listReviewerDTO = listProductReview.stream().map(this::convertEntityToDTO).collect(Collectors.toList());

		if (listReviewerDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available In Page :" + page, List.of()));
		}
		return ResponseEntity.ok(Map.of("error", false, "message", "", "data", listReviewerDTO, "totalPages",
				listProductReview.getTotalPages(), "currentPage", page, "totalRecords", listProductReview.getTotalElements()));
	}

	@GetMapping("/search")
	public ResponseEntity<?> listAlReviewersWithSearch(@RequestParam("query") String query) {
		List<ReviewerDTO> listReviewerDTO = this.reviewerService.getReviewersWithSearch(query);
		if (listReviewerDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available in Database", List.of()));
		}
		return ResponseEntity.ok(Map.of("error", false, "message", "", "data", listReviewerDTO));
	}

	private ReviewerDTO convertEntityToDTO(Reviewer reviewer) {
		ReviewerDTO reviewerDTO = new ReviewerDTO();
		reviewerDTO = modelMapper.map(reviewer, ReviewerDTO.class);
		return reviewerDTO;
	}

}
