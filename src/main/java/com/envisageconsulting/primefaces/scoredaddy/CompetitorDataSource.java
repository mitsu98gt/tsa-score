package com.envisageconsulting.primefaces.scoredaddy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitorDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

@ManagedBean(name = "competitorDataSource")
@SessionScoped
public class CompetitorDataSource {

	@ManagedProperty("#{competitorDAO}")
	private CompetitorDAO dao;

	public List<Competitor> competitors = new ArrayList<Competitor>();

	@PostConstruct
	public void init() { }

	public void getCompetitorsForScoreSheetByCompetitionId(int id) {
		try {
			competitors = dao.getCompetitorsByCompetitionId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CompetitorDataSource(){
		
	}

	public List<Competitor> getCompetitors() {
		return competitors;
	}

	public void setCompetitors(List<Competitor> competitors) {
		this.competitors = competitors;
	}

	public List<Competitor> queryByName(String name){
		// Assumed search using the contains
		List<Competitor> queried = new ArrayList<Competitor>();
		for(Competitor competitor: this.competitors){
			if(competitor.getFullName().toLowerCase().contains(name.toLowerCase())){
				queried.add(competitor);
			}
		}
		return queried;
	}

	public void setDao(CompetitorDAO dao) {
		this.dao = dao;
	}
	
}
