package com.miniproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miniproject.entity.Reviewer;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {

	Reviewer findByEmail(String email);

	List<Reviewer> findByFullNameContaining(String name);

	List<Reviewer> findByActive(Boolean status);

	Page<Reviewer> findByFullNameContaining(String name, Pageable pageable);
}
