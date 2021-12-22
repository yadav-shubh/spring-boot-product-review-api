package com.miniproject.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.miniproject.dto.GlobalResponseFormat;
import com.miniproject.dto.ProductDTO;
import com.miniproject.entity.Product;
import com.miniproject.services.ProductService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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
@RequestMapping("/product")
public class ProductResource {

	@Autowired
	private ProductService productService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/")
	public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
		return ResponseEntity
				.ok(new GlobalResponseFormat(false, "", List.of(this.productService.addProduct(productDTO))));
	}

	@GetMapping("/all")
	public ResponseEntity<?> listAllProduct() {
		List<ProductDTO> products = this.productService.getAllProducts();
		if (products.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available In Database", products));

		}
		return ResponseEntity.ok(new GlobalResponseFormat(false, "", products));
	}

	@GetMapping("/{productId}")
	public ResponseEntity<?> getProduct(@PathVariable("productId") Long productId) {
		return ResponseEntity
				.ok(new GlobalResponseFormat(false, "", List.of(this.productService.getProductById(productId))));
	}

	@PutMapping("")
	public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
		return ResponseEntity
				.ok(new GlobalResponseFormat(false, "", List.of(this.productService.updateProduct(productDTO))));
	}

	@DeleteMapping("/{productId}")
	public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
		this.productService.deleteProduct(productId);
		return ResponseEntity.ok(new GlobalResponseFormat(false, "Product Deleted !", List.of()));
	}


	@GetMapping("/search")
	public ResponseEntity<?> listAllProductWithSearchResult(@RequestParam("query") String query) {
		List<ProductDTO> productDTOList = this.productService.getAllProductWithSerach(query);
		if (productDTOList.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available In Database", productDTOList));

		}
		return ResponseEntity.ok(new GlobalResponseFormat(false, "", productDTOList));
	}

	@GetMapping("/page")
	public ResponseEntity<?> listAllProductWithPagination(@RequestParam(required = false) String title,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "50") int size,
			@RequestParam(defaultValue = "productID,asc") String[] sort) {

		List<Order> orders = new ArrayList<Order>();
		if (sort[0].contains(",")) {
			// will sort more than 2 fields
			// sortOrder="field, direction"
			for (String sortOrder : sort) {
				String[] _sort = sortOrder.split(",");
//				Direction.ASC,Direction.DESC
				orders.add(new Order(Direction.valueOf(Direction.class, _sort[1].toUpperCase()), _sort[0]));
			}
		} else {
			// sort=[field, direction]
			orders.add(new Order(Direction.valueOf(Direction.class, sort[1].toUpperCase()), sort[0]));
		}
		Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
		Page<Product> listProduct;
		if (title == null) {
			listProduct = this.productService.getAllProductsByPagination(pagingSort);
		} else {
			listProduct = this.productService.getAllProductsByPagination(title, pagingSort);
		}
		
		List<ProductDTO> listProductDTO = listProduct.stream().map(this::convertEntityToDTO).collect(Collectors.toList());

		if (listProductDTO.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new GlobalResponseFormat(true, "No Data Available In Page :" + page, List.of()));
		}
		return ResponseEntity.ok(Map.of("error", false, "message", "", "data", listProductDTO, "totalPages",
				listProduct.getTotalPages(), "currentPage", page, "totalRecords", listProduct.getTotalElements()));
	}

	private ProductDTO convertEntityToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO = modelMapper.map(product, ProductDTO.class);
		return productDTO;
	}
}
