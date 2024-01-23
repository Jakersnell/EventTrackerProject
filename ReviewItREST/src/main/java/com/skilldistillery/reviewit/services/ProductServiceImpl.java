package com.skilldistillery.reviewit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.dtos.ProductDTO;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Override
	public Product createProduct(ProductDTO productDto) {
		Product product = productDto.intoEntity();
		product.setId(0);
		return productRepo.save(product);
	}

	@Override
	public Product updateProduct(ProductDTO productDto, int productId) throws EntityDoesNotExistException {
		Product product = productRepo
				.findById(productId)
				.orElseThrow(EntityDoesNotExistException::new);
		product.setName(product.getName());
		product.setDescription(product.getDescription());
		product.setImageUrl(productDto.getImageUrl());
		product.setUsMsrp(productDto.getUsMsrp());
		return productRepo.saveAndFlush(product);
	}

	@Override
	public void setStatus(int productId, boolean status) throws EntityDoesNotExistException {
		Product product = productRepo
				.findById(productId)
				.orElseThrow(EntityDoesNotExistException::new);
		product.setEnabled(status);
		productRepo.saveAndFlush(product);
	}

}
