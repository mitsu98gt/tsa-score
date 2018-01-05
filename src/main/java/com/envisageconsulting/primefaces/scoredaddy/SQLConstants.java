package com.envisageconsulting.primefaces.scoredaddy;

public class SQLConstants {

    public static final String STOCK_DIVISION = "cr.stock_division = 1";
    public static final String UNLIMITED_DIVISION = "cr.unlimited_division = 1";
    public static final String POCKET_DIVISION = "cr.pocket_division = 1";
    public static final String WOMAN_DIVISION = "cr.woman_division = 1";
    public static final String SENIOR_DIVISION = "cr.senior_division = 1";
    public static final String JUNIOR_DIVISION = "cr.junior_division = 1";

    public static final String COMPETITION_RESULTS_QUERY_BY_DIVISION_AND_COMPETITION_ID =
            "select" +
            "    cr.id," +
            "    cr.code," +
            "    cr.date," +
            "    cr.competitor_id," +
            "    cr.firearm_id," +
            "    cr.target_one_x," +
            "    cr.target_one_ten," +
            "    cr.target_one_eight," +
            "    cr.target_one_five," +
            "    cr.target_one_misses," +
            "    cr.target_two_x," +
            "    cr.target_two_ten," +
            "    cr.target_two_eight," +
            "    cr.target_two_five," +
            "    cr.target_two_misses," +
            "    cr.penalty," +
            "    cr.final_score," +
            "    cr.total_x," +
            "    cm.first_name," +
            "    cm.last_name," +
            "    fm.model," +
            "    comp.description," +
            "    ac.name" +
            " from" +
            "   scoredaddy.competition_results cr," +
            "   scoredaddy.competitor cm," +
            "   scoredaddy.firearm_models fm," +
            "   scoredaddy.competition comp," +
            "   scoredaddy.account ac" +
            " where" +
            "   cr.id = ?" +
            " and %s" + // cr.stock_division = 1
            " and cr.competitor_id = cm.id" +
            " and cr.firearm_id = fm.id" +
            " and cr.id = comp.id" +
            " and comp.account_id = ac.id" +
            " order by" +
            "   final_score desc, total_x desc";

    public static final String COMPETITION_RESULTS_QUERY_AVERAGE_TWO_COMPETITIONS_BY_COMPETITOR_ID =
            "select \n" +
            "    max(cr.date) as current_results_date,\n" +
            "    min(cr.date) as previous_results_date,\n" +
            "    (select target_one_x + target_two_x from scoredaddy.competition_results where competitor_id = 1 and " +
            "stock_division = 1 \n" +
            "    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as current_x,\n" +
            "    (select target_one_ten + target_two_ten from scoredaddy.competition_results where competitor_id = 1 " +
            "and stock_division = 1 \n" +
            "    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as current_ten,\n" +
            "\t(select target_one_eight + target_two_eight from scoredaddy.competition_results where competitor_id = " +
            "1 and stock_division = 1 \n" +
            "    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as current_eight,\n" +
            "    (select target_one_five + target_two_five from scoredaddy.competition_results where competitor_id = " +
            "1 and stock_division = 1 \n" +
            "    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as current_five,\n" +
            "    (select target_one_misses + target_two_misses from scoredaddy.competition_results where " +
            "competitor_id = 1 and stock_division = 1 \n" +
            "    and date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as current_misses,\n" +
            "    (select penalty from scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 and " +
            "stock_division = 1 \n" +
            "\t\tand date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as current_penalty,\n" +
            "    (select final_score from scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 " +
            "and stock_division = 1 \n" +
            "\t\tand date = (select max(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as current_score,\n" +
            "\t(select final_score from scoredaddy.competition_results where id in ('1','2') and competitor_id = 1 " +
            "and stock_division = 1 \n" +
            "\t\tand date = (select min(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as previous_score,\n" +
            "\t(select target_one_x + target_two_x from scoredaddy.competition_results where competitor_id = 1 and " +
            "stock_division = 1 \n" +
            "    and date = (select min(date) from Scoredaddy.competition_results where id in ('1','2') and " +
            "competitor_id = 1 and stock_division = 1)) as previous_x,\n" +
            "    (select round(avg(final_score),1) from scoredaddy.competition_results where competitor_id = 1 and " +
            "stock_division = 1 and id in ('1','2')) as average_score,\n" +
            "    (select sum(target_one_x) + sum(target_two_x) from scoredaddy.competition_results where " +
            "competitor_id = 1 and stock_division = 1 and id in ('1','2')) as total_x,\n" +
            "    max(cm.first_name) as first_name,\n" +
            "    max(cm.last_name) as last_name,\n" +
            "    max(fm.model) as firearm_model,\n" +
            "    max(ac.name) as account_name,\n" +
            "    max(comp.description) as competition_description\n" +
            "from \n" +
            "\tscoredaddy.competition_results cr, \n" +
            "\tscoredaddy.competitor cm, \n" +
            "\tscoredaddy.firearm_models fm, \n" +
            "\tscoredaddy.competition comp, \n" +
            "\tscoredaddy.account ac \n" +
            "where \n" +
            "\tcr.competitor_id = 1\n" +
            "and cr.stock_division = 1 \n" +
            "and cr.id in ('1', '2')\n" +
            "and cr.competitor_id = cm.id \n" +
            "and cr.firearm_id = fm.id \n" +
            "and cr.id = comp.id \n" +
            "and comp.account_id = ac.id \n" +
            "group by cr.competitor_id\n" +
            ";\n";

}
