package com.miniproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.miniproject.entity.Reviewer;

@Repository
public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {

	@Query(value = "select * from reviewers where email=:email", nativeQuery = true)
	Reviewer getByEmail(String email);

	@Query(value = "select * from reviewers where id=:id", nativeQuery = true)
	Reviewer getById(@Param("id") Long id);

	@Query(value = "select * from reviewers where active=:status", nativeQuery = true)
	List<Reviewer> getByStatus(@Param("status") Boolean status);

	List<Reviewer> findByFullNameContaining(String name);

	List<Reviewer> findByActive(Boolean status);

	Page<Reviewer> findByFullNameContaining(String name, Pageable pageable);
}
