package com.miniproject.resources;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.miniproject.dto.GlobalPageResponseFormat;
import com.miniproject.dto.GlobalResponseFormat;
import com.miniproject.dto.ReviewerDTO;
import com.miniproject.services.ReviewerService;

@RestController
@RequestMapping("/api")
public class ReviewerResource {
	@Autowired
	private ReviewerService reviewerService;

	@PostMapping("/reviewer")
	public ResponseEntity<GlobalResponseFormat<ReviewerDTO>> createReviewer(
			@Valid @RequestBody ReviewerDTO reviewerDTO) {
		GlobalResponseFormat<ReviewerDTO> response = new GlobalResponseFormat<>();
		ReviewerDTO result = this.reviewerService.save(reviewerDTO);
		response.setError(false);
		response.setMessage(null);
		response.setData(result);
		return new ResponseEntity<GlobalResponseFormat<ReviewerDTO>>(response, HttpStatus.CREATED);
	}

	@GetMapping("/reviewer/{id}")
	public ResponseEntity<GlobalResponseFormat<ReviewerDTO>> getReviewer(@PathVariable("id") Long id) {
		GlobalResponseFormat<ReviewerDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.reviewerService.findOne(id));
		return new ResponseEntity<GlobalResponseFormat<ReviewerDTO>>(response, HttpStatus.OK);

	}

	@PutMapping("/reviewer")
	public ResponseEntity<GlobalResponseFormat<ReviewerDTO>> updateReviewer(
			@Valid @RequestBody ReviewerDTO reviewerDTO) {
		GlobalResponseFormat<ReviewerDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.reviewerService.update(reviewerDTO));
		return new ResponseEntity<GlobalResponseFormat<ReviewerDTO>>(response, HttpStatus.OK);

	}

	@DeleteMapping("/reviewer/{id}")
	public ResponseEntity<GlobalResponseFormat<String>> deleteReviewer(@PathVariable("id") Long id) {
		this.reviewerService.delete(id);
		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage("Reviewer Deleted");
		response.setData(null);
		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.OK);

	}

	@PutMapping("/reviewer/soft-delete/{id}")
	public ResponseEntity<GlobalResponseFormat<String>> deactivateReviewer(@PathVariable("id") Long id) {
		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();
		this.reviewerService.deactivate(id);
		response.setError(true);
		response.setMessage("Reviewer is Deactivated");
		response.setData(null);
		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.OK);
	}

	@PutMapping("/reviewer/{id}")
	public ResponseEntity<GlobalResponseFormat<String>> activateReviewer(@PathVariable("id") Long id) {
		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();
		this.reviewerService.activate(id);
		response.setError(true);
		response.setMessage("Reviewer is Activated");
		response.setData(null);
		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.OK);

	}

	@GetMapping("/reviewer/search")
	public ResponseEntity<GlobalPageResponseFormat<List<ReviewerDTO>>> listAllReviewer(
			@RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size, @RequestParam(defaultValue = "id,asc") String[] sort) {
		GlobalPageResponseFormat<List<ReviewerDTO>> response = this.reviewerService.findAllReviewers(name, page, size,
				sort);
		return new ResponseEntity<GlobalPageResponseFormat<List<ReviewerDTO>>>(response, HttpStatus.OK);

	}
}
