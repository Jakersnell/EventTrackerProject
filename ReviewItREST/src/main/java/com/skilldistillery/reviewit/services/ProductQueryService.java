package com.skilldistillery.reviewit.services;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;

public interface ProductQueryService {
	Page<Product> getPageOfProducts(int pageNum, int pageSize, String searchQuery, String orderBy, String sortBy,
			Boolean discontinued, Double minRating, Set<Category> categories);
}
