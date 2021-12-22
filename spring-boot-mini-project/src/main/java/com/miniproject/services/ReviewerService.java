package com.miniproject.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miniproject.dto.ReviewerDTO;
import com.miniproject.entity.Reviewer;

public interface ReviewerService {
	public ReviewerDTO addReviewer(ReviewerDTO reviewerDTO);

	public ReviewerDTO getReviewer(Long revId);

	public ReviewerDTO updateReviewer(ReviewerDTO reviewerDTO, Long id);

	public Page<Reviewer> getReviewersWithPagination(Pageable pageable);
	
	public Page<Reviewer> getReviewersWithPagination(String name,Pageable pageable);

	public void deleteReviewer(Long id);

	public List<ReviewerDTO> getAllReviewer();

	public List<ReviewerDTO> getReviewersWithSearch(String query);

	public List<ReviewerDTO> getReviewerByStatus(Boolean status);

	public Boolean deactivateReviewer(Long id);

	public Boolean activateReviewer(Long id);

}
