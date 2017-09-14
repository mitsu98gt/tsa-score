package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.GSSFIndoorScoreSheet;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class GSSFIndoorScoreSheetPDF {

    private static final String GSSF_INDOOR_SCORE_SHEET = "/GSSF_Indoor.pdf";

    public void savePDF(GSSFIndoorScoreSheet scoreSheet) throws Exception {

        //If no path specified, the save will save in tomcat bin directory
        PDDocument pdfDocument = createPDDcoument(scoreSheet);
        pdfDocument.save("C:/IFBIDEV/FillFormField.pdf");
        pdfDocument.close();

    }

    public InputStream downloadPDF(GSSFIndoorScoreSheet scoreSheet) throws Exception {

        PDDocument pdfDocument = createPDDcoument(scoreSheet);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        pdfDocument.save(out);
        pdfDocument.close();

        return new ByteArrayInputStream(out.toByteArray());

    }

    public PDDocument createPDDcoument(GSSFIndoorScoreSheet scoreSheet) throws Exception {

        PDDocument pdfDocument = PDDocument.load(getPDFInputStream());
        // get the document catalog
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        fillPDFData(acroForm, scoreSheet);
        return pdfDocument;
    }

    public InputStream getPDFInputStream() {
        return this.getClass().getResourceAsStream(GSSF_INDOOR_SCORE_SHEET);
    }

    public void fillPDFData(PDAcroForm acroForm, GSSFIndoorScoreSheet scoreSheet) throws Exception {
        
        PDTextField name = (PDTextField) acroForm.getField("name");
        name.setValue(scoreSheet.getCompetitor().getFullName());

        PDTextField date = (PDTextField) acroForm.getField("date");
        DateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        date.setValue(df.format(scoreSheet.getDate()));

        PDTextField model = (PDTextField) acroForm.getField("model");
        model.setValue(scoreSheet.getModel());

        PDTextField entry = (PDTextField) acroForm.getField("entry");
        entry.setValue(scoreSheet.getEntry());

        //

        if (scoreSheet.getCheckStock()) {
            PDField checkStock = (PDField) acroForm.getField("checkStock");
            ((PDCheckBox) checkStock).check();
        }

        if (scoreSheet.getCheckUnlimited()) {
            PDField checkUnlimited = (PDField) acroForm.getField("checkUnlimited");
            ((PDCheckBox) checkUnlimited).check();
        }

        if (scoreSheet.getCheckPocket()) {
            PDField checkPocket = (PDField) acroForm.getField("checkPocket");
            ((PDCheckBox) checkPocket).check();
        }

        if (scoreSheet.getCheckWoman()) {
            PDField checkWoman = (PDField) acroForm.getField("checkWoman");
            ((PDCheckBox) checkWoman).check();
        }

        if (scoreSheet.getCheckSenior()) {
            PDField checkSenior = (PDField) acroForm.getField("checkSenior");
            ((PDCheckBox) checkSenior).check();
        }

        if (scoreSheet.getCheckJunior()) {
            PDField checkJunior = (PDField) acroForm.getField("checkJunior");
            ((PDCheckBox) checkJunior).check();
        }

        //

        PDTextField targetOneX = (PDTextField) acroForm.getField("targetOneX");
        targetOneX.setValue(Integer.toString(scoreSheet.getTargetOneX()));

        PDTextField targetOneTen = (PDTextField) acroForm.getField("targetOneTen");
        targetOneTen.setValue(Integer.toString(scoreSheet.getTargetOneTen()));

        PDTextField targetOneEight = (PDTextField) acroForm.getField("targetOneEight");
        targetOneEight.setValue(Integer.toString(scoreSheet.getTargetOneEight()));

        PDTextField targetOneFive = (PDTextField) acroForm.getField("targetOneFive");
        targetOneFive.setValue(Integer.toString(scoreSheet.getTargetOneFive()));

        PDTextField targetOneMisses = (PDTextField) acroForm.getField("targetOneMisses");
        targetOneMisses.setValue(Integer.toString(scoreSheet.getTargetOneMisses()));

        PDTextField targetOneTotal = (PDTextField) acroForm.getField("targetOneTotal");
        targetOneTotal.setValue(Integer.toString(scoreSheet.getTargetOneTotal()));

        //

        PDTextField targetTwoX = (PDTextField) acroForm.getField("targetTwoX");
        targetTwoX.setValue(Integer.toString(scoreSheet.getTargetTwoX()));

        PDTextField targetTwoTen = (PDTextField) acroForm.getField("targetTwoTen");
        targetTwoTen.setValue(Integer.toString(scoreSheet.getTargetTwoTen()));

        PDTextField targetTwoEight = (PDTextField) acroForm.getField("targetTwoEight");
        targetTwoEight.setValue(Integer.toString(scoreSheet.getTargetTwoEight()));

        PDTextField targetTwoFive = (PDTextField) acroForm.getField("targetTwoFive");
        targetTwoFive.setValue(Integer.toString(scoreSheet.getTargetTwoFive()));

        PDTextField targetTwoMisses = (PDTextField) acroForm.getField("targetTwoMisses");
        targetTwoMisses.setValue(Integer.toString(scoreSheet.getTargetTwoMisses()));

        PDTextField targetTwoTotal = (PDTextField) acroForm.getField("targetTwoTotal");
        targetTwoTotal.setValue(Integer.toString(scoreSheet.getTargetTwoTotal()));

        //

        PDTextField sumX = (PDTextField) acroForm.getField("sumX");
        sumX.setValue(Integer.toString(scoreSheet.getSumX()));

        PDTextField sumTen = (PDTextField) acroForm.getField("sumTen");
        sumTen.setValue(Integer.toString(scoreSheet.getSumTen()));

        PDTextField sumEight = (PDTextField) acroForm.getField("sumEight");
        sumEight.setValue(Integer.toString(scoreSheet.getSumEight()));

        PDTextField sumFive = (PDTextField) acroForm.getField("sumFive");
        sumFive.setValue(Integer.toString(scoreSheet.getSumFive()));

        PDTextField sumMisses = (PDTextField) acroForm.getField("sumMisses");
        sumMisses.setValue(Integer.toString(scoreSheet.getSumMisses()));

        PDTextField sumTotal = (PDTextField) acroForm.getField("sumTotal");
        sumTotal.setValue(Integer.toString(scoreSheet.getSumTotal()));

        //

        PDTextField totalX = (PDTextField) acroForm.getField("totalX");
        totalX.setValue(Integer.toString(scoreSheet.getTotalX()));

        PDTextField totalTen = (PDTextField) acroForm.getField("totalTen");
        totalTen.setValue(Integer.toString(scoreSheet.getTotalTen()));

        PDTextField toalEight = (PDTextField) acroForm.getField("totalEight");
        toalEight.setValue(Integer.toString(scoreSheet.getTotalEight()));

        PDTextField totalFive = (PDTextField) acroForm.getField("totalFive");
        totalFive.setValue(Integer.toString(scoreSheet.getTotalFive()));

        PDTextField penalty = (PDTextField) acroForm.getField("penalty");
        penalty.setValue(Integer.toString(scoreSheet.getPenalty()));

        PDTextField finalScore = (PDTextField) acroForm.getField("finalScore");
        finalScore.setValue(Integer.toString(scoreSheet.getFinalScore()));

        //

        PDTextField rangeOfficerInitials = (PDTextField) acroForm.getField("rangeOfficerInitials");
        rangeOfficerInitials.setValue(scoreSheet.getRangeOfficerInitials());

        PDTextField competitorInitials = (PDTextField) acroForm.getField("competitorInitials");
        competitorInitials.setValue(scoreSheet.getCompetitorInitials());

        acroForm.flatten();

    }
}
