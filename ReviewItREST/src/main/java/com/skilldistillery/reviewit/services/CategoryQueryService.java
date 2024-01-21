package com.skilldistillery.reviewit.services;

import java.util.Set;

import org.springframework.data.domain.Page;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.RestServerException;

public interface CategoryQueryService {
	Page<Category> getPageOfCategories(int pageNum, int pageSize, String searchQuery, Set<Category> excludedCategories,
			Boolean enabled) throws RestServerException;
}
