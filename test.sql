SELECT
    p,
    (0.7 * AVG(pr.rating) + 0.3 * COUNT(pr.id)) AS weighted_score
FROM
    Product p
    LEFT JOIN FETCH p.reviews pr
    LEFT JOIN FETCH p.categories c
WHERE
    p.enabled = true
    AND pr.enabled = true
    AND (:discontinued IS NULL OR p.discontinued = :discontinued)
    AND (:categories IS NULL OR c IN :categories)
GROUP BY
    p.id,
    p.name
ORDER BY
    weighted_score DESC
