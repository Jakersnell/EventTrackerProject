SELECT
    p.id,
    p.name,
    (0.7 * AVG(pr.rating) + 0.3 * COUNT(pr.id)) AS weighted_score
FROM
    product p
    LEFT JOIN product_review pr ON p.id = pr.product_id
WHERE
    p.enabled = 1
    AND pr.enabled = 1
GROUP BY
    p.id,
    p.name
ORDER BY
    weighted_score DESC;