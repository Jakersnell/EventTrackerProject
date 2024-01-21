package com.skilldistillery.reviewit.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.reviewit.entities.Category;
import com.skilldistillery.reviewit.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	public static final class PageSort {

		public static Sort byPopularity() {
			return JpaSort.unsafe("0.7 * AVG(pr.rating) + 0.3 * COUNT(pr.id)");
		}

		public static Sort byMostReviews() {
			return JpaSort.unsafe("COUNT(pr.id)");
		}

	}

	List<Product> findByCategoriesId(int catId);

	@Query("SELECT p FROM Product p WHERE p.enabled = true")
	Page<Product> pageAll(Pageable pageable);

	@Query("""
			SELECT
			    p
			FROM
			    Product p
			    LEFT JOIN p.reviews pr
			WHERE
				(
					:searchQuery IS NULL
					OR p.name LIKE %:searchQuery%
				)
				AND
			    (
			        :discontinued IS NULL
			        OR p.discontinued = :discontinued
			    )
			    AND (
			        :categories IS NULL
			        OR EXISTS (
			            SELECT
			                c
			            FROM
			                Category c
			            WHERE
			                c MEMBER OF p.categories
			                AND c IN (:categories)
			        )
			    )
			GROUP BY
			    p
			HAVING
			    :minRating IS NULL
			    OR AVG(pr.rating) >= :minRating
			""") // make the db pull its weight lol
	Page<Product> getPage(@Param("searchQuery") String searchQuery, @Param("discontinued") Boolean discontinued,
			@Param("minRating") Double minRating, @Param("categories") Set<Category> categories, Pageable pageable);

}
