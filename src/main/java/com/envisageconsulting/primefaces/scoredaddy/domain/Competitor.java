package com.envisageconsulting.primefaces.scoredaddy.domain;

public class Competitor {

	public String competitorId;
	public String firstName;
	public String lastName;
	public String fullName;
	
	/**
	 * @return the competitorId
	 */
	public String getCompetitorId() {
		return competitorId;
	}
	/**
	 * @param competitorId the competitorId to set
	 */
	public void setCompetitorId(String competitorId) {
		this.competitorId = competitorId;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}
	
	public boolean equals(Object obj){
		if(obj instanceof Competitor){
			Competitor competitor = (Competitor)obj;
			if(this.competitorId.equals(competitor.getCompetitorId())){
				return true;
			}
		}
		return false;
	}

	public int hashCode(){
		return this.competitorId.hashCode();
	}
}
