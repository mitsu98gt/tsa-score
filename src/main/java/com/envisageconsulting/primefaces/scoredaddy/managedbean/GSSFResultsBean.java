package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.Constants;
import com.envisageconsulting.primefaces.scoredaddy.SQLConstants;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.sun.javafx.scene.control.skin.VirtualFlow;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@ViewScoped
@ManagedBean(name="gssfResultsBean")
public class GSSFResultsBean implements Serializable {

    @ManagedProperty("#{competitionResultsDAO}")
    private CompetitionResultsDAO competitionResultsDAO;

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO competitionDAO;

    private List<CompetitionResults> competitionStockResultsList;
    private List<CompetitionResults> competitionUnlimitedResultsList;
    private List<CompetitionResults> competitionPocketResultsList;
    private List<CompetitionResults> competitionWomanResultsList;
    private List<CompetitionResults> competitionSeniorResultsList;
    private List<CompetitionResults> competitionJuniorResultsList;
    private List<CompetitionResults> filtered;

    private List<Competition> allCompetitions;

    private String competitionDate;
    private String accountName;
    private String competitionDescription;

    private Competition competition;

    private boolean renderSingleScores;
    private boolean renderAveragedScores;
    private boolean disableAveragedScoresButton;

    @PostConstruct
    public void init() {

        renderSingleScores = false;
        renderAveragedScores = false;

        try {
            allCompetitions = competitionDAO.getCompetitionsByAccountIdAndStatus(getAccountIdFromSession(), "I");
            accountName = allCompetitions.get(0).getName();
            competitionDescription = allCompetitions.get(0).getDescription();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        disableAveragedScoresButton = isMultipleCompetitions();
    }

    public void viewSingleScores() {
        try {
            renderSingleScores = true;
            renderAveragedScores = false;
            competitionStockResultsList = calculateClassifcation(competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.STOCK_DIVISION, Integer.valueOf(competition.getId())));
            competitionUnlimitedResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.UNLIMITED_DIVISION, Integer.valueOf(competition.getId()));
            competitionPocketResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.POCKET_DIVISION, Integer.valueOf(competition.getId()));
            competitionWomanResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.WOMAN_DIVISION, Integer.valueOf(competition.getId()));
            competitionSeniorResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.SENIOR_DIVISION, Integer.valueOf(competition.getId()));
            competitionJuniorResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.JUNIOR_DIVISION, Integer.valueOf(competition.getId()));
            competitionDate = getCompetitionDate();
            accountName = getAccountInfoName();
            competitionDescription = getCompetitionInfoDescription();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAveragedScores() {

        renderAveragedScores = true;
        renderSingleScores = false;
        int numberOfCompetitions = allCompetitions.size();

        if (numberOfCompetitions == 2) {
            calculateAveragesForTwoCompetitions();
        } else {
            calculateAveragesForThreeCompetitions();
        }
    }

    public void calculateAveragesForTwoCompetitions() {

        try {
            int competitionId1 = Integer.valueOf(allCompetitions.get(0).getId());
            int competitionId2 = Integer.valueOf(allCompetitions.get(1).getId());

            List<Competitor> listOfCompetitors1 = new ArrayList<Competitor>();
            listOfCompetitors1 = competitionResultsDAO.getCompetitorIdByCompetitionAndDivision(competitionId1, SQLConstants.STOCK_DIVISION);
            List<Competitor> listOfCompetitors2 = new ArrayList<Competitor>();
            listOfCompetitors2 = competitionResultsDAO.getCompetitorIdByCompetitionAndDivision(competitionId2, SQLConstants.STOCK_DIVISION);

            Map<Competitor, List<Firearm>> competitorFirearmMap1 = new HashMap<>();
            for (int i = 0; i < listOfCompetitors1.size(); i++) {
                int competitorId = Integer.valueOf(listOfCompetitors1.get(i).getCompetitorId());
                List<Firearm> firearmList = competitionResultsDAO.getCompetitorFirearmByCompetitionAndDivision(competitorId, competitionId1, SQLConstants.STOCK_DIVISION);
                competitorFirearmMap1.put(listOfCompetitors1.get(i), firearmList);
            }

            Map<Competitor, List<Firearm>> competitorFirearmMap2 = new HashMap<>();
            for (int i = 0; i < listOfCompetitors2.size(); i++) {
                int competitorId = Integer.valueOf(listOfCompetitors2.get(i).getCompetitorId());
                List<Firearm> firearmList = competitionResultsDAO.getCompetitorFirearmByCompetitionAndDivision(competitorId, competitionId2, SQLConstants.STOCK_DIVISION);
                competitorFirearmMap2.put(listOfCompetitors2.get(i), firearmList);
            }

            Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap1 = new HashMap<>();
            for (Map.Entry<Competitor, List<Firearm>> entry : competitorFirearmMap1.entrySet()) {
                List<Firearm> firearmList1 = entry.getValue();
                for (int i = 0; i < firearmList1.size(); i++) {
                    int competitorId1 = Integer.valueOf(entry.getKey().getCompetitorId());
                    int firearmId1 = Integer.valueOf(firearmList1.get(i).getId());
                    CompetitionResultsRow competitionResultsRow1 = competitionResultsDAO.getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitionId1, competitorId1, firearmId1, SQLConstants.STOCK_DIVISION);
                    competitorResultsMap1.put(new CompetitorFirearmKey(competitorId1, firearmId1), competitionResultsRow1);
                }
            }

            Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap2 = new HashMap<>();
            for (Map.Entry<Competitor, List<Firearm>> entry : competitorFirearmMap2.entrySet()) {
                List<Firearm> firearmList2 = entry.getValue();
                for (int i = 0; i < firearmList2.size(); i++) {
                    int competitorId2 = Integer.valueOf(entry.getKey().getCompetitorId());
                    int firearmId2 = Integer.valueOf(firearmList2.get(i).getId());
                    CompetitionResultsRow competitionResultsRow2 = competitionResultsDAO.getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitionId2, competitorId2, firearmId2, SQLConstants.STOCK_DIVISION);
                    competitorResultsMap2.put(new CompetitorFirearmKey(competitorId2, firearmId2), competitionResultsRow2);
                }
            }

            Set<CompetitorFirearmKey> matchingSet = new HashSet<>(competitorResultsMap1.keySet());
            matchingSet.retainAll(competitorResultsMap2.keySet());

            Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingMap = new HashMap<>();
            for (CompetitorFirearmKey c : matchingSet) {
                List<CompetitionResultsRow> matchingCompetitionResultsRowList = new ArrayList<CompetitionResultsRow>();
                matchingCompetitionResultsRowList.add(competitorResultsMap1.get(c));
                matchingCompetitionResultsRowList.add(competitorResultsMap2.get(c));
                matchingMap.put(c, matchingCompetitionResultsRowList);
            }

            List<CompetitionResultsAverage> competitionResultsAverageList = new ArrayList<CompetitionResultsAverage>();
            for (Map.Entry<CompetitorFirearmKey, List<CompetitionResultsRow>> mmap : matchingMap.entrySet()) {

                CompetitionResultsAverage competitionResultsAverage = new CompetitionResultsAverage();

                List<CompetitionResultsRow> crlist = mmap.getValue();
                CompetitionResultsRow competitionResultsRow1 = crlist.get(0);
                CompetitionResultsRow competitionResultsRow2 = crlist.get(1);

                competitionResultsAverage.setFirst_name(competitionResultsRow1.getFirst_name());
                competitionResultsAverage.setLast_name(competitionResultsRow1.getLast_name());
                competitionResultsAverage.setFirearm_model(competitionResultsRow1.getFirearm_model());


                competitionResultsAverageList.add(competitionResultsAverage);
            }

            System.out.println("");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void calculateAveragesForThreeCompetitions() {

    }

    public boolean isMultipleCompetitions() {
        return allCompetitions.size() > 1 ? false : true;
    }

    public List<CompetitionResults> calculateClassifcation(List<CompetitionResults> list) {

        int groups = list.size() / 3;
        int classA = groups;
        int classB = groups * 2;

        for (int i=0; i < list.size(); i++) {
            if (i < classA) {
                list.get(i).setClassification("A");
            } else if (i >= classA && i < classB) {
                list.get(i).setClassification("B");
            } else {
                list.get(i).setClassification("C");
            }

        }

        return list;
    }

    public int getAccountIdFromSession() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = externalContext.getSessionMap();
        return (int) sessionMap.get("accountId");
    }

    public String getCompetitionDate() {
        if (null == competitionStockResultsList || competitionStockResultsList.size() == 0) {
            return "";
        }
        Date competitionDate = competitionStockResultsList.get(0).getCompetitionDetails().getDate();
        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");
        return format.format(competitionDate);
    }

    public String getAccountInfoName() {
        return (null == competitionStockResultsList || competitionStockResultsList.size() == 0) ? "" : competitionStockResultsList.get(0).getAccount().getName();
    }

    public String getCompetitionInfoDescription() {
        return (null == competitionStockResultsList || competitionStockResultsList.size() == 0) ? "" : competitionStockResultsList.get(0).getCompetition().getDescription();
    }

    public List<CompetitionResults> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<CompetitionResults> filtered) {
        this.filtered = filtered;
    }

    public void setCompetitionResultsDAO(CompetitionResultsDAO competitionResultsDAO) {
        this.competitionResultsDAO = competitionResultsDAO;
    }

    public CompetitionDAO getCompetitionDAO() {
        return competitionDAO;
    }

    public void setCompetitionDAO(CompetitionDAO competitionDAO) {
        this.competitionDAO = competitionDAO;
    }

    public List<CompetitionResults> getCompetitionStockResultsList() {
        return competitionStockResultsList;
    }

    public List<CompetitionResults> getCompetitionUnlimitedResultsList() {
        return competitionUnlimitedResultsList;
    }

    public List<CompetitionResults> getCompetitionPocketResultsList() {
        return competitionPocketResultsList;
    }

    public List<CompetitionResults> getCompetitionWomanResultsList() {
        return competitionWomanResultsList;
    }

    public List<CompetitionResults> getCompetitionSeniorResultsList() {
        return competitionSeniorResultsList;
    }

    public List<CompetitionResults> getCompetitionJuniorResultsList() {
        return competitionJuniorResultsList;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getCompetitionDescription() {
        return competitionDescription;
    }

    public void setCompetitionDescription(String competitionDescription) {
        this.competitionDescription = competitionDescription;
    }

    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public boolean isRenderSingleScores() {
        return renderSingleScores;
    }

    public void setRenderSingleScores(boolean renderSingleScores) {
        this.renderSingleScores = renderSingleScores;
    }

    public boolean isRenderAveragedScores() {
        return renderAveragedScores;
    }

    public void setRenderAveragedScores(boolean renderAveragedScores) {
        this.renderAveragedScores = renderAveragedScores;
    }

    public boolean isDisableAveragedScoresButton() {
        return disableAveragedScoresButton;
    }

    public void setDisableAveragedScoresButton(boolean disableAveragedScoresButton) {
        this.disableAveragedScoresButton = disableAveragedScoresButton;
    }

    public List<Competition> getAllCompetitions() {
        return allCompetitions;
    }

    public void setAllCompetitions(List<Competition> allCompetitions) {
        this.allCompetitions = allCompetitions;
    }
}
