package com.skilldistillery.reviewit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository catRepo;

	@Override
	public Category createCategory(CategoryDTO categoryDto) {
		Category category = categoryDto.intoEntity();
		category.setId(0);
		return catRepo.saveAndFlush(category);
	}

	@Override
	public void disableCategory(int categoryId) throws EntityDoesNotExistException {

		Category cat = catRepo.findById(categoryId).orElseThrow(EntityDoesNotExistException::new);

		cat.setEnabled(false);
		catRepo.saveAndFlush(cat);
	}

	@Override
	public Category updateCategory(CategoryDTO category) throws EntityDoesNotExistException {
		Category managed = catRepo.findById(category.getId()).orElseThrow(EntityDoesNotExistException::new);
		managed.setName(category.getName());
		return catRepo.saveAndFlush(managed);
	}

}
