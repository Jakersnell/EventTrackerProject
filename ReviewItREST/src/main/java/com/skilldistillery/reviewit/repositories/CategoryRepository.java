package com.skilldistillery.reviewit.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.reviewit.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	List<Category> getByProductsId(int productId);

	@Query("""
			SELECT
			    c
			FROM
			    Category c
			WHERE
				(
					:excludedCategories IS NULL
					OR c NOT IN :excludedCategories
				)
				AND
			    (
			        :searchQuery IS NULL
			        OR c.name LIKE %:searchQuery%
			    )
			    AND (
			        :enabled IS NULL
			        OR c.enabled = :enabled
			    )
			""")
	Page<Category> getPage(@Param("searchQuery") String searchQuery, @Param("enabled") Boolean enabled,
			@Param("excludedCategories") Set<Category> excludedCategories, Pageable pageable);
}
