package com.envisageconsulting.primefaces.scoredaddy;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

@ManagedBean
@SessionScoped
public class AutoCompleteView {

	@ManagedProperty("#{competitorDataSource}")
	private CompetitorDataSource ds;

	private Competitor competitor;

	public AutoCompleteView(){

	}

	public List<Competitor> complete(String query){
		// Assumed Datasource
		return ds.queryByName(query);
	}

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public CompetitorDataSource getDs() {
		return ds;
	}

	public void setDs(CompetitorDataSource ds) {
		this.ds = ds;
	}
	
}
