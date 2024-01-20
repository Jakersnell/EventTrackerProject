SELECT
    p.id,
    p.name,
    COUNT(pr.id) as review_count
FROM
    product p
    LEFT JOIN product_review pr ON p.id = pr.product_id
    AND pr.enabled = 1
WHERE
    p.enabled = 1
GROUP BY
    p.id
ORDER BY
    review_count ASC;

SELECT
    p
FROM
    Product p
    LEFT JOIN p.reviews pr
WHERE
    p.enabled = true
    AND pr.enabled = true
GROUP BY
    p.id
ORDER BY
    COUNT(pr.id) ASC