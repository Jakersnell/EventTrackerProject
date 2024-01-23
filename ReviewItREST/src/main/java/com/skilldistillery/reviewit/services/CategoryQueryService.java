package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface CategoryQueryService {
	Page<Category> getPageOfCategories(int pageNum, int pageSize, String searchQuery, Set<Category> excludedCategories,
			Boolean enabled);

	Category getCategoryById(int categoryId) throws EntityDoesNotExistException;

	List<Product> getProductsByCategoryId(int categoryId) throws EntityDoesNotExistException;
}
