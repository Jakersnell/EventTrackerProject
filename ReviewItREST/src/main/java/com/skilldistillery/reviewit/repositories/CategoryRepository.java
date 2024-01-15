package com.skilldistillery.reviewit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.reviewit.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> getByProductsId(int productId);
}
