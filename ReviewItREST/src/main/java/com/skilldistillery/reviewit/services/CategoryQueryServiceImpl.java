package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.CategoryRepository;
import com.skilldistillery.reviewit.repositories.ProductRepository;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private ProductRepository productRepo;

	@Override
	public Page<Category> getPageOfCategories(int pageNum, int pageSize, String searchQuery,
			Set<Category> excludedCategories, Boolean enabled) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		return catRepo.getPage(searchQuery, enabled, excludedCategories, pageable);
	}

	@Override
	public Category getCategoryById(int categoryId) throws EntityDoesNotExistException {
		Category category = catRepo
				.findById(categoryId)
				.orElseThrow(EntityDoesNotExistException::new);
		if (!category.isEnabled()) {
			throw new EntityDoesNotExistException();
		}
		return category;
	}

	@Override
	public List<Product> getProductsByCategoryId(int categoryId) throws EntityDoesNotExistException {
		if (!catRepo.existsById(categoryId)) {
			throw new EntityDoesNotExistException();
		}
		List<Product> products = productRepo
				.findByCategoriesId(categoryId)
				.stream()
				.filter(Product::isEnabled)
				.toList();
		
		return products;
	}

}
