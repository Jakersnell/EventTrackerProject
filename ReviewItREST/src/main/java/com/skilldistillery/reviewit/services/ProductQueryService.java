package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductQueryService {
	PageDTO<ProductDTO> getPageOfProducts(int pageNum, int pageSize, String searchQuery, String orderBy, String sortBy,
			Boolean discontinued, Double minRating, Set<Category> categories, Boolean enabled);

	ProductDTO getProductById(int id) throws EntityDoesNotExistException;

	List<CategoryDTO> getCategoriesForProduct(int productId) throws EntityDoesNotExistException;
	
}

