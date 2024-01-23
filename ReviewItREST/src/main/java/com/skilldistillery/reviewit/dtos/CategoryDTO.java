package com.skilldistillery.reviewit.dtos;

import com.skilldistillery.reviewit.entities.Category;

public class CategoryDTO {
	private int id;

	private String name;

	public CategoryDTO() {
	}

	public CategoryDTO(Category category) {
		this.id = category.getId();
		this.name = category.getName();
	}
	
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
