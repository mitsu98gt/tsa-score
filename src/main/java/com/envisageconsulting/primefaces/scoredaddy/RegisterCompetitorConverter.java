package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "registerCompetitorConverter")
@FacesConverter(value = "registerCompetitorConverter")
public class RegisterCompetitorConverter implements Converter {

	@ManagedProperty("#{competitorDAO}")
	private CompetitorDAO competitorDAO;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		try {
			for(Competitor c : competitorDAO.getAllCompetitors()){
                if(c.getCompetitorId().equals(value)){
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
		if(value instanceof Competitor){
			Competitor competitor = (Competitor)value;
			return competitor.getCompetitorId();
		}
		return "";
	}

	public CompetitorDAO getCompetitorDAO() {
		return competitorDAO;
	}

	public void setCompetitorDAO(CompetitorDAO competitorDAO) {
		this.competitorDAO = competitorDAO;
	}
}
