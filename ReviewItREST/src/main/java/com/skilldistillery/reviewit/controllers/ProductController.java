package com.skilldistillery.reviewit.controllers;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;
import com.skilldistillery.reviewit.services.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RestController
@RequestMapping({ "api" })
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;

	@GetMapping({ "products" })
	private List<Product> getAll(@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.getAll(auth);
		}, res);

	}

//	@RequestParam("pageSize") int pageSize,
//	@RequestParam("pageNum") int pageNum,
//	@RequestParam(name="continued", required=false) Boolean continued,
	@GetMapping({ "products/testpaging" })
	private List<Object> getPageOfProducts(@RequestParam(name = "pageSize") int pageSize,
			@RequestParam(name = "pageNum") int pageNum,
			@RequestParam(name = "continued", required = false) Boolean continued,
			@RequestParam(name = "categories", required = false) List<Integer> categories,
			@RequestParam(name = "sortBy", required = false, defaultValue = "POP") String sortBy,
			@RequestParam(name = "orderBy", required = false, defaultValue = "DESC") String orderBy) {
		List<Object> params = Arrays.asList(pageSize, pageNum, continued, categories, sortBy, orderBy);
		return params;
	}

	@GetMapping({ "products/{productId}" })
	private Product getProductById(@PathVariable("productId") int productId,
			@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.getProductById(productId, auth);
		}, res);

	}

	@PostMapping({ "products" })
	private Product createProduct(@RequestBody Product product, @RequestParam("auth") String auth,
			HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.createProduct(product, auth);
		}, res);

	}

	@PutMapping({ "products/{productId}" })
	private Product updateProduct(@PathVariable("productId") int productId, @RequestBody Product product,
			@RequestParam("auth") String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.updateProduct(productId, product, auth);
		}, res);

	}

	@DeleteMapping({ "products/{productId}" })
	private void deleteProduct(@PathVariable("productId") int productId, @RequestParam("auth") String auth,
			HttpServletResponse res) {
		System.out.println(auth);
		tryFailableAction(() -> {
			productService.disableProduct(productId, auth);
			res.setStatus(204);
		}, res);

	}

	@GetMapping({ "products/{productId}/categories" })
	private List<Category> getCategoriesForProduct(@PathVariable("productId") int productId,
			@RequestParam(name = "auth", required = false) String auth, HttpServletResponse res) {

		return tryFailableAction(() -> {
			return productService.getCategoriesForProduct(productId, auth);
		}, res);

	}

}
