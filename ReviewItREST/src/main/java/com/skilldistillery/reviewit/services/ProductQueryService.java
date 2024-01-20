package com.skilldistillery.reviewit.services;

import java.util.Set;

import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;

public interface ProductQueryService {
	PageDTO<Product> getPageOfProducts(
			int pageNum, 
			int pageSize, 
			String orderBy, 
			String sortBy, 
			Boolean discontinued,
			Double minRating, 
			Set<Category> categories);
}
