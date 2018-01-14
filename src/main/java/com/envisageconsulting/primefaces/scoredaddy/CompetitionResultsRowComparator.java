package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResultsAverage;
import com.envisageconsulting.primefaces.scoredaddy.domain.CompetitionResultsRow;

import java.util.Comparator;

public class CompetitionResultsRowComparator implements Comparator<CompetitionResultsRow> {

    public int compare(CompetitionResultsRow o1, CompetitionResultsRow o2) {
        int value1 = o2.getFinal_score().compareTo(o1.getFinal_score());
        if (value1 == 0) {
            int value2 = o2.getTotal_x().compareTo(o1.getTotal_x());
            if (value2 == 0) {
                return o2.getTotal_eight().compareTo(o1.getTotal_eight());
            } else {
                return value2;
            }
        }
        return value1;
    }

}
