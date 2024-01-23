package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductQueryService {
	Page<Product> getPageOfProducts(int pageNum, int pageSize, String searchQuery, String orderBy, String sortBy,
			Boolean discontinued, Double minRating, Set<Category> categories, Boolean enabled);

	Product getProductById(int id) throws EntityDoesNotExistException;

	List<Category> getCategoriesForProduct(int productId) throws EntityDoesNotExistException;
}
