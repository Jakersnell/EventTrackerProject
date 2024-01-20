SELECT DISTINCT
    p.id,
    p.name
FROM
    product p
LEFT JOIN
    product_review pr ON pr.product_id = p.id AND pr.enabled = 1
WHERE
    p.enabled = 1
GROUP BY
    p.id
ORDER BY
    COUNT(pr.id) ASC;
