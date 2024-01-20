package com.skilldistillery.reviewit.dtos;

import java.util.Arrays;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.skilldistillery.reviewit.entities.Category;


public class ProductQueryDTO {

	private Pageable pageable;

	private Boolean continued;

	private Set<Category> categories;

	public ProductQueryDTO() {
	}

	public ProductQueryDTO(int pageSize, int pageNum, String sortBy, String orderBy, Set<Category> categories) {

	}

//	private Sort getSort(String sortBy, String orderBy) {
//		SortBy selection = SortBy.matchName(sortBy);
//		Sort sort;
//		switch (selection) {
//		case POPULARITY:
//			sort = Sort.by("")
//		}
//		return null;
//	}

	public enum SortBy {
		POPULARITY("popularity"), NUM_REVIEWS("reviews");

		private String name;

		private SortBy(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public static SortBy matchName(String name) {
			return Arrays.stream(values()).filter((value) -> value.name.equals(name)).findFirst().orElse(null);
		}

	}

}
