package com.envisageconsulting.primefaces.scoredaddy;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;

@ManagedBean
@SessionScoped
public class CompetitorDataSource {

	public List<Competitor> competitors = new ArrayList<Competitor>();

	public CompetitorDataSource(){
		
		Competitor competitor = new Competitor();
		
		competitor.setCompetitorId("1");
		competitor.setFirstName("Vinh");
		competitor.setLastName("Dang");

		competitors.add(competitor);

		competitor = new Competitor();
		competitor.setCompetitorId("2");
		competitor.setFirstName("Heather");
		competitor.setLastName("Dang");

		competitors.add(competitor);

		competitor = new Competitor();
		competitor.setCompetitorId("3");
		competitor.setFirstName("Alexa");
		competitor.setLastName("Dang");
	
		competitors.add(competitor);
		
		competitor = new Competitor();
		competitor.setCompetitorId("4");
		competitor.setFirstName("Brandon");
		competitor.setLastName("Dang");
	
		competitors.add(competitor);
		

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
	
}
