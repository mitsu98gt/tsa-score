package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "scoresDataSource")
@SessionScoped
public class ScoresDataSource {

    public List<ScoresDataInput> scores = new ArrayList<ScoresDataInput>();

    public ScoresDataSource() {

        ScoresDataInput data1 = new ScoresDataInput();
        data1.setRank("1");
        data1.setFirstName("Vinh");
        data1.setLastName("Dang");
        data1.setModel("G34");
        data1.setCurrentX("50");
        data1.setCurrent10("0");
        data1.setCurrent8("0");
        data1.setCurrent5("0");
        data1.setCurrentMisses("0");
        data1.setCurrentPenalty("0");
        data1.setCurrentScore("500");
        data1.setFirstScore("500");
        data1.setFirstX("50");
        data1.setPreviousScore("500");
        data1.setPreviousX("50");
        data1.setTopScore("500");
        data1.setTopX("50");
        data1.setSecondTopScore("500");
        data1.setSecondTopX("50");
        data1.setAverageScore("500");
        data1.setTotalX("50");
        scores.add(data1);

        ScoresDataInput data2 = new ScoresDataInput();
        data2.setRank("2");
        data2.setFirstName("Heather");
        data2.setLastName("Dang");
        data2.setModel("G17");
        data2.setCurrentX("50");
        data2.setCurrent10("0");
        data2.setCurrent8("0");
        data2.setCurrent5("0");
        data2.setCurrentMisses("0");
        data2.setCurrentPenalty("0");
        data2.setCurrentScore("500");
        data2.setFirstScore("500");
        data2.setFirstX("50");
        data2.setPreviousScore("500");
        data2.setPreviousX("50");
        data2.setTopScore("500");
        data2.setTopX("50");
        data2.setSecondTopScore("500");
        data2.setSecondTopX("50");
        data2.setAverageScore("500");
        data2.setTotalX("50");
        scores.add(data2);

        ScoresDataInput data3 = new ScoresDataInput();
        data3.setRank("3");
        data3.setFirstName("Brandon");
        data3.setLastName("Dang");
        data3.setModel("G19");
        data3.setCurrentX("50");
        data3.setCurrent10("0");
        data3.setCurrent8("0");
        data3.setCurrent5("0");
        data3.setCurrentMisses("0");
        data3.setCurrentPenalty("0");
        data3.setCurrentScore("500");
        data3.setFirstScore("500");
        data3.setFirstX("50");
        data3.setPreviousScore("500");
        data3.setPreviousX("50");
        data3.setTopScore("500");
        data3.setTopX("50");
        data3.setSecondTopScore("500");
        data3.setSecondTopX("50");
        data3.setAverageScore("500");
        data3.setTotalX("50");
        scores.add(data3);
    }

    public List<ScoresDataInput> getScores() {
        return scores;
    }

    public void setScores(List<ScoresDataInput> scores) {
        this.scores = scores;
    }
}
