package com.skilldistillery.reviewit.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.reviewit.dtos.CategoryDTO;
import com.skilldistillery.reviewit.dtos.PageDTO;
import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.services.CategoryQueryService;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping({ "api/categories" })
@RestController
public class PublicCategoryController {

	@Autowired
	private CategoryQueryService cqs;

	@GetMapping
	private ResponseEntity<PageDTO<CategoryDTO>> getAll(@RequestParam(name = "pageNum") int pageNum,
			@RequestParam(name = "pageSize") int pageSize,
			@RequestParam(name = "searchQuery", required = false) String searchQuery,
			@RequestParam(name = "excludedCategories", required = false) Set<Category> excludedCategories,
			HttpServletResponse res) {
		PageDTO<CategoryDTO> pageDto = cqs.getPageOfCategories(pageNum, pageSize, searchQuery, excludedCategories, true);
		return ResponseEntity.ok(pageDto);
	}

}
