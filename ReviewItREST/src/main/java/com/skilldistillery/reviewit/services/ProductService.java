package com.skilldistillery.reviewit.services;

import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;

public interface ProductService {

	Product createProduct(ProductDTO productDto);

	Product updateProduct(ProductDTO productDto) throws EntityDoesNotExistException;

	void setStatus(int productId, boolean status) throws EntityDoesNotExistException;

}
