package com.skilldistillery.reviewit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.repositories.ProductRepository;
import com.skilldistillery.reviewit.util.ServiceBoilerplate;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Override
	public List<Product> getAll() {
		return productRepo.findAll();
	}

	@Override
	public Product getProductById(int id) {
		return productRepo.findById(id).orElse(null);
	}

	@Override
	public Product createProduct(Product product) {
		return productRepo.save(product);
	}

	@Override
	public Product updateProduct(int productId, Product product) {
		Product managed = productRepo.findById(productId).orElse(null);

		if (managed != null) {
			managed.setName(product.getName());
			managed.setDescription(product.getDescription());
			productRepo.saveAndFlush(managed);
		}

		return managed;
	}

	@Override
	public boolean deleteProduct(int productId) {
		return ServiceBoilerplate.delete(productRepo, productId);
	}
}
