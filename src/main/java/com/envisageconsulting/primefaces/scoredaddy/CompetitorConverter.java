package com.envisageconsulting.primefaces.scoredaddy;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

@ManagedBean(name = "competitorConverter")
@FacesConverter(value = "competitorConverter")
public class CompetitorConverter implements Converter {

	@ManagedProperty("#{competitorDataSource}")
	private CompetitorDataSource ds;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		for(Competitor c : ds.getCompetitors()){
			if(c.getCompetitorId().equals(value)){
				return c;
			}
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

	public CompetitorDataSource getDs() {
		return ds;
	}

	public void setDs(CompetitorDataSource ds) {
		this.ds = ds;
	}
	
}
