package com.miniproject.repository;

import com.miniproject.entity.Product;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findByTitleContaining(String query);

	@Query(value = "select * from products where productid=:id", nativeQuery = true)
	Product getById(@Param("id") Long id);

	Page<Product> findByTitleContaining(String title, Pageable pageable);

}
