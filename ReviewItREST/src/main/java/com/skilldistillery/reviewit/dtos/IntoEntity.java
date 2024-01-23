package com.skilldistillery.reviewit.dtos;

@FunctionalInterface
public interface IntoEntity<T> {
	T intoEntity();
}
