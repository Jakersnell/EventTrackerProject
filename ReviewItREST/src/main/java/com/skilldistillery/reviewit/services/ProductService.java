package com.skilldistillery.reviewit.services;

import java.util.List;

import com.skilldistillery.reviewit.entities.Product;

public interface ProductService {
	List<Product> getAll();
	
	Product getProductById(int id);
	
	Product createProduct(Product product);
	
	Product updateProduct(int productId, Product product);
	
	boolean deleteProduct(int productId);
}
