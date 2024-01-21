package com.skilldistillery.reviewit.dtos;

import com.skilldistillery.reviewit.entities.Product;

public class ProductDTO {
	private int id;

	private String name;

	private String description;

	private String usMsrp;

	private String brandName;

	private String imageUrl;

	private boolean discontinued;

	private double averageRating;

	public ProductDTO() {
	}

	public ProductDTO(int id, String name, String description, String usMsrp, String brandName, String imageUrl,
			boolean discontinued, double averageRating) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.usMsrp = usMsrp;
		this.brandName = brandName;
		this.imageUrl = imageUrl;
		this.discontinued = discontinued;
		this.averageRating = averageRating;
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

	@Override
	public String toString() {
		return "ProductDTO [id=" + id + ", name=" + name + ", description=" + description + ", usMsrp=" + usMsrp
				+ ", brandName=" + brandName + ", imageUrl=" + imageUrl + ", discontinued=" + discontinued
				+ ", averageRating=" + averageRating + "]";
	}

}
