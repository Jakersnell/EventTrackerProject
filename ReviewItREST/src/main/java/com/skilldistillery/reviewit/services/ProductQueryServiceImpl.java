package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.CategoryRepository;
import com.skilldistillery.reviewit.repositories.ProductRepository;
import com.skilldistillery.reviewit.repositories.ProductRepository.PageSort;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository catRepo;

	@Override
	public List<CategoryDTO> getCategoriesForProduct(int productId) throws EntityDoesNotExistException {
		if (!productRepo.existsById(productId)) {
			throw new EntityDoesNotExistException();
		}
		return catRepo.getByProductsId(productId).stream().filter(Category::isEnabled).map(CategoryDTO::new).toList();

	}

	@Override
	public ProductDTO getProductById(int id) throws EntityDoesNotExistException {
		return productRepo.findById(id).filter(Product::isEnabled).map(ProductDTO::new)
				.orElseThrow(EntityDoesNotExistException::new);
	}

	@Override
	public PageDTO<ProductDTO> getPageOfProducts(int pageNum, int pageSize, String searchQuery, String groupBy,
			String orderBy, Boolean discontinued, Double minRating, Set<Category> categories, Boolean enabled) {
		Sort sort = getSort(groupBy, orderBy);
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		Page<Product> page = productRepo.getPage(enabled, searchQuery, discontinued, minRating, categories,
				pageable);
		List<ProductDTO> products = page.getContent().stream().map(ProductDTO::new).toList();
		return new PageDTO<>(page, products, searchQuery);
	}

	// santa's little helper method
	private Sort getSort(String groupBy, String orderBy) {

		if (groupBy == null || orderBy == null) {
			return Sort.unsorted();
		}

		Sort sort;
		switch (groupBy) {
		case "POPULARITY":
			sort = PageSort.byPopularity();
			break;
		case "NUM_REVIEWS":
			sort = PageSort.byMostReviews();
			break;
		case "NONE":
			sort = Sort.unsorted();
		default:
			throw new IllegalArgumentException("Sort order '" + groupBy + "' is not a valid Product sort order.");
		}

		switch (orderBy) {
		case "DESC":
			sort = sort.descending();
			break;
		case "ASC":
			sort = sort.ascending();
			break;
		case "NONE":
			sort = Sort.unsorted();
		default:
			throw new IllegalArgumentException(
					"Sort Direction '" + orderBy + "' is not a valid Product sort direction.");
		}
		return sort;
	}

}
