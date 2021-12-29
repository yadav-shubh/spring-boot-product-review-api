package com.miniproject.services;

import java.util.List;

import com.miniproject.dto.GlobalPageResponseFormat;
import com.miniproject.dto.ReviewerDTO;

public interface ReviewerService {
	public ReviewerDTO save(ReviewerDTO reviewerDTO);

	public ReviewerDTO findOne(Long revId);

	public ReviewerDTO update(ReviewerDTO reviewerDTO);

	public GlobalPageResponseFormat<List<ReviewerDTO>> findAllReviewers(String name, int page, int size,
			String[] sort);

	public void deactivate(Long id);

	public void activate(Long id);

	public void delete(Long id);

}
