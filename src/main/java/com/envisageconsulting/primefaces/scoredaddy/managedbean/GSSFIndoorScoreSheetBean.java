package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.*;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.Division;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

@RequestScoped
@ManagedBean(name="scoresheetBean")
public class GSSFIndoorScoreSheetBean implements Serializable {

	private GSSFIndoorScoreSheet scoreSheet;
    private StreamedContent file;

    @ManagedProperty("#{competitorDataSource}")
    private CompetitorDataSource competitorDataSource;

    @ManagedProperty("#{firearmDataSource}")
    private FirearmDataSource firearmDataSource;

    @ManagedProperty("#{competitionResultsDAO}")
    private CompetitionResultsDAO competitionResultsDAO;

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO competitionDAO;

    private List<Firearm> firearmList;

    private String[] selectedDivisions;

    private Competition competition;

    private List<Competition> allCompetitions;

    private boolean stockDivision;
    private boolean unlimitedDivision;
    private boolean pocketDivision;
    private boolean rimfireDivision;
    private boolean womanDivision;
    private boolean seniorDivision;
    private boolean juniorDivision;

    private boolean additionalEntry;

    public GSSFIndoorScoreSheetBean(){}

    @PostConstruct
    public void init() {
        scoreSheet = new GSSFIndoorScoreSheet();
        stockDivision = false;
        unlimitedDivision = false;
        pocketDivision = false;
        rimfireDivision = false;
        womanDivision = false;
        seniorDivision = false;
        juniorDivision = false;
        try {
            allCompetitions = competitionDAO.getGlockCompetitionsByAccountIdAndStatus(SessionUtils.getAccountId(), "I");
            firearmList = firearmDataSource.getFirearms();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onCompetitionChange() {
        scoreSheet.setDate(competition.getDate());
        competitorDataSource.getCompetitorsForScoreSheetByCompetitionId(Integer.valueOf(competition.getId()));
        firearmList = getListOfFirearms();
    }

    public void onCompetitorChange() {
        scoreSheet.setEntry(scoreSheet.getCompetitor().getGssfId());
        scoreSheet.setRangeOfficerInitials(SessionUtils.getUserInitials());
        scoreSheet.setCompetitorInitials(scoreSheet.getCompetitor().getFirstName().substring(0,1) + scoreSheet.getCompetitor().getLastName().substring(0,1));
    }

    public boolean doScore() {

        scoreSheet.setAdditionalEntry(additionalEntry);
        parseSelectedDivisions();
        if (doValidation()){
            ScoreSheetUtils.calculateTargetTotals(scoreSheet);
            ScoreSheetUtils.calculateSumRow(scoreSheet);
            ScoreSheetUtils.calculateTotalRow(scoreSheet);
            ScoreSheetUtils.calculatePenalty(scoreSheet);
        } else {
            return false;
        }

        if (!additionalEntry && !validateDesignatedEntries()) {
            return false;
        }

        if (!validateAdditionalEntries()) {
            return false;
        }

        return true;
    }

    public List<Firearm> getListOfFirearms() {
        return firearmDataSource.getAllGlockFirearmsForScoreSheet();
    }

    public boolean doValidation() {

        if (!validateDivisions()) {
            return false;
        }

        if (!ScoreSheetUtils.validateHitsAreNonNegative(scoreSheet)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Invalid score input! Scores can not be negative!"));
            return false;
        }

        if (!ScoreSheetUtils.validateStockTotals(scoreSheet)) {
            return false;
        }

        if (!ScoreSheetUtils.validatePocketTotals(scoreSheet)) {
            return false;
        }

        return true;
    }

    public boolean validateDesignatedEntries() {

        boolean pass = true;

        try {
            int competitorId = Integer.valueOf(scoreSheet.getCompetitor().getCompetitorId());
            int competitionId = Integer.valueOf(competition.getId());
            String division = ScoreSheetUtils.getDivisionForSqlColumnName(scoreSheet.getDivsion());
            List<Firearm> designatedFirearms = competitionResultsDAO.getCompetitorDesignatedFirearmByTournamentAndDivision(competitorId, competitionId, division, "false");

            if (designatedFirearms.size() == 0) {
                pass = true;
            } else {
                String firearmId = designatedFirearms.get(0).getId();
                if (scoreSheet.getFirearm().getId().equals(firearmId)) {
                    pass = true;
                } else {
                    pass = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Firearm model does not match with previous competition(s) designated entry model!"));
                }
            }
        } catch (Exception e) {
            pass = false;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Designated entry error, please try again!"));
        }

        return pass;

    }

    public boolean validateAdditionalEntries() {

        boolean pass = true;

        try {
            int competitionId = Integer.valueOf(competition.getId());
            int competitorId = Integer.valueOf(scoreSheet.getCompetitor().getCompetitorId());
            String division = ScoreSheetUtils.getDivisionForSqlColumnName(scoreSheet.getDivsion());
            int designatedEntriesInCompetition = competitionResultsDAO.getCompetitorNumberOfDesignatedEntriesByCompetitionAndDivision(competitionId, competitorId, division);
            int tournamentDesignatedEntries = competitionResultsDAO.getCompetitorNumberOfDesignatedEntriesByTournamentAndDivision(competitionId, competitorId, division);
            List<Firearm> additionalEntryFirearmsInCompetition = competitionResultsDAO.getCompetitorAdditionalEntriesByFirearms(competitionId, competitorId, division);

            /*if (designatedEntriesInCompetition == 0) {
                if (additionalEntry && (tournamentDesignatedEntries < 2)) {
                    pass = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "No other entries found for competitor in this division! Please input the designated entry before inputting an additional entry."));
                } else {
                    pass = true;
                }
            } else {
                if (additionalEntry) {
                    for (Firearm f : additionalEntryFirearms) {
                        if (scoreSheet.getFirearm().getId().equals(f.getId())) {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Additional entries of the same model in the same division is not supported at this time!"));
                            return false;
                        }
                    }
                    pass = true;
                }  else {
                    pass = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "A designated entry was found for competitor in this division! Please select the additional entries checkbox."));
                }
            }*/
            if (additionalEntry) {
                for (Firearm f : additionalEntryFirearmsInCompetition) {
                    if (scoreSheet.getFirearm().getId().equals(f.getId())) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Additional entries of the same model in the same division is not supported at this time!"));
                        return false;
                    }
                }
                pass = true;
            }  /*else {
                pass = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "A designated entry was found for competitor in this division! Please select the additional entries checkbox."));
            }*/
        } catch (Exception e) {
            pass = false;
        }

        return pass;
    }

    public boolean validateDivisions() {

        if (!isDivisionSelected()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Please select a division!"));
            return false;
        }

        if (!isOnlyStock()) {
            return false;
        }

        if (!isOnlyUnlimited()) {
            return false;
        }

        if (!isOnlyPocket()) {
            return false;
        }

        if (!isOnlyRimfire()) {
            return false;
        }

        if (!validateSeniorJunior()) {
            return false;
        }

        if (!validateWoman()) {
            return false;
        }

        if (!validateSenior()) {
            return false;
        }

        if (!validateJunior()) {
            return false;
        }

        return true;
    }

    public boolean validateWoman() {
        if (scoreSheet.getDivsion().isWoman()) {
            if(scoreSheet.getDivsion().isRimfire() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Woman division applies to only Stock!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateSenior() {
        if (scoreSheet.getDivsion().isSenior()) {
            if(scoreSheet.getDivsion().isRimfire() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Senior division applies to only Stock!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateJunior() {
        if (scoreSheet.getDivsion().isJunior()) {
            if(scoreSheet.getDivsion().isRimfire() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Junior division applies to only Stock!"));
                return false;
            }
        }
        return true;
    }

    public boolean validateSeniorJunior() {
        if (scoreSheet.getDivsion().isSenior() && scoreSheet.getDivsion().isJunior()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Senior and Junior divisions can't be both selected!"));
            return false;
        }
        return true;
    }

    public boolean isDivisionSelected() {
        return (scoreSheet.getDivsion().isRimfire() || scoreSheet.getDivsion().isStock() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) ? true : false;
    }

    public boolean isOnlyStock() {
        if (scoreSheet.getDivsion().isStock()) {
            if (scoreSheet.getDivsion().isRimfire() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Stock, Unlimited, Pocket, or Rimfire divisions can be selected!"));
                return false;
            }
            if (scoreSheet.getFirearm().getModel().equals("G43") || scoreSheet.getFirearm().getModel().equals("G42") || scoreSheet.getFirearm().getModel().equals("G44")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Firearm model not applicable for Stock division!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyUnlimited() {
        if (scoreSheet.getDivsion().isUnlimited()) {
            if (scoreSheet.getDivsion().isRimfire() || scoreSheet.getDivsion().isStock() || scoreSheet.getDivsion().isPocket()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Stock, Unlimited, Pocket, or Rimfire divisions can be selected!"));
                return false;
            }
            if (scoreSheet.getFirearm().getModel().equals("G36") || scoreSheet.getFirearm().getModel().equals("G42") || scoreSheet.getFirearm().getModel().equals("G43") || scoreSheet.getFirearm().getModel().equals("G44")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Firearm model not applicable for Unlimited division!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyPocket() {
        if (scoreSheet.getDivsion().isPocket()) {
            if (scoreSheet.getDivsion().isRimfire() || scoreSheet.getDivsion().isUnlimited() || scoreSheet.getDivsion().isStock() || scoreSheet.getDivsion().isWoman() || scoreSheet.getDivsion().isSenior() || scoreSheet.getDivsion().isJunior()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Stock, Unlimited, Pocket, or Rimfire divisions can be selected!"));
                return false;
            }
            if (!scoreSheet.getFirearm().getModel().equals("G42") && !scoreSheet.getFirearm().getModel().equals("G43")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Firearm model not applicable for Pocket division!"));
                return false;
            }
        }
        return true;
    }

    public boolean isOnlyRimfire() {
        if (scoreSheet.getDivsion().isRimfire()) {
            if (scoreSheet.getDivsion().isStock() || scoreSheet.getDivsion().isPocket() || scoreSheet.getDivsion().isUnlimited()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Only 1 of Stock, Unlimited, Pocket, or Rimfire divisions can be selected!"));
                return false;
            }
            if (!scoreSheet.getFirearm().getModel().equals("G44")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Firearm model not applicable for Rimfire division!"));
                return false;
            }
        }
        return true;
    }

    public void saveScoreSheetPDF() throws Exception {
        doScore();
        GSSFIndoorScoreSheetPDF pdf = new GSSFIndoorScoreSheetPDF();
        pdf.savePDF(scoreSheet);
    }

    public StreamedContent downloadScoreSheetPDF() throws Exception {
        if (doScore()) {
            GSSFIndoorScoreSheetPDF pdf = new GSSFIndoorScoreSheetPDF();
            return new DefaultStreamedContent(pdf.downloadPDF(scoreSheet), "application/pdf", scoreSheet.getCompetitor().getFirstName() + scoreSheet.getCompetitor().getLastName() + "_Scoresheet_" + competition.getDate().toString() + ".pdf");
        }
        return null;
    }
    public void saveToDatabase() {

        if (doScore()) {
            try {

                int rank = 0;

                List<CompetitorRank> rankList = new ArrayList<>();
                rankList = competitionResultsDAO.getCompetitorRanks(Integer.valueOf(competition.getId()), Integer.valueOf(scoreSheet.getCompetitor().getCompetitorId()), "cr."+ScoreSheetUtils.getDivisionForSqlColumnName(scoreSheet.getDivsion()), "false");
                if (rankList.size() == 0) {
                    rank = 1;
                } else {
                    if (rankList.size() == 1) {
                        CompetitorRank competitorRank = rankList.get(0);
                        if (scoreSheet.getFinalScore() < competitorRank.getFinal_score()) {
                            rank = 2;
                        }
                        if (scoreSheet.getFinalScore() > competitorRank.getFinal_score()) {
                            rank = 1;
                            competitionResultsDAO.updateCompetitionResultsRank(competitorRank.getCompetition_results_id(),2);
                        }
                        if (scoreSheet.getFinalScore() == competitorRank.getFinal_score()) {
                            if (scoreSheet.getTotalX() < competitorRank.getTotal_x()) {
                                rank = 2;
                            }
                            if (scoreSheet.getTotalX() > competitorRank.getTotal_x()) {
                                rank = 1;
                                competitionResultsDAO.updateCompetitionResultsRank(competitorRank.getCompetition_results_id(), 2);
                            }
                            // same score and x's, so rank won't matter
                            rank = 2;
                        }
                    }
                    if (rankList.size() == 2) {
                        CompetitorRank competitorRank1 = rankList.get(0);
                        CompetitorRank competitorRank2 = rankList.get(1);
                        CompetitorRank highest = new CompetitorRank();
                        CompetitorRank lowest = new CompetitorRank();
                        Boolean isFinalScoreEqual = false;
                        Boolean isEqualTotal_X = false;

                        if (competitorRank1.getFinal_score() == competitorRank2.getFinal_score()) {
                            isFinalScoreEqual = true;
                        }

                        if (competitorRank1.getTotal_x() == competitorRank2.getTotal_x()) {
                            isEqualTotal_X = true;
                        }

                        if (competitorRank1.getFinal_score() > competitorRank2.getFinal_score()) {
                            highest = competitorRank1;
                            lowest = competitorRank2;
                        } else if (competitorRank1.getFinal_score() < competitorRank2.getFinal_score()){
                            highest = competitorRank2;
                            lowest = competitorRank1;
                        } else if (competitorRank1.getFinal_score() == competitorRank2.getFinal_score()) {
                            if (competitorRank1.getTotal_x() > competitorRank2.getTotal_x()) {
                                highest = competitorRank1;
                                lowest = competitorRank2;
                            } else if (competitorRank1.getTotal_x() < competitorRank2.getTotal_x()) {
                                highest = competitorRank2;
                                lowest = competitorRank1;
                            } else if (competitorRank1.getTotal_x() == competitorRank2.getTotal_x()) {
                                highest = competitorRank1;
                                lowest = competitorRank2;
                            }
                        }

                        if ((scoreSheet.getFinalScore() < competitorRank1.getFinal_score()) &&
                             (scoreSheet.getFinalScore() < competitorRank2.getFinal_score())) {
                            rank = 3;
                            competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                            competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 2);
                        }

                        if (scoreSheet.getFinalScore() > highest.getFinal_score()) {
                            rank = 1;
                            competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 2);
                            competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 3);
                        }

                        if ((scoreSheet.getFinalScore() < highest.getFinal_score()) && (scoreSheet.getFinalScore() > lowest.getFinal_score())) {
                            rank = 2;
                            competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                            competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 3);
                        }

                        if (scoreSheet.getFinalScore() < highest.getFinal_score() && isFinalScoreEqual) {
                            rank = 3;
                            competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                            competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 2);
                        }

                        if (scoreSheet.getFinalScore() == highest.getFinal_score()) {
                            if (scoreSheet.getTotalX() == highest.getTotal_x()) {
                                rank = 2;
                                competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                                competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 3);
                            } else if (scoreSheet.getTotalX() > highest.getTotal_x()) {
                                rank = 1;
                                competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 2);
                                competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 3);
                            } else if (scoreSheet.getTotalX() < highest.getTotal_x()) {
                                rank = 2;
                                competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                                competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 3);
                            }
                        }

                        if (scoreSheet.getFinalScore() == lowest.getFinal_score()) {
                            if (scoreSheet.getTotalX() == lowest.getTotal_x()) {
                                rank = 3;
                                competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                                competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 2);
                            } else if (scoreSheet.getTotalX() > lowest.getTotal_x()) {
                                rank = 2;
                                competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                                competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 3);
                            } else if (scoreSheet.getTotalX() < lowest.getTotal_x()) {
                                rank = 3;
                                competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                                competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 2);
                            }
                        }

                        if (scoreSheet.getFinalScore() < lowest.getFinal_score()) {
                            rank = 3;
                            competitionResultsDAO.updateCompetitionResultsRank(highest.getCompetition_results_id(), 1);
                            competitionResultsDAO.updateCompetitionResultsRank(lowest.getCompetition_results_id(), 2);
                        }
                    }
                }

                competitionResultsDAO.addCompetitionResults(buildCompetitionResults(), rank);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info:", "Scores saved successfully!"));
                init();
            } catch (Exception ex) {
                ex.printStackTrace();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Scores did not save!"));
            }
        }
    }

    public CompetitionResults buildCompetitionResults() throws Exception {

        doScore();

        CompetitionResults competitionResults = new CompetitionResults();
        CompetitionDetails competitionDetails = new CompetitionDetails();
        CompetitionCode competitionCode = new CompetitionCode();

        competitionCode.setCompetitionCodeId("1");
        competitionDetails.setCompetitionCode(competitionCode);

        competitionDetails.setCompetitionDetailsId(competition.getId());
        //competitionDetails.setDate(DateUtils.getDateFromString("2018-01-06"));
        competitionDetails.setDate(competition.getDate());
        competitionResults.setCompetitionDetails(competitionDetails);

        CompetitionCompetitors competitionCompetitors = new CompetitionCompetitors();
        competitionCompetitors.setCompetitorId(scoreSheet.getCompetitor().getCompetitorId());
        competitionCompetitors.setFirearmId(scoreSheet.getFirearm().getId());
        competitionResults.setCompetitionCompetitors(competitionCompetitors);

        parseSelectedDivisions();
        scoreSheet.setTotalX(calculateTotalX());

        competitionResults.setGssfIndoorScoreSheet(scoreSheet);

        return competitionResults;
    }

    public int calculateTotalX() {
        return scoreSheet.getTargetOne().getX() + scoreSheet.getTargetTwo().getX();
    }

    public int calculateTargetOneTotals() {
        return (scoreSheet.getTargetOne().getX() + scoreSheet.getTargetOne().getTen() + scoreSheet.getTargetOne().getEight() + scoreSheet.getTargetOne().getFive() + scoreSheet.getTargetOne().getMisses());
    }

    public int calculateTargetTwoTotals() {
        return (scoreSheet.getTargetTwo().getX() + scoreSheet.getTargetTwo().getTen() + scoreSheet.getTargetTwo().getEight() + scoreSheet.getTargetTwo().getFive() + scoreSheet.getTargetTwo().getMisses());
    }

    public int calculateTotalScore() {
        return (scoreSheet.getTotalRow().getX() + scoreSheet.getTotalRow().getTen() + scoreSheet.getTotalRow().getEight() + scoreSheet.getTotalRow().getFive());
    }

    public List<Competitor> complete(String query){
        // Assumed Datasource
        return competitorDataSource.queryByName(query);
    }

    public void parseSelectedDivisions(){
        scoreSheet.getDivsion().setUnlimited(unlimitedDivision);
        scoreSheet.getDivsion().setStock(stockDivision);
        scoreSheet.getDivsion().setPocket(pocketDivision);
        scoreSheet.getDivsion().setRimfire(rimfireDivision);
        scoreSheet.getDivsion().setWoman(womanDivision);
        scoreSheet.getDivsion().setSenior(seniorDivision);
        scoreSheet.getDivsion().setJunior(juniorDivision);

    }

    public CompetitorDataSource getCompetitorDataSource() {
        return competitorDataSource;
    }

    public void setCompetitorDataSource(CompetitorDataSource competitorDataSource) {
        this.competitorDataSource = competitorDataSource;
    }

    public FirearmDataSource getFirearmDataSource() {
        return firearmDataSource;
    }

    public void setFirearmDataSource(FirearmDataSource firearmDataSource) {
        this.firearmDataSource = firearmDataSource;
    }

    public void setCompetitionResultsDAO(CompetitionResultsDAO competitionResultsDAO) {
        this.competitionResultsDAO = competitionResultsDAO;
    }

    public void setCompetitionDAO(CompetitionDAO competitionDAO) {
        this.competitionDAO = competitionDAO;
    }

    public GSSFIndoorScoreSheet getScoreSheet() {
        return scoreSheet;
    }

    public void setScoreSheet(GSSFIndoorScoreSheet scoreSheet) {
        this.scoreSheet = scoreSheet;
    }

    public String[] getSelectedDivisions() {
        return selectedDivisions;
    }

    public void setSelectedDivisions(String[] selectedDivisions) {
        this.selectedDivisions = selectedDivisions;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public List<Firearm> getFirearmList() {
        return firearmList;
    }

    public void setFirearmList(List<Firearm> firearmList) {
        this.firearmList = firearmList;
    }

    public List<Competition> getAllCompetitions() {
        return allCompetitions;
    }

    public void setAllCompetitions(List<Competition> allCompetitions) {
        this.allCompetitions = allCompetitions;
    }

    public boolean isStockDivision() {
        return stockDivision;
    }

    public void setStockDivision(boolean stockDivision) {
        this.stockDivision = stockDivision;
    }

    public boolean isUnlimitedDivision() {
        return unlimitedDivision;
    }

    public void setUnlimitedDivision(boolean unlimitedDivision) {
        this.unlimitedDivision = unlimitedDivision;
    }

    public boolean isPocketDivision() {
        return pocketDivision;
    }

    public void setPocketDivision(boolean pocketDivision) {
        this.pocketDivision = pocketDivision;
    }

    public boolean isRimfireDivision() {
        return rimfireDivision;
    }

    public void setRimfireDivision(boolean rimfireDivision) {
        this.rimfireDivision = rimfireDivision;
    }

    public boolean isWomanDivision() {
        return womanDivision;
    }

    public void setWomanDivision(boolean womanDivision) {
        this.womanDivision = womanDivision;
    }

    public boolean isSeniorDivision() {
        return seniorDivision;
    }

    public void setSeniorDivision(boolean seniorDivision) {
        this.seniorDivision = seniorDivision;
    }

    public boolean isJuniorDivision() {
        return juniorDivision;
    }

    public void setJuniorDivision(boolean juniorDivision) {
        this.juniorDivision = juniorDivision;
    }

    public boolean isAdditionalEntry() {
        return additionalEntry;
    }

    public void setAdditionalEntry(boolean additionalEntry) {
        this.additionalEntry = additionalEntry;
    }

    public StreamedContent getFile() throws Exception {
        return (null == file) ? downloadScoreSheetPDF() : file;
    }

}
