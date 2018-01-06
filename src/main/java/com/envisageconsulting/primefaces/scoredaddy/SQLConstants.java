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

    public static final String COMPETITION_RESULTS_QUERY_BY_COMPETITON_AND_DIVISION =
            "select" +
            "  max(cm.first_name) as first_name," +
            "  max(cm.last_name) as last_name," +
            "  max(fm.model) as firearm_model," +
            "  max(cr.date) as date," +
            "  sum(cr.target_one_x + cr.target_two_x) as total_x," +
            "  sum(cr.target_one_ten + cr.target_two_ten) as total_ten," +
            "  sum(cr.target_one_eight + cr.target_two_eight) as total_eight," +
            "  sum(cr.target_one_five + cr.target_two_five) as total_five," +
            "  sum(cr.target_one_misses + cr.target_two_misses) as total_misses," +
            "  max(cr.penalty) as penalty," +
            "  max(cr.final_score) as final_score" +
            " from" +
            "  scoredaddy.competition_results cr," +
            "  scoredaddy.competitor cm," +
            "  scoredaddy.firearm_models fm" +
            " where" +
            "  cr.id = ?" + // competition
            " and %s" + // division
            " and cr.competitor_id = cm.id" +
            " and cr.firearm_id = fm.id" +
            " group by" +
            "  cm.first_name";



}
