package com.skilldistillery.reviewit.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;

public interface ProductService {
	List<Product> getAll(String auth) throws TokenInvalidException;

	Product getProductById(int id, String auth) throws EntityDoesNotExistException;

	Product createProduct(Product product, String auth) throws RestServerException;

	Product updateProduct(int productId, Product product, String auth) throws RestServerException;

	void disableProduct(int productId, String auth) throws RestServerException;

	List<Product> getProductsByCategoryId(int categoryId, String auth) throws RestServerException;

	List<Category> getCategoriesForProduct(int productId, String auth) throws RestServerException;
	
	Page<Product> getPageOfProducts(Integer pageSize, Integer pageNum, String sortBy, String auth) throws RestServerException;
	
}
