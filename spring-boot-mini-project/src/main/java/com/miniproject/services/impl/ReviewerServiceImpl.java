package com.miniproject.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.miniproject.exception.EntityNotExistException;
import com.miniproject.exception.InvalidInputException;

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
	public ReviewerDTO save(ReviewerDTO reviewerDTO) {
		Reviewer reviewer = this.reviewerRepository.getByEmail(reviewerDTO.getEmail());
		if (reviewer != null) {
			throw new InvalidInputException("Reviewer is already exist with given email : " + reviewerDTO.getEmail());
		}
		reviewer = convertDTOToEntity(reviewerDTO);
		reviewer = this.reviewerRepository.save(reviewer);
		return convertEntityToDTO(reviewer);
	}

	@Override
	public ReviewerDTO findOne(Long id) {
		Reviewer reviewer = this.reviewerRepository.getById(id);
		if (reviewer == null) {
			throw new EntityNotExistException("No Reviewer Exists with ID : " + id);
		}
		return convertEntityToDTO(reviewer);

	}

	@Override
	public ReviewerDTO update(ReviewerDTO reviewerDTO) {
		if (reviewerDTO.getId() == null) {
			throw new InvalidInputException("Reviewer ID should not be null !");
		}
		Reviewer reviewer = this.reviewerRepository.getById(reviewerDTO.getId());
		if (reviewer == null) {
			throw new EntityNotExistException("No Reviewer Exists with given ID : " + reviewerDTO.getId());
		}
		reviewerDTO.setId(reviewer.getId());
		reviewerDTO.setEmail(reviewer.getEmail());
		reviewer = convertDTOToEntity(reviewerDTO);
		this.reviewerRepository.save(reviewer);
		return reviewerDTO;

	}

	@Override
	public void delete(Long id) {
		Reviewer reviewer = this.reviewerRepository.getById(id);
		if (reviewer == null) {
			throw new EntityNotExistException("No Reviewer Exists with given ID : " + id);
		}
		this.reviewerRepository.delete(reviewer);

	}

	@Override
	public void deactivate(Long id) {
		Reviewer reviewer = this.reviewerRepository.getById(id);
		if (reviewer == null) {
			throw new EntityNotExistException("No Reviewer Exists with given ID : " + id);
		}
		reviewer.setActive(false);
		this.reviewerRepository.save(reviewer);

	}

	@Override
	public void activate(Long id) {
		Reviewer reviewer = this.reviewerRepository.getById(id);
		if (reviewer == null) {
			throw new EntityNotExistException("No Reviewer Exists with given ID : " + id);
		}
		reviewer.setActive(true);
		this.reviewerRepository.save(reviewer);
	}

	@Override
	public GlobalPageResponseFormat<List<ReviewerDTO>> findAllReviewers(String name, int page, int size,
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

		Page<Reviewer> pages;
		if (name == null || name.trim().isEmpty()) {
			pages = this.reviewerRepository.findAll(pagingSort);
		} else {
			pages = this.reviewerRepository.findByFullNameContaining(name, pagingSort);
		}
		List<Reviewer> listReviewer = (pages.getContent().isEmpty()) ? null : pages.getContent();
		if (listReviewer == null) {
			throw new InvalidInputException("No Reviewer Exists in page: " + page);
		}
		List<ReviewerDTO> listReviewerDTO = listReviewer.stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());

		GlobalPageResponseFormat<List<ReviewerDTO>> response = new GlobalPageResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setCurrentPage(page);
		response.setTotalPages(pages.getTotalPages());
		response.setTotalRecords(pages.getTotalElements());
		response.setData(listReviewerDTO);
		return response;

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
