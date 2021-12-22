package com.miniproject.repository;

import com.miniproject.entity.Product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByTitleContaining(String query);

	Page<Product> findByTitleContaining(String title, Pageable pageable);
}
