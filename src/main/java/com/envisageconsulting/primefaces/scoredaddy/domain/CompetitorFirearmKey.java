package com.envisageconsulting.primefaces.scoredaddy.domain;

import java.util.Objects;

public class CompetitorFirearmKey {

    private int competitor_id;
    private int firearm_id;

    public int getCompetitor_id() {
        return competitor_id;
    }

    public void setCompetitor_id(int competitor_id) {
        this.competitor_id = competitor_id;
    }

    public int getFirearm_id() {
        return firearm_id;
    }

    public void setFirearm_id(int firearm_id) {
        this.firearm_id = firearm_id;
    }

    public CompetitorFirearmKey(int competitor_id, int firearm_id) {
        this.competitor_id = competitor_id;
        this.firearm_id = firearm_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompetitorFirearmKey that = (CompetitorFirearmKey) o;
        return competitor_id == that.competitor_id &&
                firearm_id == that.firearm_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(competitor_id, firearm_id);
    }
}
