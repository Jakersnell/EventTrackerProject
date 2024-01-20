package com.skilldistillery.reviewit.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.reviewit.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByCategoriesId(int catId);

	@Query("SELECT p FROM Product p WHERE p.enabled = true")
	Page<Product> pageAll(Pageable pageable);

	@Query("""
			SELECT
				DISTINCT p
			FROM
				Product p
				LEFT JOIN p.reviews pr ON pr.enabled = true
				LEFT JOIN p.categories c
			WHERE
				p.enabled = true
				AND (
					:discontinued IS NULL
					OR p.discontinued = :discontinued
				)
			GROUP BY
				p.id
			ORDER BY
				CASE
					WHEN :sortBy = 'POPULARITY'
					AND :orderBy = 'DESC' THEN 0.7 * AVG(pr.rating) + 0.3 * COUNT(pr.id)
				END DESC,
				CASE
					WHEN :sortBy = 'POPULARITY'
					AND :orderBy = 'ASC' THEN 0.7 * AVG(pr.rating) + 0.3 * COUNT(pr.id)
				END ASC,
				CASE
					WHEN :sortBy = 'M_REVIEWS'
					AND :orderBy = 'DESC' THEN COUNT(pr.id)
				END DESC,
				CASE
					WHEN :sortBy = 'M_REVIEWS'
					AND :orderBy = 'ASC' THEN COUNT(pr.id)
				END ASC
						""")
	Page<Product> getPage(Pageable pageable, @Param("sortBy") String sortBy, @Param("orderBy") String orderBy,
			@Param("discontinued") Boolean discontinued);

}
