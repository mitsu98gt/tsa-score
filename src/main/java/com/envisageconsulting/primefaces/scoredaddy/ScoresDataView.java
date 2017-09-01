package com.envisageconsulting.primefaces.scoredaddy;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="scoresDataView")
@ViewScoped
public class ScoresDataView implements Serializable {

    private List<ScoresDataInput> scores;

    @ManagedProperty("#{scoresDataSource}")
    private ScoresDataSource service;

    @PostConstruct
    public void init() {
        scores = service.getScores();
    }

    public List<ScoresDataInput> getScores() {
        return scores;
    }

    public void setScores(List<ScoresDataInput> scores) {
        this.scores = scores;
    }

    public ScoresDataSource getService() {
        return service;
    }

    public void setService(ScoresDataSource service) {
        this.service = service;
    }
}
