package com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet;

import com.envisageconsulting.primefaces.scoredaddy.domain.Competitor;
import com.envisageconsulting.primefaces.scoredaddy.domain.Firearm;

import java.util.Date;

public class GSSFIndoorScoreSheet {

	private Competitor competitor;
	private Date date = new Date();
	private Firearm firearm;
	private String entry;
	private Division divsion = new Division();
	private TargetOne targetOne = new TargetOne();
	private TargetTwo targetTwo = new TargetTwo();
	private SumRow sumRow = new SumRow();
	private TotalRow totalRow = new TotalRow();
	private Integer penalty;
	private Integer finalScore;
	private Integer totalX;
	private String rangeOfficerInitials;
	private String competitorInitials;
	private boolean additionalEntry;

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

	public Firearm getFirearm() {
		return firearm;
	}

	public void setFirearm(Firearm firearm) {
		this.firearm = firearm;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	public Division getDivsion() {
		return divsion;
	}

	public void setDivsion(Division divsion) {
		this.divsion = divsion;
	}

	public TargetOne getTargetOne() {
		return targetOne;
	}

	public void setTargetOne(TargetOne targetOne) {
		this.targetOne = targetOne;
	}

	public TargetTwo getTargetTwo() {
		return targetTwo;
	}

	public void setTargetTwo(TargetTwo targetTwo) {
		this.targetTwo = targetTwo;
	}

	public SumRow getSumRow() {
		return sumRow;
	}

	public void setSumRow(SumRow sumRow) {
		this.sumRow = sumRow;
	}

	public TotalRow getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(TotalRow totalRow) {
		this.totalRow = totalRow;
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

	public Integer getTotalX() {
		return totalX;
	}

	public void setTotalX(Integer totalX) {
		this.totalX = totalX;
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

	public boolean isAdditionalEntry() {
		return additionalEntry;
	}

	public void setAdditionalEntry(boolean additionalEntry) {
		this.additionalEntry = additionalEntry;
	}
}
