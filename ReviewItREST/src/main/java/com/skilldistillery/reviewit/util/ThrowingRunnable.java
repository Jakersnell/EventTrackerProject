package com.skilldistillery.reviewit.util;

import com.skilldistillery.reviewit.exceptions.RestServerException;

@FunctionalInterface
public interface ThrowingRunnable {
	void run() throws RestServerException;
}
