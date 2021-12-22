package com.miniproject.dto;

import java.io.Serializable;

public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productID;
	private String title;
	private String description;
	private Boolean active;
	private String manufacturer;
	private Double price;

	public Long getProductID() {
		return productID;
	}

	public void setProductID(Long productID) {
		this.productID = productID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	
	@Override
	public String toString() {
		return "ProductDTO [productID=" + productID + ", title=" + title + ", description=" + description + ", active="
				+ active + ", manufacturer=" + manufacturer + ", price=" + price + "]";
	}

}
