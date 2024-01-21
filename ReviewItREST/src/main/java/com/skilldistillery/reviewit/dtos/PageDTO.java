package com.skilldistillery.reviewit.dtos;

import java.util.List;

import org.springframework.data.domain.Page;

/// Not a necessary class but a quality of life thing nevertheless
/// this mainly exists to limit unnecessary serialization of excess data
/// from the Page entity
public class PageDTO<T> {

	private int pageNum;

	private int pageSize;

	private int numElements;

	private int totalNumPages;

	private long totalNumElements;

	private String searchQuery;

	private List<T> content;

	public PageDTO() {
	}

	public PageDTO(Page<T> page) {
		pageNum = page.getPageable().getPageNumber();
		pageSize = page.getPageable().getPageSize();
		numElements = page.getNumberOfElements();
		totalNumPages = page.getTotalPages();
		totalNumElements = page.getTotalElements();
		content = page.getContent();
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNumElements() {
		return numElements;
	}

	public void setNumElements(int numElements) {
		this.numElements = numElements;
	}

	public int getTotalNumPages() {
		return totalNumPages;
	}

	public void setTotalNumPages(int totalNumPages) {
		this.totalNumPages = totalNumPages;
	}

	public long getTotalNumElements() {
		return totalNumElements;
	}

	public void setTotalNumElements(long totalNumElements) {
		this.totalNumElements = totalNumElements;
	}

	public String getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(String searchQuery) {
		this.searchQuery = searchQuery;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

}
