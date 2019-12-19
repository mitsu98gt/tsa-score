package com.envisageconsulting.primefaces.scoredaddy.domain;

import java.util.Date;

public class CompetitionResultsRow {

    private String first_name;
    private String last_name;
    private String firearm_model;
    private Date date;
    private String total_x;
    private String total_ten;
    private String total_eight;
    private String total_five;
    private String total_misses;
    private String penalty;
    private String final_score;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getFirearm_model() {
        return firearm_model;
    }

    public void setFirearm_model(String firearm_model) {
        this.firearm_model = firearm_model;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTotal_x() {
        return total_x;
    }

    public void setTotal_x(String total_x) {
        this.total_x = total_x;
    }

    public String getTotal_ten() {
        return total_ten;
    }

    public void setTotal_ten(String total_ten) {
        this.total_ten = total_ten;
    }

    public String getTotal_eight() {
        return total_eight;
    }

    public void setTotal_eight(String total_eight) {
        this.total_eight = total_eight;
    }

    public String getTotal_five() {
        return total_five;
    }

    public void setTotal_five(String total_five) {
        this.total_five = total_five;
    }

    public String getTotal_misses() {
        return total_misses;
    }

    public void setTotal_misses(String total_misses) {
        this.total_misses = total_misses;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getFinal_score() {
        return final_score;
    }

    public void setFinal_score(String final_score) {
        this.final_score = final_score;
    }
}
