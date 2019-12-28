package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.Division;
import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class ScoreSheetUtils {

    public static boolean validateScoreSheet(GSSFIndoorScoreSheet scoreSheet) {

        if (validateStockTotals(scoreSheet) && validatePocketTotals(scoreSheet)) {
            calculateTargetTotals(scoreSheet);
            calculateSumRow(scoreSheet);
            calculateTotalRow(scoreSheet);
            calculatePenalty(scoreSheet);
        } else {
            return false;
        }
        return true;
    }

    public static void calculateTotalRow(GSSFIndoorScoreSheet scoreSheet) {
        scoreSheet.getTotalRow().setX(scoreSheet.getSumRow().getX() * 10);
        scoreSheet.getTotalRow().setTen(scoreSheet.getSumRow().getTen() * 10);
        scoreSheet.getTotalRow().setEight(scoreSheet.getSumRow().getEight() * 8);
        scoreSheet.getTotalRow().setFive(scoreSheet.getSumRow().getFive() * 5);
        scoreSheet.setFinalScore(calculateTotalScore(scoreSheet));
    }

    public static int calculateTotalScore(GSSFIndoorScoreSheet scoreSheet) {
        return (scoreSheet.getTotalRow().getX() + scoreSheet.getTotalRow().getTen() + scoreSheet.getTotalRow().getEight() + scoreSheet.getTotalRow().getFive());
    }

    public static void calculatePenalty(GSSFIndoorScoreSheet scoreSheet) {
        scoreSheet.setPenalty(scoreSheet.getTargetOne().getMisses() + scoreSheet.getTargetTwo().getMisses());
    }

    public static void calculateSumRow(GSSFIndoorScoreSheet scoreSheet) {
        scoreSheet.getSumRow().setX(scoreSheet.getTargetOne().getX() + scoreSheet.getTargetTwo().getX());
        scoreSheet.getSumRow().setTen(scoreSheet.getTargetOne().getTen() + scoreSheet.getTargetTwo().getTen());
        scoreSheet.getSumRow().setEight(scoreSheet.getTargetOne().getEight() + scoreSheet.getTargetTwo().getEight());
        scoreSheet.getSumRow().setFive(scoreSheet.getTargetOne().getFive() + scoreSheet.getTargetTwo().getFive());
        scoreSheet.getSumRow().setMisses(scoreSheet.getTargetOne().getMisses() + scoreSheet.getTargetTwo().getMisses());
        scoreSheet.getSumRow().setTotal(scoreSheet.getTargetOne().getTotal() + scoreSheet.getTargetTwo().getTotal());
    }

    public static void calculateTargetTotals(GSSFIndoorScoreSheet scoreSheet) {
        scoreSheet.getTargetOne().setTotal(calculateTargetOneTotals(scoreSheet));
        scoreSheet.getTargetTwo().setTotal(calculateTargetTwoTotals(scoreSheet));
    }

    public static boolean validateStockTotals(GSSFIndoorScoreSheet scoreSheet) {

        boolean pass = true;

        if (!scoreSheet.getDivsion().isPocket()) {
            if (calculateTargetOneTotals(scoreSheet) != 20) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 1 totals are incorrect!"));
                pass = false;
            }
            if (calculateTargetTwoTotals(scoreSheet) != 30) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 2 totals are incorrect!"));
                pass = false;
            }
        }
        return pass;
    }

    public static boolean validatePocketTotals(GSSFIndoorScoreSheet scoreSheet) {

        boolean pass = true;

        if (scoreSheet.getDivsion().isPocket()) {
            if (calculateTargetOneTotals(scoreSheet) != 10) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 1 totals are incorrect!"));
                pass = false;
            }
            if (calculateTargetTwoTotals(scoreSheet) != 15) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Target 2 totals are incorrect!"));
                pass = false;
            }
        }
        return pass;
    }

    public static int calculateTargetOneTotals(GSSFIndoorScoreSheet scoreSheet) {
        return (scoreSheet.getTargetOne().getX() + scoreSheet.getTargetOne().getTen() + scoreSheet.getTargetOne().getEight() + scoreSheet.getTargetOne().getFive() + scoreSheet.getTargetOne().getMisses());
    }

    public static int calculateTargetTwoTotals(GSSFIndoorScoreSheet scoreSheet) {
        return (scoreSheet.getTargetTwo().getX() + scoreSheet.getTargetTwo().getTen() + scoreSheet.getTargetTwo().getEight() + scoreSheet.getTargetTwo().getFive() + scoreSheet.getTargetTwo().getMisses());
    }

    public static String getDivisionForSql(Division division) {
        if (division.isStock()) {
            return SQLConstants.STOCK_DIVISION;
        } else if (division.isUnlimited()){
            return SQLConstants.UNLIMITED_DIVISION;
        } else if (division.isPocket()) {
            return SQLConstants.POCKET_DIVISION;
        } else {
            return SQLConstants.RIMFIRE_DIVISION;
        }
    }

    public static String getConvertedDivisionCode(String division) {

        if (division.equals("GSSF_UNLIMITED")) {
            return SQLConstants.UNLIMITED_DIVISION;
        }
        if (division.equals("GSSF_STOCK")) {
            return SQLConstants.STOCK_DIVISION;
        }
        if (division.equals("GSSF_POCKET")) {
            return SQLConstants.POCKET_DIVISION;
        }
        if (division.equals("GSSF_RIMFIRE")) {
            return SQLConstants.RIMFIRE_DIVISION;
        }

        return null;
    }
}
