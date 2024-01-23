package com.skilldistillery.reviewit.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.DuplicateEntityException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Override
	public ProductDTO createProduct(ProductDTO productDto) throws DuplicateEntityException {
		if (productRepo.existsByName(productDto.getName())) {
			Map<String, String> errors = new HashMap<>();
			errors.put("Name", "A product with that name already exists.");
			throw new DuplicateEntityException(errors);
		}
		Product product = productDto.intoEntity();
		product.setId(0);
		product = productRepo.save(product);
		return new ProductDTO(product);
	}

	@Override
	public ProductDTO updateProduct(ProductDTO productDto, int productId) throws EntityDoesNotExistException {
		Product product = productRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);
		product.setName(product.getName());
		product.setDescription(product.getDescription());
		product.setImageUrl(productDto.getImageUrl());
		product.setUsMsrp(productDto.getUsMsrp());
		product = productRepo.saveAndFlush(product);
		return new ProductDTO(product);
	}

	@Override
	public void setStatus(int productId, boolean status) throws EntityDoesNotExistException {
		Product product = productRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);
		product.setEnabled(status);
		productRepo.saveAndFlush(product);
	}

}
