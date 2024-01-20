use reviewitdb;

SELECT
    p.id,
    p.name
FROM
    product p
    JOIN product_category pc ON p.id = pc.product_id
    JOIN product_review pr ON p.id = pr.product_id
WHERE
    pc.category_id = 1
GROUP BY
    p.id
ORDER BY
    COUNT(pr.id) DESC;