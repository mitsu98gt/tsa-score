package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competition;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.util.Map;

@ManagedBean(name = "competitionConverter")
@FacesConverter(value = "competitionConverter")
public class CompetitionConverter implements Converter {

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO competitionDAO;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            for(Competition c : competitionDAO.getCompetitionsByAccountIdAndStatus(getAccountIdFromSession(), "I")){
                if(c.getId().equals(value)){
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
        if(value instanceof Competition){
            Competition competition = (Competition)value;
            return competition.getId();
        }
        return "";
    }

    public int getAccountIdFromSession() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        return (int) sessionMap.get("accountId");
    }

    public CompetitionDAO getCompetitionDAO() {
        return competitionDAO;
    }

    public void setCompetitionDAO(CompetitionDAO competitionDAO) {
        this.competitionDAO = competitionDAO;
    }
}
