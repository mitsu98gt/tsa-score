package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResultsAverage;

import java.util.Comparator;

public class CompetitionResultsComparator implements Comparator<CompetitionResultsAverage> {

    public int compare(CompetitionResultsAverage o1, CompetitionResultsAverage o2) {
        int value1 = o2.getAverage_score().compareTo(o1.getAverage_score());
        if (value1 == 0) {
            int value2 = o2.getTotal_x().compareTo(o1.getTotal_x());
            if (value2 == 0) {
                return o2.getLast_name().compareTo(o1.getLast_name());
            } else {
                return value2;
            }
        }
        return value1;
    }

}
