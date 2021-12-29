package com.miniproject.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
import com.miniproject.dto.ProductDTO;
import com.miniproject.entity.Product;
import com.miniproject.exception.EntityNotExistException;
import com.miniproject.exception.InvalidInputException;
import com.miniproject.exception.RecordNotFoundException;
import com.miniproject.repository.ProductRepository;
import com.miniproject.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO save(ProductDTO productDTO) {
		Product product = convertDTOToEntity(productDTO);
		product = this.productRepository.save(product);
		return convertEntityToDTO(product);
	}

	@Override
	public ProductDTO findOne(Long pId) {

		Product product = productRepository.getById(pId);
		if (product == null) {
			throw new EntityNotExistException("No Product Exist with Given ID: " + pId);
		}
		return convertEntityToDTO(product);

	}

	@Override
	public ProductDTO update(ProductDTO productDTO) {
		if (productDTO.getProductID() == null) {
			throw new InvalidInputException("Product ID should not be null !");
		}
		Product productFromDB = this.productRepository.getById(productDTO.getProductID());
		if (productFromDB == null) {
			throw new EntityNotExistException("No Product Exists with ID : " + productDTO.getProductID());
		}
		productDTO.setProductID(productFromDB.getProductID());
		Product product = convertDTOToEntity(productDTO);
		this.productRepository.saveAndFlush(product);
		ProductDTO savedProduct = convertEntityToDTO(product);
		return savedProduct;

	}

	@Override
	public void deleteById(Long pId) {

		Product product = this.productRepository.getById(pId);
		if (product == null) {
			throw new EntityNotExistException("No Product Exists with ID : " + pId);
		}
		this.productRepository.delete(product);
	}

	@Override
	public GlobalPageResponseFormat<List<ProductDTO>> getAllProducts(String title, int page, int size, String[] sort)
			throws InvalidInputException {
		List<Order> orders = new ArrayList<Order>();
		if (sort[0].contains(",")) {
			for (String sortOrder : sort) {
				String[] _sort = sortOrder.split(",");
				orders.add(new Order(Direction.valueOf(Direction.class, _sort[1].toUpperCase()), _sort[0]));
			}
		} else {
			orders.add(new Order(Direction.valueOf(Direction.class, sort[1].toUpperCase()), sort[0]));
		}
		Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

		Page<Product> pages;
		if (title == null || title.trim().isEmpty()) {
			pages = this.productRepository.findAll(pagingSort);
		} else {
			pages = this.productRepository.findByTitleContaining(title, pagingSort);
		}
		List<Product> listProduct = (pages.getContent().isEmpty()) ? null : pages.getContent();
		if (listProduct == null) {
			throw new RecordNotFoundException("No Produts Exists in page: " + page);
		}
		List<ProductDTO> listProductDTO = listProduct.stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
		GlobalPageResponseFormat<List<ProductDTO>> response = new GlobalPageResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setCurrentPage(page);
		response.setTotalPages(pages.getTotalPages());
		response.setTotalRecords(pages.getTotalElements());
		response.setData(listProductDTO);
		return response;

	}

	private ProductDTO convertEntityToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO = modelMapper.map(product, ProductDTO.class);
		return productDTO;
	}

	private Product convertDTOToEntity(ProductDTO productDTO) {
		Product product = new Product();
		product = modelMapper.map(productDTO, Product.class);
		return product;
	}

}
