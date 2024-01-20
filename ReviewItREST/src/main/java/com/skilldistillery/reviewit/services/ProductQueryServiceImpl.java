package com.skilldistillery.reviewit.services;

import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;

@Service
public class ProductQueryServiceImpl implements ProductQueryService {

	@Override
	public Page<Product> getPageOfProducts(int page, int pageSize, String orderBy, String sortBy, Boolean discontinued,
			Double minRating, Set<Category> categories) {
		// TODO Auto-generated method stub
		return null;
	}

}
