package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductService {

	ProductDTO createProduct(ProductDTO productDto) throws DuplicateEntityException;

	ProductDTO updateProduct(ProductDTO productDto, int productId) throws EntityDoesNotExistException;

	void setStatus(int productId, boolean status) throws EntityDoesNotExistException;

}
