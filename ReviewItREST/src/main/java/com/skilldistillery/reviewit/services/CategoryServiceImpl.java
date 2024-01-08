package com.skilldistillery.reviewit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.exceptions.BadRequestException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;
import com.skilldistillery.reviewit.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private AuthService authService;

	@Autowired
	private CategoryRepository catRepo;

	@Override
	public List<Category> getAll(String auth) throws TokenInvalidException {
		List<Category> categories = catRepo.findAll();
		if (!authService.userIsAdmin(auth)) {
			categories = categories
					.stream()
					.filter(Category::isEnabled)
					.toList();
		}
		return categories;
	}

	@Override
	public Category getCategoryById(int categoryId, String auth) throws RestServerException {
		Category category = catRepo
				.findById(categoryId)
				.orElseThrow(EntityDoesNotExistException::new);
		
		if (!category.isEnabled() && !authService.userIsAdmin(auth)) {
			throw new EntityDoesNotExistException();
		}
		
		return category;
	}

	@Override
	public Category createCategory(Category category, String auth) throws RestServerException{
		if (!authService.userIsAdmin(auth)) {
			throw new TokenInvalidException();
		}
		if (category.getName() == null || category.getName().isBlank()) {
			throw new BadRequestException();
		}
		category.setId(0);
		return catRepo.saveAndFlush(category);
	}

	@Override
	public void disableCategory(int categoryId, String auth) throws RestServerException {
		
		Category cat = catRepo
				.findById(categoryId)
				.orElseThrow(EntityDoesNotExistException::new);
		
		if (!authService.userIsAdmin(auth)) {
			throw new TokenInvalidException();
		}
		
		cat.setEnabled(false);
		catRepo.saveAndFlush(cat);
	}

	@Override
	public Category updateCategory(int id, Category category, String auth) throws RestServerException {
		if (!authService.userIsAdmin(auth)) {
			throw new TokenInvalidException();
		}

		Category managed = catRepo
				.findById(id)
				.orElseThrow(EntityDoesNotExistException::new);
		
		managed.setName(category.getName());
		managed.setEnabled(category.isEnabled());
		return catRepo.saveAndFlush(managed);
	}

}
