package com.miniproject.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.miniproject.dto.ReviewerDTO;
import com.miniproject.entity.Reviewer;
import com.miniproject.repository.ReviewerRepository;
import com.miniproject.services.ReviewerService;

@Service
public class ReviewerServiceImpl implements ReviewerService {

	@Autowired
	private ReviewerRepository reviewerRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ReviewerDTO addReviewer(ReviewerDTO reviewerDTO) {
		return convertEntityToDTO(this.reviewerRepository.saveAndFlush(convertDTOToEntity(reviewerDTO)));
	}

	@Override
	public ReviewerDTO getReviewer(Long id) {
		return convertEntityToDTO(this.reviewerRepository.findById(id).get());
	}

	@Override
	public List<ReviewerDTO> getAllReviewer() {
		return this.reviewerRepository.findAll().stream().map(this::convertEntityToDTO).collect(Collectors.toList());
	}

	@Override
	public ReviewerDTO updateReviewer(ReviewerDTO reviewerDTO, Long id) {
		Reviewer existReviewer = this.reviewerRepository.findById(id).get();
		existReviewer.setFullName(reviewerDTO.getFullName());
		existReviewer.setAddress(reviewerDTO.getAddress());
		existReviewer.setOccupation(reviewerDTO.getOccupation());
		existReviewer.setActive(reviewerDTO.getActive());

		return convertEntityToDTO(this.reviewerRepository.saveAndFlush(existReviewer));

	}

	@Override
	public void deleteReviewer(Long revId) {
		Reviewer reviewer = this.reviewerRepository.findById(revId).get();
		this.reviewerRepository.delete(reviewer);
		this.reviewerRepository.flush();

	}

	@Override
	public List<ReviewerDTO> getReviewerByStatus(Boolean status) {
		return this.reviewerRepository.findByActive(status).stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public Boolean deactivateReviewer(Long id) {
		Reviewer reviewer = this.reviewerRepository.findById(id).get();
		if (reviewer.getActive()) {
			reviewer.setActive(false);
			this.reviewerRepository.saveAndFlush(reviewer);
			return true;
		}
		return false;
	}

	@Override
	public Boolean activateReviewer(Long id) {
		Reviewer reviewer = this.reviewerRepository.findById(id).get();
		if (!reviewer.getActive()) {
			reviewer.setActive(true);
			this.reviewerRepository.saveAndFlush(reviewer);
			return true;
		}
		return false;
	}

	@Override
	public Page<Reviewer> getReviewersWithPagination(Pageable pageable) {
		Page<Reviewer> page = this.reviewerRepository.findAll(pageable);
		return page;
	}
	
	@Override
	public Page<Reviewer> getReviewersWithPagination(String name, Pageable pageable) {
		return this.reviewerRepository.findByFullNameContaining(name,pageable);
	}

	@Override
	public List<ReviewerDTO> getReviewersWithSearch(String query) {
		List<Reviewer> list = this.reviewerRepository.findByFullNameContaining(query);
		return list.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
	}

	private ReviewerDTO convertEntityToDTO(Reviewer reviewer) {
		ReviewerDTO reviewerDTO = new ReviewerDTO();
		reviewerDTO = modelMapper.map(reviewer, ReviewerDTO.class);
		return reviewerDTO;
	}

	private Reviewer convertDTOToEntity(ReviewerDTO reviewerDTO) {
		Reviewer reviewer = new Reviewer();
		reviewer = modelMapper.map(reviewerDTO, Reviewer.class);
		return reviewer;
	}

	

}
