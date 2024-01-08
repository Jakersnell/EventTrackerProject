package com.skilldistillery.reviewit.util;

@FunctionalInterface
public interface ThrowingSupplier<T> {
	T get() throws Exception;
}
