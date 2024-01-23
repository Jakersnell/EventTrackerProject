package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface CategoryQueryService {
	PageDTO<CategoryDTO> getPageOfCategories(int pageNum, int pageSize, String searchQuery, Set<Category> excludedCategories,
			Boolean enabled);

	CategoryDTO getCategoryById(int categoryId) throws EntityDoesNotExistException;

	List<ProductDTO> getProductsByCategoryId(int categoryId) throws EntityDoesNotExistException;
}
