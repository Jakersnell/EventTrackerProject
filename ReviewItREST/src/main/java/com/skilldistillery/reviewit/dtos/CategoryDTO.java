package com.skilldistillery.reviewit.dtos;


import com.skilldistillery.reviewit.entities.Category;

import jakarta.validation.constraints.NotBlank;

public class CategoryDTO implements IntoEntity<Category> {
	private int id;
	
	@NotBlank
	private String name;

	public CategoryDTO() {
	}

	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
	
	@Override
	public Category intoEntity() {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		return category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CategoryDTO [id=" + id + ", name=" + name + "]";
	}
	
	
}
