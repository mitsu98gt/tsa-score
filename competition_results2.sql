select 
    max(cr.date) as current_results_date,
    min(cr.date) as previous_results_date,
    (select target_one_x + target_two_x from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 
    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as current_x,
    (select target_one_ten + target_two_ten from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 
    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as current_ten,
	(select target_one_eight + target_two_eight from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 
    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as current_eight,
    (select target_one_five + target_two_five from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 
    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as current_five,
    (select target_one_misses + target_two_misses from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 
    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as current_misses,
    (select penalty from scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1 
		and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as current_penalty,
    (select final_score from scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1 
		and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as current_score,
	(select final_score from scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1 
		and date = (select min(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as previous_score,
	(select target_one_x + target_two_x from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 
    and date = (select min(date) from Scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and stock_division = 1)) as previous_x,
    (select round(avg(final_score),1) from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 and id in ('1','2')) as average_score,
    (select sum(target_one_x) + sum(target_two_x) from scoredaddy.competition_results where competitor_id = 1 and stock_division = 1 and id in ('1','2')) as total_x,
    max(cm.first_name) as first_name,
    max(cm.last_name) as last_name,
    max(fm.model) as firearm_model,
    max(ac.name) as account_name,
    max(comp.description) as competition_description
from 
	scoredaddy.competition_results cr, 
	scoredaddy.competitor cm, 
	scoredaddy.firearm_models fm, 
	scoredaddy.competition comp, 
	scoredaddy.account ac 
where 
	cr.competitor_id = 1
and cr.stock_division = 1 
and cr.id in ('1', '2')
and cr.competitor_id = cm.id 
and cr.firearm_id = fm.id 
and cr.id = comp.id 
and comp.account_id = ac.id 
group by cr.competitor_id
;

select count(*) from scoredaddy.competition_results where competitor_id = 1 and id in (1, 2) and stock_division = 1;