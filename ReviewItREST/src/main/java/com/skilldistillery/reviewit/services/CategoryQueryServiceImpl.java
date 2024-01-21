package com.skilldistillery.reviewit.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.repositories.CategoryRepository;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

	@Autowired
	private CategoryRepository catRepo;

	@Override
	public Page<Category> getPageOfCategories(int pageNum, int pageSize, String searchQuery,
			Set<Category> excludedCategories, Boolean enabled) throws RestServerException {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		return catRepo.getPage(searchQuery, enabled, excludedCategories, pageable);
	}

}
