package com.miniproject.services;

import java.util.List;

import com.miniproject.dto.GlobalPageResponseFormat;
import com.miniproject.dto.ProductDTO;

public interface ProductService {

	public ProductDTO save(ProductDTO productDTO);

	public ProductDTO update(ProductDTO productDTO);

	public void deleteById(Long pId);

	public ProductDTO findOne(Long pId);

	public GlobalPageResponseFormat<List<ProductDTO>> getAllProducts(String title, int page, int size,
			String[] sort);

}
