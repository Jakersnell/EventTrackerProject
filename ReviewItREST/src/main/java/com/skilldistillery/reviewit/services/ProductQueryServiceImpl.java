package com.skilldistillery.reviewit.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.repositories.ProductRepository;
import com.skilldistillery.reviewit.repositories.ProductRepository.PageSort;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

	@Autowired
	private ProductRepository productRepo;
	
	@Override
	public PageDTO<Product> getPageOfProducts(int pageNum, int pageSize, String groupBy, String orderBy, Boolean discontinued,
			Double minRating, Set<Category> categories) {
		
		Sort sort = getSort(groupBy, orderBy);
		Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
		Page<Product> page = productRepo.getPage(discontinued, minRating, categories, pageable);
		return new PageDTO<>(page);
		
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
		case "MOST_REVIEWS":
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
