select 
	 cr.competitor_id,
     cr.firearm_id,
     round(avg(cr.final_score),1) as final_score,
     count(*) as occurrences
from 
     competition_results cr
where 
     cr.id in (25,26)
and  cr.additional_entry = false
and  cr.stock_division
group by cr.competitor_id, cr.firearm_id
having count(*) > 1
order by final_score desc
;

select avg(scores) as average
from (
	select 
	   final_score as scores 
	from 
	   competition_results 
	where 
	   competitor_id = 138
	and id in (25, 26, 27) 
	and stock_division 
	and additional_entry = false 
	order by final_score desc limit 2
) competition_results
;

-- Main Query
select
    c.first_name,
    c.last_name,
    fm.model,
    cr.competitor_id,
    cr.firearm_id,
	round((select(avg(final_score))
       from 
	      competition_results
	   where 
	      competitor_id = cr.competitor_id
	   and id in (25,26,27) 
	   and stock_division 
	   and additional_entry = false
	   order by final_score desc limit 2
    ),1) as final_average
    /*(select(sum(total_x))
       from 
	      competition_results 
	   where 
	      competitor_id = cr.competitor_id
	   and id in (22,23,24) 
	   and stock_division 
	   and additional_entry = false 
	   order by final_score desc limit 2
    ) as total_x */
from 
     competition_results cr,
     competitor c,
     firearm_models fm
where 
     cr.id in (25,26,27)
and  cr.additional_entry = false
and  cr.stock_division
and  cr.competitor_id = c.id
and  cr.firearm_id = fm.id
group by 
     cr.competitor_id,
     cr.firearm_id
having count(*) > 1
order by final_average desc-- , total_x desc
;

-- Average Top 2 X's
select sum(total_x) as total_x
from (
	select 
	   total_x as total_x 
	from 
	   competition_results 
	where 
	   competitor_id = 135
	and id in (22, 23, 24) 
	and stock_division 
	and additional_entry = false 
	order by final_score desc limit 2
) total_x
;


-- Average Top 2 Scores
select     
   cr.first_name,
   cr.last_name,
   cr.competitor_id, 
   cr.firearm_model,
   round(avg(cr.final_score),1) average 
from       
   (select 
      c.first_name as first_name,
      c.last_name as last_name,
      cr.competitor_id,
      fm.model as firearm_model,
      cr.final_score
	from 
      competition_results cr,
      competitor c,
      firearm_models fm
	where 
	    cr.id in(22,23,24)
	and cr.additional_entry = false
	and cr.stock_division
    and cr.competitor_id = c.id
    and cr.firearm_id = fm.id
    and (select count(*)
           from competition_results
           where competitor_id = cr.competitor_id
           and final_score >= cr.final_score
           and id in(22,23,24)
           and additional_entry = false
           and stock_division
		)<=2
   ) cr
group by cr.first_name,
	     cr.last_name,
         cr.competitor_id,
         cr.firearm_model
having count(*) >= 2
order by average desc 
;
   