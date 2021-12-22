package com.miniproject.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.miniproject.dto.ProductDTO;
import com.miniproject.entity.Product;
import com.miniproject.repository.ProductRepository;
import com.miniproject.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ProductDTO addProduct(ProductDTO productDTO) {
		Product product = this.productRepository.saveAndFlush(convertDTOToEntity(productDTO));
		return convertEntityToDTO(product);
	}

	@Override
	public List<ProductDTO> getAllProducts() {
		List<ProductDTO> listProducts = this.productRepository.findAll().stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
		return listProducts;
	}

	@Override
	public ProductDTO getProductById(Long pId) {
		return convertEntityToDTO(this.productRepository.findById(pId).get());
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDTO) {
		Product existProduct = this.productRepository.findById(convertDTOToEntity(productDTO).getProductID()).get();
		existProduct.setTitle(productDTO.getTitle());
		existProduct.setDescription(productDTO.getDescription());
		existProduct.setManufacturer(productDTO.getManufacturer());
		existProduct.setActive(productDTO.getActive());
		existProduct.setPrice(productDTO.getPrice());
		return convertEntityToDTO(this.productRepository.saveAndFlush(existProduct));

	}

	@Override
	public void deleteProduct(Long pId) {
		this.productRepository.findById(pId);
		this.productRepository.deleteById(pId);
	}

	@Override
	public Page<Product> getAllProductsByPagination(Pageable pageable) {
		return this.productRepository.findAll(pageable);
	}
	
	@Override
	public Page<Product> getAllProductsByPagination(String title, Pageable pageable) {
		return this.productRepository.findByTitleContaining(title,pageable);
	}

	@Override
	public List<ProductDTO> getAllProductWithSerach(String query) {
		return this.productRepository.findByTitleContaining(query).stream().map(this::convertEntityToDTO)
				.collect(Collectors.toList());
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
