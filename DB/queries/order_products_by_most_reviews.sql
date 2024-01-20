use reviewitdb;

select
    p.id,
    p.name
from
    product p
    left join product_review pr on p.id = pr.product_id
where
    null is null
    or p.id in (
        select
            pr.product_id
        from
            product_review pr
        where
            pr.id = 1
    )
group by
    p.id
order by
    count(pr.id) desc;