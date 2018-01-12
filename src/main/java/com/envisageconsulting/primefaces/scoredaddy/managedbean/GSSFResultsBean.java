package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.CompetitionResultsComparator;
import com.envisageconsulting.primefaces.scoredaddy.Constants;
import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
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
import java.util.stream.Stream;

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
    private String competitionDateAverages;
    private String previousCompetitionDate;
    private String currentCompetitionDate;
    private String accountName;
    private String competitionDescription;

    private Competition competition;

    private boolean renderSingleScores;
    private boolean renderAveragedScores;
    private boolean disableAveragedScoresButton;

    private List<CompetitionResultsAverage> competitionStockResultsAverageList;
    private List<CompetitionResultsAverage> competitionUnlimitedResultsAverageList;
    private List<CompetitionResultsAverage> competitionPocketResultsAverageList;
    private List<CompetitionResultsAverage> competitionWomanResultsAverageList;
    private List<CompetitionResultsAverage> competitionSeniorResultsAverageList;
    private List<CompetitionResultsAverage> competitionJuniorResultsAverageList;
    private List<CompetitionResultsAverage> resultsAverageListFiltered;

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
            competitionStockResultsAverageList = calculateAveragesForTwoCompetitions(allCompetitions, SQLConstants.STOCK_DIVISION);
            competitionUnlimitedResultsAverageList = calculateAveragesForTwoCompetitions(allCompetitions, SQLConstants.UNLIMITED_DIVISION);
            competitionPocketResultsAverageList = calculateAveragesForTwoCompetitions(allCompetitions, SQLConstants.POCKET_DIVISION);
            competitionWomanResultsAverageList = calculateAveragesForTwoCompetitions(allCompetitions, SQLConstants.WOMAN_DIVISION);
            competitionSeniorResultsAverageList = calculateAveragesForTwoCompetitions(allCompetitions, SQLConstants.SENIOR_DIVISION);
            competitionJuniorResultsAverageList = calculateAveragesForTwoCompetitions(allCompetitions, SQLConstants.JUNIOR_DIVISION);
        } else {
            calculateAveragesForThreeCompetitions();
        }
    }

    public List<Competitor> getListOfCompetitorsForByCompetitionIdAndDivision(int competitionId, String division) throws Exception {
        return competitionResultsDAO.getCompetitorIdByCompetitionAndDivision(competitionId, division);
    }

    public Map<Competitor, List<Firearm>> getListOfCompetitorFirearms(List<Competitor> listOfCompetitors, int competitionId, String division) throws Exception {
        Map<Competitor, List<Firearm>> competitorFirearmMap = new HashMap<>();
        for (int i = 0; i < listOfCompetitors.size(); i++) {
            int competitorId = Integer.valueOf(listOfCompetitors.get(i).getCompetitorId());
            List<Firearm> firearmList = competitionResultsDAO.getCompetitorFirearmByCompetitionAndDivision(competitorId, competitionId, division);
            competitorFirearmMap.put(listOfCompetitors.get(i), firearmList);
        }
        return competitorFirearmMap;
    }

    public Map<CompetitorFirearmKey, CompetitionResultsRow> getCompetitionResultsByCompetitionCompetitorFirearmDivision(Map<Competitor, List<Firearm>> competitorFirearmMap, int competitionId, String division) throws Exception {
        Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap = new HashMap<>();
        for (Map.Entry<Competitor, List<Firearm>> entry : competitorFirearmMap.entrySet()) {
            List<Firearm> firearmList = entry.getValue();
            for (int i = 0; i < firearmList.size(); i++) {
                int competitorId = Integer.valueOf(entry.getKey().getCompetitorId());
                int firearmId = Integer.valueOf(firearmList.get(i).getId());
                CompetitionResultsRow competitionResultsRow = competitionResultsDAO.getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitionId, competitorId, firearmId, division);
                competitorResultsMap.put(new CompetitorFirearmKey(competitorId, firearmId), competitionResultsRow);
            }
        }
        return competitorResultsMap;
    }

    public Map<CompetitorFirearmKey, List<CompetitionResultsRow>> getMatchingMapOfEntries(Set<CompetitorFirearmKey> matchingSet, Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap1, Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap2) {
        Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingMap = new HashMap<>();
        for (CompetitorFirearmKey c : matchingSet) {
            List<CompetitionResultsRow> matchingCompetitionResultsRowList = new ArrayList<CompetitionResultsRow>();
            matchingCompetitionResultsRowList.add(competitorResultsMap1.get(c));
            matchingCompetitionResultsRowList.add(competitorResultsMap2.get(c));
            matchingMap.put(c, matchingCompetitionResultsRowList);
        }
        return matchingMap;
    }

    public List<CompetitionResultsAverage> getCompetitionResultsAverageList(Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingMap, String division) {

        List<CompetitionResultsAverage> competitionResultsAverageList = new ArrayList<CompetitionResultsAverage>();

        for (Map.Entry<CompetitorFirearmKey, List<CompetitionResultsRow>> mmap : matchingMap.entrySet()) {

            CompetitionResultsAverage competitionResultsAverage = new CompetitionResultsAverage();

            List<CompetitionResultsRow> crlist = mmap.getValue();
            CompetitionResultsRow previousCompetitionResultsRow = crlist.get(0);
            CompetitionResultsRow currentCompetitionResultsRow = crlist.get(1);

            competitionResultsAverage.setFirst_name(currentCompetitionResultsRow.getFirst_name());
            competitionResultsAverage.setLast_name(currentCompetitionResultsRow.getLast_name());
            competitionResultsAverage.setFirearm_model(currentCompetitionResultsRow.getFirearm_model());
            competitionResultsAverage.setCurrent_date(currentCompetitionResultsRow.getDate());
            competitionResultsAverage.setPrevious_date(previousCompetitionResultsRow.getDate());
            competitionResultsAverage.setCurrent_x(currentCompetitionResultsRow.getTotal_x());
            competitionResultsAverage.setCurrent_ten(currentCompetitionResultsRow.getTotal_ten());
            competitionResultsAverage.setCurrent_eight(currentCompetitionResultsRow.getTotal_eight());
            competitionResultsAverage.setCurrent_five(currentCompetitionResultsRow.getTotal_five());
            competitionResultsAverage.setCurrent_misses(currentCompetitionResultsRow.getTotal_misses());
            competitionResultsAverage.setCurrent_penalty(currentCompetitionResultsRow.getPenalty());
            competitionResultsAverage.setCurrent_score(currentCompetitionResultsRow.getFinal_score());
            competitionResultsAverage.setPrevious_score(previousCompetitionResultsRow.getFinal_score());
            competitionResultsAverage.setPrevious_x(previousCompetitionResultsRow.getTotal_x());

            float averageScore = (float) ((Float.valueOf(previousCompetitionResultsRow.getFinal_score()) + Float.valueOf(currentCompetitionResultsRow.getFinal_score())) / 2);
            competitionResultsAverage.setAverage_score(String.valueOf(averageScore));
            competitionResultsAverage.setTotal_x(String.valueOf(Integer.valueOf(previousCompetitionResultsRow.getTotal_x()) + Integer.valueOf(currentCompetitionResultsRow.getTotal_x())));

            competitionResultsAverageList.add(competitionResultsAverage);
            Collections.sort(competitionResultsAverageList, new CompetitionResultsComparator());

            for (int i = 0; i < competitionResultsAverageList.size(); i++) {
                competitionResultsAverageList.get(i).setRank(String.valueOf(i + 1));
            }

            if (division.equalsIgnoreCase(SQLConstants.STOCK_DIVISION)) {
                competitionResultsAverageList = calculateClassificationForAverage(competitionResultsAverageList);
            }
        }

        return competitionResultsAverageList;
    }

    public List<CompetitionResultsAverage> calculateAveragesForTwoCompetitions(List<Competition> allCompetitions, String division) {

        List<CompetitionResultsAverage> competitionResultsAverageList = new ArrayList<CompetitionResultsAverage>();

        try {
            int competitionId1 = Integer.valueOf(allCompetitions.get(0).getId());
            int competitionId2 = Integer.valueOf(allCompetitions.get(1).getId());

            competitionDateAverages = DateUtils.getDateWithFullMonthSpellingAsString(allCompetitions.get(1).getDate());
            previousCompetitionDate = DateUtils.getDate(allCompetitions.get(0).getDate());
            currentCompetitionDate = DateUtils.getDate(allCompetitions.get(1).getDate());

            List<List<Competitor>> listOfCompetitors = new ArrayList<List<Competitor>>();
            List<Map<Competitor, List<Firearm>>> competitorFirearmMap = new ArrayList<>();
            List<Map<CompetitorFirearmKey, CompetitionResultsRow>> competitorResultsMap = new ArrayList<>();

            for (int i=0; i < allCompetitions.size(); i++) {
                listOfCompetitors.add(getListOfCompetitorsForByCompetitionIdAndDivision(Integer.valueOf(allCompetitions.get(i).getId()), division));
                competitorFirearmMap.add(getListOfCompetitorFirearms(listOfCompetitors.get(i), Integer.valueOf(allCompetitions.get(i).getId()), division));
                competitorResultsMap.add(getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitorFirearmMap.get(i), Integer.valueOf(allCompetitions.get(i).getId()), division));
            }

            // Get Unique List of Entries (Competitor & Firearm)
            /*List<Set<CompetitorFirearmKey>> unqiueSetListOfEntries = new ArrayList<Set<CompetitorFirearmKey>>();
            for (int j=0; j < competitorResultsMap.size(); j++) {
                if (j != competitorResultsMap.size()-1) {
                    Set<CompetitorFirearmKey> thisMatchingSet = new HashSet<>(competitorResultsMap.get(j).keySet());
                    for (int k = 0; k < competitorResultsMap.size(); k++) {
                        if (!(j<=k)) {
                            thisMatchingSet.retainAll(competitorResultsMap.get(k).keySet());
                            unqiueSetListOfEntries.add(thisMatchingSet);
                        }
                    }
                }
            }*/

            List<Competitor> listOfCompetitors1 = getListOfCompetitorsForByCompetitionIdAndDivision(competitionId1, division);
            List<Competitor> listOfCompetitors2 = getListOfCompetitorsForByCompetitionIdAndDivision(competitionId2, division);

            Map<Competitor, List<Firearm>> competitorFirearmMap1 = getListOfCompetitorFirearms(listOfCompetitors1, competitionId1, division);
            Map<Competitor, List<Firearm>> competitorFirearmMap2 = getListOfCompetitorFirearms(listOfCompetitors2, competitionId2, division);

            Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap1 = getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitorFirearmMap1, competitionId1, division);
            Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap2 = getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitorFirearmMap2, competitionId2, division);

            Set<CompetitorFirearmKey> matchingSet = new HashSet<>(competitorResultsMap1.keySet());
            matchingSet.retainAll(competitorResultsMap2.keySet());


            Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingMap = getMatchingMapOfEntries(matchingSet, competitorResultsMap1, competitorResultsMap2);

            competitionResultsAverageList = getCompetitionResultsAverageList(matchingMap, division);

            //Mismatches
            for(Map.Entry<CompetitorFirearmKey, CompetitionResultsRow> map1 : competitorResultsMap1.entrySet()) {
                if (!competitorResultsMap2.containsKey(map1.getKey())) {
                    CompetitionResultsAverage mmRow1 = new CompetitionResultsAverage();
                    mmRow1.setFirst_name(map1.getValue().getFirst_name());
                    mmRow1.setLast_name(map1.getValue().getLast_name());
                    mmRow1.setFirearm_model(map1.getValue().getFirearm_model());
                    if (division.equals(SQLConstants.STOCK_DIVISION)) {
                        mmRow1.setRank("UNQ");
                    } else {
                        mmRow1.setRank("UNQ");
                    }
                    competitionResultsAverageList.add(mmRow1);
                }
            }

            for(Map.Entry<CompetitorFirearmKey, CompetitionResultsRow> map2 : competitorResultsMap2.entrySet()) {
                if (!competitorResultsMap1.containsKey(map2.getKey())) {
                    CompetitionResultsAverage mmRow2 = new CompetitionResultsAverage();
                    mmRow2.setFirst_name(map2.getValue().getFirst_name());
                    mmRow2.setLast_name(map2.getValue().getLast_name());
                    mmRow2.setFirearm_model(map2.getValue().getFirearm_model());
                    if (division.equals(SQLConstants.STOCK_DIVISION)) {
                        mmRow2.setRank("UNQ");
                    } else {
                        mmRow2.setRank("UNQ");
                    }
                    competitionResultsAverageList.add(mmRow2);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return competitionResultsAverageList;

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

    public List<CompetitionResultsAverage> calculateClassificationForAverage(List<CompetitionResultsAverage> list) {

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

    public String getPreviousCompetitionDate() {
        return previousCompetitionDate;
    }

    public String getCurrentCompetitionDate() {
        return currentCompetitionDate;
    }

    public String getCompetitionDateAverages() {
        return competitionDateAverages;
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

    public List<CompetitionResultsAverage> getCompetitionStockResultsAverageList() {
        return competitionStockResultsAverageList;
    }

    public void setCompetitionStockResultsAverageList(List<CompetitionResultsAverage> competitionStockResultsAverageList) {
        this.competitionStockResultsAverageList = competitionStockResultsAverageList;
    }

    public List<CompetitionResultsAverage> getCompetitionUnlimitedResultsAverageList() {
        return competitionUnlimitedResultsAverageList;
    }

    public void setCompetitionUnlimitedResultsAverageList(List<CompetitionResultsAverage> competitionUnlimitedResultsAverageList) {
        this.competitionUnlimitedResultsAverageList = competitionUnlimitedResultsAverageList;
    }

    public List<CompetitionResultsAverage> getCompetitionPocketResultsAverageList() {
        return competitionPocketResultsAverageList;
    }

    public void setCompetitionPocketResultsAverageList(List<CompetitionResultsAverage> competitionPocketResultsAverageList) {
        this.competitionPocketResultsAverageList = competitionPocketResultsAverageList;
    }

    public List<CompetitionResultsAverage> getCompetitionWomanResultsAverageList() {
        return competitionWomanResultsAverageList;
    }

    public void setCompetitionWomanResultsAverageList(List<CompetitionResultsAverage> competitionWomanResultsAverageList) {
        this.competitionWomanResultsAverageList = competitionWomanResultsAverageList;
    }

    public List<CompetitionResultsAverage> getCompetitionSeniorResultsAverageList() {
        return competitionSeniorResultsAverageList;
    }

    public void setCompetitionSeniorResultsAverageList(List<CompetitionResultsAverage> competitionSeniorResultsAverageList) {
        this.competitionSeniorResultsAverageList = competitionSeniorResultsAverageList;
    }

    public List<CompetitionResultsAverage> getCompetitionJuniorResultsAverageList() {
        return competitionJuniorResultsAverageList;
    }

    public void setCompetitionJuniorResultsAverageList(List<CompetitionResultsAverage> competitionJuniorResultsAverageList) {
        this.competitionJuniorResultsAverageList = competitionJuniorResultsAverageList;
    }

    public List<CompetitionResultsAverage> getResultsAverageListFiltered() {
        return resultsAverageListFiltered;
    }

    public void setResultsAverageListFiltered(List<CompetitionResultsAverage> resultsAverageListFiltered) {
        this.resultsAverageListFiltered = resultsAverageListFiltered;
    }
}
