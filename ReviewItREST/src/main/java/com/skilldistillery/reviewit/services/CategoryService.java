package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface CategoryService {
	Category createCategory(CategoryDTO category) throws DuplicateEntityException;

	void disableCategory(int id) throws EntityDoesNotExistException;

	Category updateCategory(CategoryDTO category) throws EntityDoesNotExistException;
}
