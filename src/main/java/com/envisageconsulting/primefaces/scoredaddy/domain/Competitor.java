package com.envisageconsulting.primefaces.scoredaddy.domain;

public class Competitor {

	private String competitorId;
	private String firstName;
	private String lastName;
	private String fullName;
	private Address address;
	private String phone;
	private String email;
	private String gssfId;

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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGssfId() {
		return gssfId;
	}

	public void setGssfId(String gssfId) {
		this.gssfId = gssfId;
	}

	public boolean equals(Object obj){
		if(obj instanceof Competitor){
			Competitor competitor = (Competitor)obj;
			if(null != this.competitorId && this.competitorId.equals(competitor.getCompetitorId())){
				return true;
			}
		}
		return false;
	}

	public int hashCode(){
		return this.competitorId.hashCode();
	}
}
