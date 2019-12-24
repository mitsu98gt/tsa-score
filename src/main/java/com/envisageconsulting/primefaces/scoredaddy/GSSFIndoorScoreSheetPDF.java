package com.envisageconsulting.primefaces.scoredaddy;

import com.envisageconsulting.primefaces.scoredaddy.domain.scoresheet.GSSFIndoorScoreSheet;
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

    private static final String GSSF_INDOOR_SCORE_SHEET = "/GSSF_Indoor_Rimfire.pdf";

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
        model.setValue(scoreSheet.getFirearm().getModel());

        PDTextField entry = (PDTextField) acroForm.getField("entry");
        entry.setValue(scoreSheet.getEntry());

        //

        if (scoreSheet.getDivsion().isStock()) {
            PDField checkStock = (PDField) acroForm.getField("checkStock");
            ((PDCheckBox) checkStock).check();
        }

        if (scoreSheet.getDivsion().isUnlimited()) {
            PDField checkUnlimited = (PDField) acroForm.getField("checkUnlimited");
            ((PDCheckBox) checkUnlimited).check();
        }

        if (scoreSheet.getDivsion().isPocket()) {
            PDField checkPocket = (PDField) acroForm.getField("checkPocket");
            ((PDCheckBox) checkPocket).check();
        }

        if (scoreSheet.getDivsion().isWoman()) {
            PDField checkWoman = (PDField) acroForm.getField("checkWoman");
            ((PDCheckBox) checkWoman).check();
        }

        if (scoreSheet.getDivsion().isSenior()) {
            PDField checkSenior = (PDField) acroForm.getField("checkSenior");
            ((PDCheckBox) checkSenior).check();
        }

        if (scoreSheet.getDivsion().isJunior()) {
            PDField checkJunior = (PDField) acroForm.getField("checkJunior");
            ((PDCheckBox) checkJunior).check();
        }

        //

        PDTextField targetOneX = (PDTextField) acroForm.getField("targetOneX");
        targetOneX.setValue(Integer.toString(scoreSheet.getTargetOne().getX()));

        PDTextField targetOneTen = (PDTextField) acroForm.getField("targetOneTen");
        targetOneTen.setValue(Integer.toString(scoreSheet.getTargetOne().getTen()));

        PDTextField targetOneEight = (PDTextField) acroForm.getField("targetOneEight");
        targetOneEight.setValue(Integer.toString(scoreSheet.getTargetOne().getEight()));

        PDTextField targetOneFive = (PDTextField) acroForm.getField("targetOneFive");
        targetOneFive.setValue(Integer.toString(scoreSheet.getTargetOne().getFive()));

        PDTextField targetOneMisses = (PDTextField) acroForm.getField("targetOneMisses");
        targetOneMisses.setValue(Integer.toString(scoreSheet.getTargetOne().getMisses()));

        PDTextField targetOneTotal = (PDTextField) acroForm.getField("targetOneTotal");
        targetOneTotal.setValue(Integer.toString(scoreSheet.getTargetOne().getTotal()));

        //

        PDTextField targetTwoX = (PDTextField) acroForm.getField("targetTwoX");
        targetTwoX.setValue(Integer.toString(scoreSheet.getTargetTwo().getX()));

        PDTextField targetTwoTen = (PDTextField) acroForm.getField("targetTwoTen");
        targetTwoTen.setValue(Integer.toString(scoreSheet.getTargetTwo().getTen()));

        PDTextField targetTwoEight = (PDTextField) acroForm.getField("targetTwoEight");
        targetTwoEight.setValue(Integer.toString(scoreSheet.getTargetTwo().getEight()));

        PDTextField targetTwoFive = (PDTextField) acroForm.getField("targetTwoFive");
        targetTwoFive.setValue(Integer.toString(scoreSheet.getTargetTwo().getFive()));

        PDTextField targetTwoMisses = (PDTextField) acroForm.getField("targetTwoMisses");
        targetTwoMisses.setValue(Integer.toString(scoreSheet.getTargetTwo().getMisses()));

        PDTextField targetTwoTotal = (PDTextField) acroForm.getField("targetTwoTotal");
        targetTwoTotal.setValue(Integer.toString(scoreSheet.getTargetTwo().getTotal()));

        //

        PDTextField sumX = (PDTextField) acroForm.getField("sumX");
        sumX.setValue(Integer.toString(scoreSheet.getSumRow().getX()));

        PDTextField sumTen = (PDTextField) acroForm.getField("sumTen");
        sumTen.setValue(Integer.toString(scoreSheet.getSumRow().getTen()));

        PDTextField sumEight = (PDTextField) acroForm.getField("sumEight");
        sumEight.setValue(Integer.toString(scoreSheet.getSumRow().getEight()));

        PDTextField sumFive = (PDTextField) acroForm.getField("sumFive");
        sumFive.setValue(Integer.toString(scoreSheet.getSumRow().getFive()));

        PDTextField sumMisses = (PDTextField) acroForm.getField("sumMisses");
        sumMisses.setValue(Integer.toString(scoreSheet.getSumRow().getMisses()));

        PDTextField sumTotal = (PDTextField) acroForm.getField("sumTotal");
        sumTotal.setValue(Integer.toString(scoreSheet.getSumRow().getTotal()));

        //

        PDTextField totalX = (PDTextField) acroForm.getField("totalX");
        totalX.setValue(Integer.toString(scoreSheet.getTotalRow().getX()));

        PDTextField totalTen = (PDTextField) acroForm.getField("totalTen");
        totalTen.setValue(Integer.toString(scoreSheet.getTotalRow().getTen()));

        PDTextField toalEight = (PDTextField) acroForm.getField("totalEight");
        toalEight.setValue(Integer.toString(scoreSheet.getTotalRow().getEight()));

        PDTextField totalFive = (PDTextField) acroForm.getField("totalFive");
        totalFive.setValue(Integer.toString(scoreSheet.getTotalRow().getFive()));

        PDTextField penalty = (PDTextField) acroForm.getField("penalty");
        penalty.setValue(Integer.toString(scoreSheet.getTargetOne().getMisses() + scoreSheet.getTargetTwo().getMisses()));

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
