package com.miniproject.resources;

import java.util.List;

import javax.validation.Valid;

import com.miniproject.dto.GlobalPageResponseFormat;
import com.miniproject.dto.GlobalResponseFormat;
import com.miniproject.dto.ProductDTO;
import com.miniproject.exception.InvalidInputException;
import com.miniproject.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@PostMapping("/product")
	public ResponseEntity<GlobalResponseFormat<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO) {
		GlobalResponseFormat<ProductDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.productService.save(productDTO));
		return new ResponseEntity<GlobalResponseFormat<ProductDTO>>(response, HttpStatus.CREATED);

	}

	@GetMapping("/product/{productId}")
	public ResponseEntity<GlobalResponseFormat<ProductDTO>> getProduct(@PathVariable("productId") Long productId) {
		GlobalResponseFormat<ProductDTO> response = new GlobalResponseFormat<>();
		ProductDTO result = this.productService.findOne(productId);
		response.setError(false);
		response.setMessage(null);
		response.setData(result);
		return new ResponseEntity<GlobalResponseFormat<ProductDTO>>(response, HttpStatus.OK);
	}

	@PutMapping("/product")
	public ResponseEntity<GlobalResponseFormat<ProductDTO>> updateProduct(@Valid @RequestBody ProductDTO productDTO) {
		GlobalResponseFormat<ProductDTO> response = new GlobalResponseFormat<>();
		response.setError(false);
		response.setMessage(null);
		response.setData(this.productService.update(productDTO));
		return new ResponseEntity<GlobalResponseFormat<ProductDTO>>(response, HttpStatus.OK);
	}

	@DeleteMapping("/product/{productId}")
	public ResponseEntity<GlobalResponseFormat<String>> removeProduct(
			@Valid @PathVariable("productId") Long productId) {

		GlobalResponseFormat<String> response = new GlobalResponseFormat<>();

		this.productService.deleteById(productId);
		response.setError(false);
		response.setMessage("Product Deleted !");
		response.setData(null);
		return new ResponseEntity<GlobalResponseFormat<String>>(response, HttpStatus.OK);
	}

	@GetMapping("/product/search")
	public ResponseEntity<GlobalPageResponseFormat<List<ProductDTO>>> listAllProduct(
			@RequestParam(required = false) String title, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "50") int size, @RequestParam(defaultValue = "productID,asc") String[] sort)
			throws InvalidInputException {

		return new ResponseEntity<GlobalPageResponseFormat<List<ProductDTO>>>(
				this.productService.getAllProducts(title, page, size, sort), HttpStatus.OK);

	}

}
