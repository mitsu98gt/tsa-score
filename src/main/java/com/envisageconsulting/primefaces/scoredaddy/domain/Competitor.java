package com.envisageconsulting.primefaces.scoredaddy.domain;

public class Competitor {

	public String competitorId;
	public String firstName;
	public String lastName;
	public String fullName;

	public String getCompetitorId() {
		return competitorId;
	}

	public void setCompetitorId(String competitorId) {
		this.competitorId = competitorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Competitor){
			return true;
			/*Competitor competitor = (Competitor)obj;
			if(this.competitorId.equals(competitor.getCompetitorId())){
				return true;
			}*/
		}
		return false;
	}

	public int hashCode(){
		return this.competitorId.hashCode();
	}
}
