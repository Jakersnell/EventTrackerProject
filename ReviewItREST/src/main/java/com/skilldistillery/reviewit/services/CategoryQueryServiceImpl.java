package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.dtos.ProductDTO;
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
	public PageDTO<CategoryDTO> getPageOfCategories(int pageNum, int pageSize, String searchQuery,
			Set<Category> excludedCategories, Boolean enabled) {
		Pageable pageable = PageRequest.of(pageNum, pageSize);
		Page<Category> page = catRepo.getPage(searchQuery, enabled, excludedCategories, pageable);
		List<CategoryDTO> categories = page.getContent().stream().map(CategoryDTO::new).toList();
		return new PageDTO<>(page, categories, searchQuery);
	}

	@Override
	public CategoryDTO getCategoryById(int categoryId) throws EntityDoesNotExistException {
		Category category = catRepo.findById(categoryId).filter(Category::isEnabled)
				.orElseThrow(EntityDoesNotExistException::new);
		return new CategoryDTO(category);
	}

	@Override
	public List<ProductDTO> getProductsByCategoryId(int categoryId) throws EntityDoesNotExistException {
		if (!catRepo.existsById(categoryId)) {
			throw new EntityDoesNotExistException();
		}
		return productRepo.findByCategoriesId(categoryId).stream().filter(Product::isEnabled).map(ProductDTO::new)
				.toList();
	}

}
