package com.skilldistillery.reviewit.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository catRepo;

	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDto) throws DuplicateEntityException {
		if (catRepo.existsByName(categoryDto.getName())) {
			Map<String, String> errors = new HashMap<>();
			errors.put("Name", "A category with that name already exists.");
			throw new DuplicateEntityException(errors);
		}
		Category category = categoryDto.intoEntity();
		category.setId(0);
		category = catRepo.saveAndFlush(category);
		return new CategoryDTO(category);
	}

	@Override
	public CategoryDTO setStatus(int categoryId, boolean status) throws EntityDoesNotExistException {
		Category cat = catRepo.findById(categoryId).orElseThrow(EntityDoesNotExistException::new);
		cat.setEnabled(status);
		cat = catRepo.saveAndFlush(cat);
		return new CategoryDTO(cat);
	}

	@Override
	public CategoryDTO updateCategory(CategoryDTO category) throws EntityDoesNotExistException {
		Category managed = catRepo.findById(category.getId()).orElseThrow(EntityDoesNotExistException::new);
		managed.setName(category.getName());
		managed = catRepo.saveAndFlush(managed);
		return new CategoryDTO(managed);
	}

}
