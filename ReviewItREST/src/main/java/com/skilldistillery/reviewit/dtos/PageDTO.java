package com.skilldistillery.reviewit.dtos;

import java.util.List;

import org.springframework.data.domain.Page;

/// Not a necessary class but a quality of life thing nevertheless
public class PageDTO<T> {

	private List<T> content;

	private int pageNum;

	private int pageSize;

	private int numPages;

	private int numElements;

	public PageDTO() {
	}

	public PageDTO(Page<T> page) {
		content = page.getContent();
		pageNum = page.getPageable().getPageNumber();
		pageSize = page.getPageable().getPageSize();
		numElements = page.getNumberOfElements();
	}

	public List<T> getContent() {
		return content;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getNumPages() {
		return numPages;
	}

	public int getNumElements() {
		return numElements;
	}

}
