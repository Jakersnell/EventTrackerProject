package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface CategoryService {
	CategoryDTO createCategory(CategoryDTO category) throws DuplicateEntityException;

	CategoryDTO setStatus(int id, boolean status) throws EntityDoesNotExistException;

	CategoryDTO updateCategory(CategoryDTO category) throws EntityDoesNotExistException;
}
