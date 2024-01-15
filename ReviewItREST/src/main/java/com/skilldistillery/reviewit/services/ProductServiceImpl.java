package com.skilldistillery.reviewit.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.exceptions.BadRequestException;
import com.skilldistillery.reviewit.exceptions.EntityDoesNotExistException;
import com.skilldistillery.reviewit.exceptions.RestServerException;
import com.skilldistillery.reviewit.exceptions.TokenInvalidException;
import com.skilldistillery.reviewit.repositories.CategoryRepository;
import com.skilldistillery.reviewit.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepository productRepo;

	@Autowired
	private CategoryRepository catRepo;

	@Autowired
	private AuthService authService;

	@Override
	public List<Product> getAll(String auth) {
		List<Product> products = productRepo.findAll();
		if (!authService.userIsAdmin(auth)) {
			products = products.stream().filter(Product::isEnabled).toList();
		}
		return products;
	}

	@Override
	public Product getProductById(int id, String auth) throws EntityDoesNotExistException {
		Product product = productRepo.findById(id).orElseThrow(EntityDoesNotExistException::new);

		if (!product.isEnabled() && !authService.userIsAdmin(auth)) {
			throw new EntityDoesNotExistException();
		}

		return product;
	}

	@Override
	public Product createProduct(Product product, String auth) throws RestServerException {
		if (!authService.userIsAdmin(auth)) {
			throw new TokenInvalidException();
		}

		if (product.getName() == null || product.getName().isBlank()) {
			throw new BadRequestException();
		}

		product.setId(0);

		return productRepo.save(product);
	}

	@Override
	public Product updateProduct(int productId, Product product, String auth) throws RestServerException {
		if (!authService.userIsAdmin(auth)) {
			throw new TokenInvalidException();
		}

		Product managed = productRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);

		if (product.getName() == null || product.getName().isBlank()) {
			throw new BadRequestException();
		}

		managed.setName(product.getName());
		managed.setDescription(product.getDescription());

		if (product.isEnabled()) {
			managed.setEnabled(product.isEnabled());
		}

		return productRepo.saveAndFlush(managed);
	}

	@Override
	public void disableProduct(int productId, String auth) throws RestServerException {
		if (!authService.userIsAdmin(auth)) {
			throw new TokenInvalidException();
		}
		Product product = productRepo.findById(productId).orElseThrow(EntityDoesNotExistException::new);
		product.setEnabled(false);
		productRepo.saveAndFlush(product);
	}

	@Override
	public List<Product> getProductsByCategoryId(int categoryId, String auth) throws RestServerException {
		if (!catRepo.existsById(categoryId)) {
			throw new EntityDoesNotExistException();
		}
		List<Product> products = productRepo.findByCategoriesId(categoryId);
		if (!authService.userIsAdmin(auth)) {
			products = products.stream().filter(Product::isEnabled).toList();
		}
		return products;
	}

	@Override
	public List<Category> getCategoriesForProduct(int productId, String auth) throws RestServerException {
		if (!productRepo.existsById(productId)) {
			throw new EntityDoesNotExistException();
		}
		List<Category> categories = catRepo.getByProductsId(productId);
		if (!authService.userIsAdmin(auth)) {
			categories = categories.stream().filter(Category::isEnabled).toList();
		}
		return categories;
	}
}
