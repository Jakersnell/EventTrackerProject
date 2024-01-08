package com.skilldistillery.reviewit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.reviewit.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	// SELECT p FROM Product p JOIN p.categories cat WHERE cat.id = ?1;
	List<Product> findByCategoriesId(int catId);
}


