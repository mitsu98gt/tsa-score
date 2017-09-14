package com.envisageconsulting.primefaces.scoredaddy.domain;

import java.util.Date;

public class GSSFIndoorScoreSheet {

	private Competitor competitor;
	private Date date = new Date();
	private String model;
	private String entry;
	private boolean checkStock;
	private boolean checkUnlimited;
	private boolean checkPocket;
	private boolean checkWoman;
	private boolean checkSenior;
	private boolean checkJunior;
	private Integer targetOneX;
	private Integer targetOneTen;
	private Integer targetOneEight;
	private Integer targetOneFive;
	private Integer targetOneMisses;
	private Integer targetOneTotal;
	private Integer targetTwoX;
	private Integer targetTwoTen;
	private Integer targetTwoEight;
	private Integer targetTwoFive;
	private Integer targetTwoMisses;
	private Integer targetTwoTotal;
	private Integer sumX;
	private Integer sumTen;
	private Integer sumEight;
	private Integer sumFive;
	private Integer sumMisses;
	private Integer sumTotal;
	private Integer totalX;
	private Integer totalTen;
	private Integer totalEight;
	private Integer totalFive;
	private Integer penalty;
	private Integer finalScore;
	private String rangeOfficerInitials;
	private String competitorInitials;

	public Competitor getCompetitor() {
		return competitor;
	}

	public void setCompetitor(Competitor competitor) {
		this.competitor = competitor;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public boolean getCheckStock() {
		return checkStock;
	}

	public void setCheckStock(boolean checkStock) {
		this.checkStock = checkStock;
	}

	public boolean getCheckUnlimited() {
		return checkUnlimited;
	}

	public void setCheckUnlimited(boolean checkUnlimited) {
		this.checkUnlimited = checkUnlimited;
	}

	public boolean getCheckPocket() {
		return checkPocket;
	}

	public void setCheckPocket(boolean checkPocket) {
		this.checkPocket = checkPocket;
	}

	public boolean getCheckWoman() {
		return checkWoman;
	}

	public void setCheckWoman(boolean checkWoman) {
		this.checkWoman = checkWoman;
	}

	public boolean getCheckSenior() {
		return checkSenior;
	}

	public void setCheckSenior(boolean checkSenior) {
		this.checkSenior = checkSenior;
	}

	public boolean getCheckJunior() {
		return checkJunior;
	}

	public void setCheckJunior(boolean checkJunior) {
		this.checkJunior = checkJunior;
	}

	public Integer getTargetOneX() {
		return targetOneX;
	}

	public void setTargetOneX(Integer targetOneX) {
		this.targetOneX = targetOneX;
	}

	public Integer getTargetOneTen() {
		return targetOneTen;
	}

	public void setTargetOneTen(Integer targetOneTen) {
		this.targetOneTen = targetOneTen;
	}

	public Integer getTargetOneEight() {
		return targetOneEight;
	}

	public void setTargetOneEight(Integer targetOneEight) {
		this.targetOneEight = targetOneEight;
	}

	public Integer getTargetOneFive() {
		return targetOneFive;
	}

	public void setTargetOneFive(Integer targetOneFive) {
		this.targetOneFive = targetOneFive;
	}

	public Integer getTargetOneMisses() {
		return targetOneMisses;
	}

	public void setTargetOneMisses(Integer targetOneMisses) {
		this.targetOneMisses = targetOneMisses;
	}

	public Integer getTargetOneTotal() {
		return targetOneTotal;
	}

	public void setTargetOneTotal(Integer targetOneTotal) {
		this.targetOneTotal = targetOneTotal;
	}

	public Integer getTargetTwoX() {
		return targetTwoX;
	}

	public void setTargetTwoX(Integer targetTwoX) {
		this.targetTwoX = targetTwoX;
	}

	public Integer getTargetTwoTen() {
		return targetTwoTen;
	}

	public void setTargetTwoTen(Integer targetTwoTen) {
		this.targetTwoTen = targetTwoTen;
	}

	public Integer getTargetTwoEight() {
		return targetTwoEight;
	}

	public void setTargetTwoEight(Integer targetTwoEight) {
		this.targetTwoEight = targetTwoEight;
	}

	public Integer getTargetTwoFive() {
		return targetTwoFive;
	}

	public void setTargetTwoFive(Integer targetTwoFive) {
		this.targetTwoFive = targetTwoFive;
	}

	public Integer getTargetTwoMisses() {
		return targetTwoMisses;
	}

	public void setTargetTwoMisses(Integer targetTwoMisses) {
		this.targetTwoMisses = targetTwoMisses;
	}

	public Integer getTargetTwoTotal() {
		return targetTwoTotal;
	}

	public void setTargetTwoTotal(Integer targetTwoTotal) {
		this.targetTwoTotal = targetTwoTotal;
	}

	public Integer getSumX() {
		return sumX;
	}

	public void setSumX(Integer sumX) {
		this.sumX = sumX;
	}

	public Integer getSumTen() {
		return sumTen;
	}

	public void setSumTen(Integer sumTen) {
		this.sumTen = sumTen;
	}

	public Integer getSumEight() {
		return sumEight;
	}

	public void setSumEight(Integer sumEight) {
		this.sumEight = sumEight;
	}

	public Integer getSumFive() {
		return sumFive;
	}

	public void setSumFive(Integer sumFive) {
		this.sumFive = sumFive;
	}

	public Integer getSumMisses() {
		return sumMisses;
	}

	public void setSumMisses(Integer sumMisses) {
		this.sumMisses = sumMisses;
	}

	public Integer getSumTotal() {
		return sumTotal;
	}

	public void setSumTotal(Integer sumTotal) {
		this.sumTotal = sumTotal;
	}

	public Integer getTotalX() {
		return totalX;
	}

	public void setTotalX(Integer totalX) {
		this.totalX = totalX;
	}

	public Integer getTotalTen() {
		return totalTen;
	}

	public void setTotalTen(Integer totalTen) {
		this.totalTen = totalTen;
	}

	public Integer getTotalEight() {
		return totalEight;
	}

	public void setTotalEight(Integer totalEight) {
		this.totalEight = totalEight;
	}

	public Integer getTotalFive() {
		return totalFive;
	}

	public void setTotalFive(Integer totalFive) {
		this.totalFive = totalFive;
	}

	public Integer getPenalty() {
		return penalty;
	}

	public void setPenalty(Integer penalty) {
		this.penalty = penalty;
	}

	public Integer getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}

	public String getRangeOfficerInitials() {
		return rangeOfficerInitials;
	}

	public void setRangeOfficerInitials(String rangeOfficerInitials) {
		this.rangeOfficerInitials = rangeOfficerInitials;
	}

	public String getCompetitorInitials() {
		return competitorInitials;
	}

	public void setCompetitorInitials(String competitorInitials) {
		this.competitorInitials = competitorInitials;
	}
	
}
