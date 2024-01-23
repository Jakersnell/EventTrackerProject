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

	boolean existsByName(String name);

	List<Category> getByProductsId(int productId);

	@Query("""
			SELECT
			DISTINCT
			    c
			FROM
			    Category c
			    LEFT JOIN c.products p
			WHERE
			    (
			        :excludedCategories IS NULL
			        OR c NOT IN :excludedCategories
			    )
			    AND (
			        :searchQuery IS NULL
			        OR c.name LIKE %:searchQuery%
			    )
			    AND (
			        :enabled IS NULL
			        OR c.enabled = :enabled
			    )
			GROUP BY
				c
			ORDER BY
				COUNT(p) DESC

			""")
	Page<Category> getPage(@Param("searchQuery") String searchQuery, @Param("enabled") Boolean enabled,
			@Param("excludedCategories") Set<Category> excludedCategories, Pageable pageable);
}
