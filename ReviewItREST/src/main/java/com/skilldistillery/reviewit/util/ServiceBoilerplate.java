package com.skilldistillery.reviewit.util;

import org.springframework.data.jpa.repository.JpaRepository;

public final class ServiceBoilerplate {
	public static <ObjectType, IdType> boolean delete(JpaRepository<ObjectType, IdType> repo, IdType id) {
		boolean success = false;
		if (repo.existsById(id)) {
			repo.deleteById(id);
			success = true;
		}
		return success;
	}
}
