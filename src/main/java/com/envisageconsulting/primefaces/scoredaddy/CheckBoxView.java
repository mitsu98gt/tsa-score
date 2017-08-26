package com.envisageconsulting.primefaces.scoredaddy;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class CheckBoxView {

	private String[] selectedDivisions;
	
	@PostConstruct
	public void init() {
		
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
