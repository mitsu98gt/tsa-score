package com.envisageconsulting.primefaces.scoredaddy.managedbean;

import com.envisageconsulting.primefaces.scoredaddy.*;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.CompetitionResultsDAO;
import com.envisageconsulting.primefaces.scoredaddy.dao.TournamentDAO;
import com.envisageconsulting.primefaces.scoredaddy.domain.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;

@ViewScoped
@ManagedBean(name="gssfResultsBean")
public class GSSFResultsBean implements Serializable {

    @ManagedProperty("#{competitionResultsDAO}")
    private CompetitionResultsDAO competitionResultsDAO;

    @ManagedProperty("#{competitionDAO}")
    private CompetitionDAO competitionDAO;

    @ManagedProperty("#{tournamentDAO}")
    private TournamentDAO tournamentDAO;

    private List<CompetitionResults> competitionStockResultsList;
    private List<CompetitionResults> competitionStockResultsListAdditionalEntries;
    private List<CompetitionResults> competitionUnlimitedResultsList;
    private List<CompetitionResults> competitionUnlimitedResultsListAdditionalEntries;
    private List<CompetitionResults> competitionPocketResultsList;
    private List<CompetitionResults> competitionPocketResultsListAdditionalEntries;
    private List<CompetitionResults> competitionRimfireResultsList;
    private List<CompetitionResults> competitionRimfireResultsListAdditionalEntries;
    private List<CompetitionResults> competitionWomanResultsList;
    private List<CompetitionResults> competitionSeniorResultsList;
    private List<CompetitionResults> competitionJuniorResultsList;
    private List<CompetitionResults> filtered;

    private List<Competition> allCompetitions;
    private List<Tournament> allTournaments;

    private String currentCompetitionFullSpellingDate;
    private String firstCompetitionDate;
    private String previousCompetitionDate;
    private String currentCompetitionDate;
    private String accountName;
    private String competitionDescription;

    private Competition competition;
    private Tournament tournament;

    private boolean disableAveragedScoresButton;
    private boolean renderSingleScores;
    private boolean renderDoubleScores;
    private boolean renderTrippleScores;


    private List<CompetitionResultsAverage> competitionStockResultsAverageList;
    private List<CompetitionResultsAverage> competitionUnlimitedResultsAverageList;
    private List<CompetitionResultsAverage> competitionPocketResultsAverageList;
    private List<CompetitionResultsAverage> competitionRimfireResultsAverageList;
    private List<CompetitionResultsAverage> competitionWomanResultsAverageList;
    private List<CompetitionResultsAverage> competitionSeniorResultsAverageList;
    private List<CompetitionResultsAverage> competitionJuniorResultsAverageList;
    private List<CompetitionResultsAverage> resultsAverageListFiltered;

    @PostConstruct
    public void init() {

        renderSingleScores = false;
        renderDoubleScores = false;
        renderTrippleScores = false;

        try {
            allTournaments = tournamentDAO.getAllGlockTournamentsByAccountIdAndStatus(SessionUtils.getAccountId(), "I");
            accountName = SessionUtils.getAccountName();
            if (null == allCompetitions || allCompetitions.size() == 0) {
                competitionDescription = "";
            } else {
                competitionDescription = allCompetitions.get(0).getDescription();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        disableAveragedScoresButton = true;
    }

    public void viewSingleScores() {
        try {
            renderSingleScores = true;
            renderDoubleScores = false;
            renderTrippleScores = false;

            competitionStockResultsList = calculateClassifcation(competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.STOCK_DIVISION, Integer.valueOf(competition.getId()), "false"));
            competitionStockResultsListAdditionalEntries = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.STOCK_DIVISION, Integer.valueOf(competition.getId()), "true");
            for (CompetitionResults results : competitionStockResultsListAdditionalEntries) {
                results.setRank("");
                results.setClassification("Additional");
                competitionStockResultsList.add(results);
            }

            competitionUnlimitedResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.UNLIMITED_DIVISION, Integer.valueOf(competition.getId()), "false");
            competitionUnlimitedResultsListAdditionalEntries = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.UNLIMITED_DIVISION, Integer.valueOf(competition.getId()), "true");
            for (CompetitionResults results : competitionUnlimitedResultsListAdditionalEntries) {
                results.setRank("");
                results.setClassification("Additional");
                competitionUnlimitedResultsList.add(results);
            }

            competitionPocketResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.POCKET_DIVISION, Integer.valueOf(competition.getId()), "false");
            competitionPocketResultsListAdditionalEntries = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.POCKET_DIVISION, Integer.valueOf(competition.getId()), "true");
            for (CompetitionResults results : competitionPocketResultsListAdditionalEntries) {
                results.setRank("");
                results.setClassification("Additional");
                competitionPocketResultsList.add(results);
            }

            competitionRimfireResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.RIMFIRE_DIVISION, Integer.valueOf(competition.getId()), "false");
            competitionRimfireResultsListAdditionalEntries = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.RIMFIRE_DIVISION, Integer.valueOf(competition.getId()), "true");
            for (CompetitionResults results : competitionRimfireResultsListAdditionalEntries) {
                results.setRank("");
                results.setClassification("Additional");
                competitionRimfireResultsList.add(results);
            }

            competitionWomanResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.WOMAN_DIVISION, Integer.valueOf(competition.getId()), "false");
            competitionSeniorResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.SENIOR_DIVISION, Integer.valueOf(competition.getId()), "false");
            competitionJuniorResultsList = competitionResultsDAO.getCompetitionResultsByDivisionAndCompetitionId(SQLConstants.JUNIOR_DIVISION, Integer.valueOf(competition.getId()), "false");
            currentCompetitionFullSpellingDate = DateUtils.getDateWithFullMonthSpellingAsString(competitionStockResultsList.get(0).getCompetitionDetails().getDate());
            competitionDescription = getCompetitionInfoDescription();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAveragedScores() {

        renderSingleScores = false;

        competitionStockResultsAverageList = calculateAveragesForTournament(allCompetitions, SQLConstants.STOCK_DIVISION, "false");
        competitionUnlimitedResultsAverageList = calculateAveragesForTournament(allCompetitions, SQLConstants.UNLIMITED_DIVISION, "false");
        competitionPocketResultsAverageList = calculateAveragesForTournament(allCompetitions, SQLConstants.POCKET_DIVISION, "false");
        competitionRimfireResultsAverageList = calculateAveragesForTournament(allCompetitions, SQLConstants.RIMFIRE_DIVISION, "false");
        competitionWomanResultsAverageList = calculateAveragesForTournament(allCompetitions, SQLConstants.WOMAN_DIVISION, "false");
        competitionSeniorResultsAverageList = calculateAveragesForTournament(allCompetitions, SQLConstants.SENIOR_DIVISION, "false");
        competitionJuniorResultsAverageList = calculateAveragesForTournament(allCompetitions, SQLConstants.JUNIOR_DIVISION, "false");
    }

    public void onTournamentChange() {
        try {
            allCompetitions = competitionDAO.getCompetitionsByTournamentIdAndStatus(tournament.getId(), "I");
            disableAveragedScoresButton = isMultipleCompetitions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CompetitionResultsAverage> calculateAveragesForTournament(List<Competition> allCompetitions, String division, String additionalEntries) {

        List<CompetitionResultsAverage> competitionResultsAverageList = new ArrayList<CompetitionResultsAverage>();

        try {

            if (allCompetitions.size() == 2) {
                renderDoubleScores = true;
                renderTrippleScores = false;

                currentCompetitionFullSpellingDate = DateUtils.getDateWithFullMonthSpellingAsString(allCompetitions.get(1).getDate());
                previousCompetitionDate = DateUtils.getDate(allCompetitions.get(0).getDate());
                currentCompetitionDate = DateUtils.getDate(allCompetitions.get(1).getDate());
            } else {
                renderDoubleScores = false;
                renderTrippleScores = true;
                currentCompetitionFullSpellingDate = DateUtils.getDateWithFullMonthSpellingAsString(allCompetitions.get(2).getDate());
                firstCompetitionDate = DateUtils.getDate(allCompetitions.get(0).getDate());
                previousCompetitionDate = DateUtils.getDate(allCompetitions.get(1).getDate());
                currentCompetitionDate = DateUtils.getDate(allCompetitions.get(2).getDate());
            }

            List<List<Competitor>> listOfCompetitors = new ArrayList<List<Competitor>>();
            List<Map<Competitor, List<Firearm>>> competitorFirearmMapList = new ArrayList<>();
            List<Map<CompetitorFirearmKey, CompetitionResultsRow>> competitorResultsMapList = new ArrayList<>();

            for (int i = 0; i < allCompetitions.size(); i++) {
                listOfCompetitors.add(getListOfCompetitorsForByCompetitionIdAndDivision(Integer.valueOf(allCompetitions.get(i).getId()), division, additionalEntries));
                competitorFirearmMapList.add(getListOfCompetitorFirearms(listOfCompetitors.get(i), Integer.valueOf(allCompetitions.get(i).getId()), division, additionalEntries));
                competitorResultsMapList.add(getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitorFirearmMapList.get(i), Integer.valueOf(allCompetitions.get(i).getId()), division, "false"));
            }

            // Combine all the entries from all competitions
            Multimap<CompetitorFirearmKey, CompetitionResultsRow> combinedCompetitorResultsMultiMap = ArrayListMultimap.create();
            for (int j = 0; j < competitorResultsMapList.size(); j++) {
                for (Map.Entry<CompetitorFirearmKey, CompetitionResultsRow> entry : competitorResultsMapList.get(j).entrySet()) {
                    combinedCompetitorResultsMultiMap.put(entry.getKey(), entry.getValue());
                }
            }

            // Ge the qualified entries
            HashSet<CompetitorFirearmKey> matchingSetOfKeys = new HashSet<CompetitorFirearmKey>();
            HashSet<CompetitorFirearmKey> tempSetMatching = new HashSet<CompetitorFirearmKey>();
            for (CompetitorFirearmKey matchingKey : combinedCompetitorResultsMultiMap.keys()) {
                if (tempSetMatching.contains(matchingKey)) {
                    matchingSetOfKeys.add(matchingKey);
                } else {
                    tempSetMatching.add(matchingKey);
                }
            }

            // Get the unqualified entries
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

            // Get the data for qualified entries
            Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingEntriesMap = getMatchingMapOfEntries(matchingSetOfKeys, competitorResultsMapList);

            // Sort the qualified entries if 3rd Round Match
            if (renderTrippleScores) {
                for (Map.Entry<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingEntries : matchingEntriesMap.entrySet()) {
                    Collections.sort(matchingEntries.getValue(), new CompetitionResultsRowComparator());
                }
            }

            // Get the calculated results of qualified entries
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
    
    public List<Competitor> getListOfCompetitorsForByCompetitionIdAndDivision(int competitionId, String division, String additionalEntries) throws Exception {
        return competitionResultsDAO.getCompetitorIdByCompetitionAndDivision(competitionId, division, additionalEntries);
    }

    public Map<Competitor, List<Firearm>> getListOfCompetitorFirearms(List<Competitor> listOfCompetitors, int competitionId, String division, String additionalEntries) throws Exception {
        Map<Competitor, List<Firearm>> competitorFirearmMap = new HashMap<>();
        for (int i = 0; i < listOfCompetitors.size(); i++) {
            int competitorId = Integer.valueOf(listOfCompetitors.get(i).getCompetitorId());
            List<Firearm> firearmList = competitionResultsDAO.getCompetitorFirearmByCompetitionAndDivision(competitorId, competitionId, division, additionalEntries);
            competitorFirearmMap.put(listOfCompetitors.get(i), firearmList);
        }
        return competitorFirearmMap;
    }

    public Map<CompetitorFirearmKey, CompetitionResultsRow> getCompetitionResultsByCompetitionCompetitorFirearmDivision(Map<Competitor, List<Firearm>> competitorFirearmMap, int competitionId, String division, String additionalEntries) throws Exception {
        Map<CompetitorFirearmKey, CompetitionResultsRow> competitorResultsMap = new HashMap<>();
        for (Map.Entry<Competitor, List<Firearm>> entry : competitorFirearmMap.entrySet()) {
            List<Firearm> firearmList = entry.getValue();
            for (int i = 0; i < firearmList.size(); i++) {
                int competitorId = Integer.valueOf(entry.getKey().getCompetitorId());
                int firearmId = Integer.valueOf(firearmList.get(i).getId());
                CompetitionResultsRow competitionResultsRow = competitionResultsDAO.getCompetitionResultsByCompetitionCompetitorFirearmDivision(competitionId, competitorId, firearmId, division, additionalEntries);
                competitorResultsMap.put(new CompetitorFirearmKey(competitorId, firearmId), competitionResultsRow);
            }
        }
        return competitorResultsMap;
    }

    public Map<CompetitorFirearmKey, List<CompetitionResultsRow>> getMatchingMapOfEntries(Set<CompetitorFirearmKey> matchingSet, List<Map<CompetitorFirearmKey, CompetitionResultsRow>> competitorResultsMapList) throws ParseException {
        Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingMap = new HashMap<>();
        for (CompetitorFirearmKey c : matchingSet) {
            List<CompetitionResultsRow> matchingCompetitionResultsRowList = new ArrayList<CompetitionResultsRow>();
            for (int b=0; b < competitorResultsMapList.size(); b++) {
                if (competitorResultsMapList.get(b).containsKey(c)){
                    matchingCompetitionResultsRowList.add(competitorResultsMapList.get(b).get(c));
                    matchingMap.put(c, matchingCompetitionResultsRowList);
                } else {
                    CompetitionResultsRow nonShootRow = new CompetitionResultsRow();
                    nonShootRow.setFinal_score("0");
                    nonShootRow.setTotal_x("0");
                    nonShootRow.setDate(DateUtils.getDateFromString("9999-12-31"));
                    matchingCompetitionResultsRowList.add(nonShootRow);
                    matchingMap.put(c, matchingCompetitionResultsRowList);
                }
            }
        }
        return matchingMap;
    }

    public List<CompetitionResultsAverage> getCompetitionResultsAverageList(Map<CompetitorFirearmKey, List<CompetitionResultsRow>> matchingMap, String division) {

        List<CompetitionResultsAverage> competitionResultsAverageList = new ArrayList<CompetitionResultsAverage>();

        for (Map.Entry<CompetitorFirearmKey, List<CompetitionResultsRow>> mmap : matchingMap.entrySet()) {

            CompetitionResultsAverage competitionResultsAverage = new CompetitionResultsAverage();
            CompetitionResultsRow previousCompetitionResultsRow = new CompetitionResultsRow();
            CompetitionResultsRow currentCompetitionResultsRow = new CompetitionResultsRow();
            CompetitionResultsRow firstCompetitionResultsRow = new CompetitionResultsRow();
            CompetitionResultsRow missedRow = new CompetitionResultsRow();

            List<CompetitionResultsRow> crlist = mmap.getValue();

            if (renderDoubleScores) {

                previousCompetitionResultsRow = crlist.get(0);
                currentCompetitionResultsRow = crlist.get(1);

            } else {

                String firstMatchDate = DateUtils.getDate(allCompetitions.get(0).getDate());
                String secondMatchDate = DateUtils.getDate(allCompetitions.get(1).getDate());
                String thirdMatchDate = DateUtils.getDate(allCompetitions.get(2).getDate());

                boolean first = false;
                boolean previous = false;
                boolean current = false;

                for (int resultRows=0; resultRows < crlist.size(); resultRows++) {

                    if (DateUtils.getDate(crlist.get(resultRows).getDate()).equals(firstMatchDate)) {
                        firstCompetitionResultsRow = crlist.get(resultRows);
                        missedRow = getDataForMissedRow(crlist.get(resultRows));
                        first = true;
                    } else if (DateUtils.getDate(crlist.get(resultRows).getDate()).equals(secondMatchDate)) {
                        previousCompetitionResultsRow = crlist.get(resultRows);
                        missedRow = getDataForMissedRow(crlist.get(resultRows));
                        previous = true;
                    } else if (DateUtils.getDate(crlist.get(resultRows).getDate()).equals(thirdMatchDate)) {
                        currentCompetitionResultsRow = crlist.get(resultRows);
                        missedRow = getDataForMissedRow(crlist.get(resultRows));
                        current = true;
                    }

                }

                if (!first) {
                    firstCompetitionResultsRow = missedRow;
                }
                if (!previous) {
                    previousCompetitionResultsRow = missedRow;
                }
                if (!current) {
                    currentCompetitionResultsRow = missedRow;
                }

            }

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

            competitionResultsAverage.setFirst_score(firstCompetitionResultsRow.getFinal_score());
            competitionResultsAverage.setFirst_x(firstCompetitionResultsRow.getTotal_x());

            competitionResultsAverage.setPrevious_score(previousCompetitionResultsRow.getFinal_score());
            competitionResultsAverage.setPrevious_x(previousCompetitionResultsRow.getTotal_x());

            competitionResultsAverage.setTop_score(crlist.get(0).getFinal_score());
            competitionResultsAverage.setTop_x(crlist.get(0).getTotal_x());
            competitionResultsAverage.setSecond_score(crlist.get(1).getFinal_score());
            competitionResultsAverage.setSecond_x(crlist.get(1).getTotal_x());

            float averageScore = (float) ((Float.valueOf(crlist.get(0).getFinal_score()) + Float.valueOf(crlist.get(1).getFinal_score())) / 2);
            competitionResultsAverage.setAverage_score(String.valueOf(averageScore));
            competitionResultsAverage.setTotal_x(String.valueOf(Integer.valueOf(crlist.get(0).getTotal_x()) + Integer.valueOf(crlist.get(1).getTotal_x())));

            competitionResultsAverageList.add(competitionResultsAverage);
            Collections.sort(competitionResultsAverageList, new CompetitionResultsAverageComparator());

            for (int i = 0; i < competitionResultsAverageList.size(); i++) {
                competitionResultsAverageList.get(i).setRank(String.valueOf(i + 1));
            }

            if (division.equalsIgnoreCase(SQLConstants.STOCK_DIVISION)) {
                competitionResultsAverageList = calculateClassificationForAverage(competitionResultsAverageList);
            }
        }

        return competitionResultsAverageList;
    }

    public boolean isMultipleCompetitions() {
        return allCompetitions.size() > 1 ? false : true;
    }

    public CompetitionResultsRow getDataForMissedRow(CompetitionResultsRow row) {
        CompetitionResultsRow missedRow =  new CompetitionResultsRow();
        missedRow.setFirst_name(row.getFirst_name());
        missedRow.setLast_name(row.getLast_name());
        missedRow.setFirearm_model(row.getFirearm_model());
        missedRow.setTotal_x("N/A");
        missedRow.setTotal_ten("N/A");
        missedRow.setTotal_eight("N/A");
        missedRow.setTotal_five("N/A");
        missedRow.setTotal_misses("N/A");
        missedRow.setPenalty("N/A");
        missedRow.setFinal_score("N/A");
        return missedRow;
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

    public String getCurrentCompetitionFullSpellingDate() {
        return currentCompetitionFullSpellingDate;
    }

    public String getFirstCompetitionDate() {
        return firstCompetitionDate;
    }

    public String getPreviousCompetitionDate() {
        return previousCompetitionDate;
    }

    public String getCurrentCompetitionDate() {
        return currentCompetitionDate;
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

    public TournamentDAO getTournamentDAO() {
        return tournamentDAO;
    }

    public void setTournamentDAO(TournamentDAO tournamentDAO) {
        this.tournamentDAO = tournamentDAO;
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

    public List<CompetitionResults> getCompetitionRimfireResultsList() {
        return competitionRimfireResultsList;
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public boolean isRenderSingleScores() {
        return renderSingleScores;
    }

    public void setRenderSingleScores(boolean renderSingleScores) {
        this.renderSingleScores = renderSingleScores;
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

    public List<Tournament> getAllTournaments() {
        return allTournaments;
    }

    public void setAllTournaments(List<Tournament> allTournaments) {
        this.allTournaments = allTournaments;
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

    public List<CompetitionResultsAverage> getCompetitionRimfireResultsAverageList() {
        return competitionRimfireResultsAverageList;
    }

    public void setCompetitionRimfireResultsAverageList(List<CompetitionResultsAverage> competitionRimfireResultsAverageList) {
        this.competitionRimfireResultsAverageList = competitionRimfireResultsAverageList;
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

    public boolean isRenderDoubleScores() {
        return renderDoubleScores;
    }

    public boolean isRenderTrippleScores() {
        return renderTrippleScores;
    }
}
