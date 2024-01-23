package com.skilldistillery.reviewit.dtos;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import com.skilldistillery.reviewit.entities.Product;

import jakarta.validation.constraints.Max;

public class ProductDTO {
	private int id;

	@NotBlank
	@Max(120)
	private String name;
	
	@NotBlank
	private String description;
	
	private String usMsrp;

	private String brandName;

	@NotBlank
	private String imageUrl;

	private boolean discontinued;

	private double averageRating;

	private int numberOfReviews;

	public ProductDTO() {
	}

	public ProductDTO(Product product) {
		id = product.getId();
		name = product.getName();
		description = product.getDescription();
		usMsrp = product.getUsMsrp();
		imageUrl = product.getImageUrl();
		brandName = product.getBrandName();
		discontinued = product.isDiscontinued();
		averageRating = product.getAverageRating();
		numberOfReviews = product.getReviews().size();
	}

	public Product intoEntity() {
		Product product = new Product();
		product.setId(id);
		product.setName(name);
		product.setDescription(description);
		product.setUsMsrp(usMsrp);
		product.setBrandName(brandName);
		product.setImageUrl(imageUrl);
		product.setDiscontinued(discontinued);
		return product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsMsrp() {
		return usMsrp;
	}

	public void setUsMsrp(String usMsrp) {
		this.usMsrp = usMsrp;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public boolean isDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(boolean discontinued) {
		this.discontinued = discontinued;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public int getNumberOfReviews() {
		return numberOfReviews;
	}

	public void setNumberOfReviews(int numberOfReviews) {
		this.numberOfReviews = numberOfReviews;
	}

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", usMsrp=" + usMsrp
				+ ", brandName=" + brandName + ", imageUrl=" + imageUrl + ", discontinued=" + discontinued
				+ ", averageRating=" + averageRating + ", numberOfReviews=" + numberOfReviews + "]";
	}

}
