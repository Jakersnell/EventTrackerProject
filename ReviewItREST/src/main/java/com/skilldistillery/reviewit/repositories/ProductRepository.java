package com.skilldistillery.reviewit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.reviewit.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
