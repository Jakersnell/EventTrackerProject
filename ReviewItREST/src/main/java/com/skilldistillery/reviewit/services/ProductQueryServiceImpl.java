package com.skilldistillery.reviewit.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;
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
	public List<Category> getCategoriesForProduct(int productId, boolean enabled) throws RestServerException {
		Product product = productRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);

		if (!product.isEnabled() && enabled) {
			throw new EntityDoesNotExistException();
		}
		
		List<Category> categories = catRepo.getByProductsId(productId);

		if (enabled) {
			categories = categories.stream().filter(Category::isEnabled).toList();
		}
		return categories;
	}

	@Override
	public Product getProductById(int id, boolean enabled) throws EntityDoesNotExistException {
		Product product = productRepo.findById(id).orElseThrow(EntityDoesNotExistException::new);

		if (!product.isEnabled() && enabled) {
			throw new EntityDoesNotExistException();
		}

		return product;
	}

	@Override
	public Page<Product> getPageOfProducts(int pageNum, int pageSize, String searchQuery, String groupBy,
			String orderBy, Boolean discontinued, Double minRating, Set<Category> categories, Boolean enabled) {
		Sort sort = getSort(groupBy, orderBy);
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		Page<Product> products = productRepo.getPage(enabled, searchQuery, discontinued, minRating, categories,
				pageable);

		return products;

	}

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
