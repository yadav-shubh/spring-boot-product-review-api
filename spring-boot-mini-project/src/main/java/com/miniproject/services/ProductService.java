package com.miniproject.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.miniproject.dto.ProductDTO;
import com.miniproject.entity.Product;

public interface ProductService {
	
	public ProductDTO addProduct(ProductDTO productDTO);

	public ProductDTO updateProduct(ProductDTO productDTO);

	public void deleteProduct(Long pId);

	public ProductDTO getProductById(Long pId);

	public List<ProductDTO> getAllProducts();

	public Page<Product> getAllProductsByPagination(Pageable pageable);
	
	public Page<Product> getAllProductsByPagination(String title,Pageable pageable);

	public List<ProductDTO> getAllProductWithSerach(String query);

}
