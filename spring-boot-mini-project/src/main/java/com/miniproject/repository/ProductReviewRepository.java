package com.miniproject.repository;

import com.miniproject.entity.ProductReview;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

	List<ProductReview> findByIsActive(Boolean status);

	@Query(value = "select * from product_review where review_id=:id", nativeQuery = true)
	ProductReview getById(@Param("id") Long id);

	Page<ProductReview> findByRating(Integer rating, Pageable pageable);

}
