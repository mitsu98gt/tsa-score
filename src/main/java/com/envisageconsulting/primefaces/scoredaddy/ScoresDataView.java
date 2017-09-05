package com.envisageconsulting.primefaces.scoredaddy;

import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean(name="scoresDataView")
@ViewScoped
public class ScoresDataView implements Serializable {

    private List<ScoresDataInput> scores;
    private List<ScoresDataInput> filtered;
    private List<Boolean> list;

    @ManagedProperty("#{scoresDataSource}")
    private ScoresDataSource service;

    @PostConstruct
    public void init() {
        scores = service.getScores();
        list = Arrays.asList(true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
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

    public List<ScoresDataInput> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<ScoresDataInput> filtered) {
        this.filtered = filtered;
    }

    public List<Boolean> getList() {
        return list;
    }

    public void onToggle(ToggleEvent e) {
        list.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }
}
