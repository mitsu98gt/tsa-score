package com.envisageconsulting.primefaces.scoredaddy.domain;

public class CompetitorRank {

    private int competition_results_id;
    private int final_score;
    private int total_x;
    private int rank;

    public int getCompetition_results_id() {
        return competition_results_id;
    }

    public void setCompetition_results_id(int competition_results_id) {
        this.competition_results_id = competition_results_id;
    }

    public int getFinal_score() {
        return final_score;
    }

    public void setFinal_score(int final_score) {
        this.final_score = final_score;
    }

    public int getTotal_x() {
        return total_x;
    }

    public void setTotal_x(int total_x) {
        this.total_x = total_x;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
