package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.TournamentDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;
import com.envisageconsulting.primefaces.scoredaddy.domain.Tournament;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "tournamentConverter")
@FacesConverter(value = "tournamentConverter")
public class TournamentConverter implements Converter {

    @ManagedProperty("#{tournamentDAO}")
    private TournamentDAO tournamentDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            for(Tournament c : tournamentDAO.getAllTournamentsByAccountIdAndStatus(SessionUtils.getAccountId(), "I")){
                if(String.valueOf(c.getId()).equals(value)){
                    return c;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Tournament){
            Tournament tournament = (Tournament)value;
            return String.valueOf(tournament.getId());
        }
        return "";
    }

    public TournamentDAO getTournamentDAO() {
        return tournamentDAO;
    }

    public void setTournamentDAO(TournamentDAO tournamentDAO) {
        this.tournamentDAO = tournamentDAO;
    }
}
