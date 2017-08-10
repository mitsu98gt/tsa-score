package com.envisageconsulting.primefaces.scoredaddy;

import java.util.Date;

import javax.faces.bean.ManagedBean;


@ManagedBean(name = "editor")
public class EditorBean {

	private String value = "This editor is provided by PrimeFaces";
	public Date date1;
	
	/**
	 * @return the date1
	 */
	public Date getDate1() {
		return date1;
	}

	/**
	 * @param date1 the date1 to set
	 */
	public void setDate1(Date date1) {
		this.date1 = date1;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
