package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.CompetitionResultsComparator;
import com.envisageconsulting.primefaces.scoredaddy.DateUtils;
import com.envisageconsulting.primefaces.scoredaddy.SQLConstants;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

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

    public Map<CompetitorFirearmKey, List<CompetitionResultsRow>> getMatchingMapOfEntries(Set<CompetitorFirearmKey> matchingSet, List<Map<CompetitorFirearmKey, CompetitionResultsRow>> competitorResultsMapList) {
        Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingMap = new HashMap<>();
        for (CompetitorFirearmKey c : matchingSet) {
            List<CompetitionResultsRow> matchingCompetitionResultsRowList = new ArrayList<CompetitionResultsRow>();
            for (int b=0; b < competitorResultsMapList.size(); b++) {
                matchingCompetitionResultsRowList.add(competitorResultsMapList.get(b).get(c));
                matchingMap.put(c, matchingCompetitionResultsRowList);
            }
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

            competitionDateAverages = DateUtils.getDateWithFullMonthSpellingAsString(allCompetitions.get(1).getDate());
            previousCompetitionDate = DateUtils.getDate(allCompetitions.get(0).getDate());
            currentCompetitionDate = DateUtils.getDate(allCompetitions.get(1).getDate());

            List<List<Competitor>> listOfCompetitors = new ArrayList<List<Competitor>>();
            List<Map<Competitor, List<Firearm>>> competitorFirearmMapList = new ArrayList<>();
            List<Map<CompetitorFirearmKey, CompetitionResultsRow>> competitorResultsMapList = new ArrayList<>();

            for (int i = 0; i < allCompetitions.size(); i++) {
                listOfCompetitors.add(getListOfCompetitorsForByCompetitionIdAndDivision(Integer.valueOf(allCompetitions.get(i).getId()), division));
                competitorFirearmMapList.add(getListOfCompetitorFirearms(listOfCompetitors.get(i), Integer.valueOf(allCompetitions.get(i).getId()), division));
                competitorResultsMapList.add(getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitorFirearmMapList.get(i), Integer.valueOf(allCompetitions.get(i).getId()), division));
            }

            Multimap<CompetitorFirearmKey, CompetitionResultsRow> combinedCompetitorResultsMultiMap = ArrayListMultimap.create();
            for (int j = 0; j < competitorResultsMapList.size(); j++) {
                for (Map.Entry<CompetitorFirearmKey, CompetitionResultsRow> entry : competitorResultsMapList.get(j).entrySet()) {
                    combinedCompetitorResultsMultiMap.put(entry.getKey(), entry.getValue());
                }
            }

            HashSet<CompetitorFirearmKey> matchingSetOfKeys = new HashSet<CompetitorFirearmKey>();
            HashSet<CompetitorFirearmKey> tempSetMatching = new HashSet<CompetitorFirearmKey>();

            for (CompetitorFirearmKey matchingKey : combinedCompetitorResultsMultiMap.keys()) {
                if (!tempSetMatching.add(matchingKey)) {
                    matchingSetOfKeys.add(matchingKey);
                }
            }

            List<CompetitorFirearmKey> nonMatchingSetOfKeys = new ArrayList<CompetitorFirearmKey>();
            List<CompetitorFirearmKey> tempSetNonMatching = new ArrayList<CompetitorFirearmKey>();
            for (CompetitorFirearmKey nonMatchingKey : combinedCompetitorResultsMultiMap.keys()) {
                if (tempSetNonMatching.contains(nonMatchingKey)) {
                    nonMatchingSetOfKeys.remove(nonMatchingKey);
                } else {
                    tempSetNonMatching.add(nonMatchingKey);
                    nonMatchingSetOfKeys.add(nonMatchingKey);
                }
            }

            Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingEntriesMap = getMatchingMapOfEntries(matchingSetOfKeys, competitorResultsMapList);

            competitionResultsAverageList = getCompetitionResultsAverageList(matchingEntriesMap, division);

            // Get Unqualified Entries - First we need to convert MultiMap to Map

            Map<CompetitorFirearmKey, CompetitionResultsRow> combinedCompetitorResultsMap = new HashMap<>();
            for (Map.Entry entry : combinedCompetitorResultsMultiMap.entries()) {
                combinedCompetitorResultsMap.put((CompetitorFirearmKey)entry.getKey(), (CompetitionResultsRow)entry.getValue());
            }

            for (CompetitorFirearmKey nonMatchingKey : nonMatchingSetOfKeys) {
                if (combinedCompetitorResultsMap.containsKey(nonMatchingKey)) {
                    CompetitionResultsAverage unqalifiedRow = new CompetitionResultsAverage();
                    unqalifiedRow.setFirst_name(combinedCompetitorResultsMap.get(nonMatchingKey).getFirst_name());
                    unqalifiedRow.setLast_name(combinedCompetitorResultsMap.get(nonMatchingKey).getLast_name());
                    unqalifiedRow.setFirearm_model(combinedCompetitorResultsMap.get(nonMatchingKey).getFirearm_model());
                    if (division.equals(SQLConstants.STOCK_DIVISION)) {
                        unqalifiedRow.setRank("UNQ");
                    } else {
                        unqalifiedRow.setRank("UNQ");
                    }
                    competitionResultsAverageList.add(unqalifiedRow);
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
