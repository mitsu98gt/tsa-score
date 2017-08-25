package com.envisageconsulting.primefaces.scoredaddy;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class CheckBoxView {

	private List<String> divisions;
	private String[] selectedDivisions;
	
	@PostConstruct
	public void init() {
		
		divisions = new ArrayList<String>();
		divisions.add("Stock");
		divisions.add("Unlimited");
		divisions.add("Pocket");
		divisions.add("Woman");
		divisions.add("Senior");
		divisions.add("Junior(<18)");
		
	}

	/**
	 * @return the divisions
	 */
	public List<String> getDivisions() {
		return divisions;
	}

	/**
	 * @param divisions the divisions to set
	 */
	public void setDivisions(List<String> divisions) {
		this.divisions = divisions;
	}

	/**
	 * @return the selectedDivisions
	 */
	public String[] getSelectedDivisions() {
		return selectedDivisions;
	}

	/**
	 * @param selectedDivisions the selectedDivisions to set
	 */
	public void setSelectedDivisions(String[] selectedDivisions) {
		this.selectedDivisions = selectedDivisions;
	}

}
