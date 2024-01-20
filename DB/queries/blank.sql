use reviewitdb;

select
	p.id,
	p.name
from
	product p

where
	(
		null is null
		or p.id in (
			select 
				pr.product_id
			from
				product_review pr
			where
				pr.id = 1
		)
	)
	and
	(
		null is null
		or p.id in (
			select
				pc.product_id
			from
				product_category pc
			where
				pc.category_id = 1
		)
	)
limit
	1;