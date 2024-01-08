package com.skilldistillery.reviewit.services;

import java.util.List;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;

public interface CategoryService {
	List<Category> getAll(String auth) throws TokenInvalidException;

	Category getCategoryById(int categoryId, String auth) throws RestServerException;

	Category createCategory(Category category, String auth) throws RestServerException;

	void disableCategory(int id, String auth) throws RestServerException;

	Category updateCategory(int id, Category category, String auth) throws RestServerException;
}
