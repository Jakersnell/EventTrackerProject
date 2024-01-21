package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;

public interface ProductQueryService {
	Page<Product> getPageOfProducts(int pageNum, int pageSize, String searchQuery, String orderBy, String sortBy,
			Boolean discontinued, Double minRating, Set<Category> categories, Boolean enabled) throws RestServerException;
	
	Product getProductById(int id, boolean enabled) throws EntityDoesNotExistException;
	
	List<Category> getCategoriesForProduct(int productId, boolean enabled) throws RestServerException;
}
