package com.envisageconsulting.primefaces.scoredaddy;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.junit.Ignore;

public class fillPDF {

	public static void main(String[] args) throws IOException {
		String formTemplate = "src/main/resources/GSSF_Indoor2.pdf";

		try (PDDocument pdfDocument = PDDocument.load(new File(formTemplate))) {
			// get the document catalog
			PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();

			// as there might not be an AcroForm entry a null check is necessary
			if (acroForm != null) {
				// Retrieve an individual field and set its value.
				PDTextField name = (PDTextField) acroForm.getField("name");
				name.setValue("Heather Dang");
				
				PDTextField date = (PDTextField) acroForm.getField("date");
				date.setValue("08-10-2017");
				
				PDTextField model = (PDTextField) acroForm.getField("model");
				model.setValue("G34");
				
				PDTextField entry = (PDTextField) acroForm.getField("entry");
				entry.setValue("123456");
				
				//
				
				PDField checkStock = (PDField) acroForm.getField("checkStock");
				((PDCheckBox) checkStock).check();
				
				PDField checkUnlimited = (PDField) acroForm.getField("checkUnlimited");
				((PDCheckBox) checkUnlimited).check();
				
				PDField checkPocket = (PDField) acroForm.getField("checkPocket");
				((PDCheckBox) checkPocket).check();
				
				PDField checkWoman = (PDField) acroForm.getField("checkWoman");
				((PDCheckBox) checkWoman).check();
				
				PDField checkSenior = (PDField) acroForm.getField("checkSenior");
				((PDCheckBox) checkSenior).check();
				
				PDField checkJunior = (PDField) acroForm.getField("checkJunior");
				((PDCheckBox) checkJunior).check();

				PDField checkRimfire = (PDField) acroForm.getField("checkRimfire");
				((PDCheckBox) checkRimfire).check();
				
				//
				
				PDTextField targetOneX = (PDTextField) acroForm.getField("targetOneX");
				targetOneX.setValue("1");
				
				PDTextField targetOneTen = (PDTextField) acroForm.getField("targetOneTen");
				targetOneTen.setValue("2");
				
				PDTextField targetOneEight = (PDTextField) acroForm.getField("targetOneEight");
				targetOneEight.setValue("3");
				
				PDTextField targetOneFive = (PDTextField) acroForm.getField("targetOneFive");
				targetOneFive.setValue("4");
				
				PDTextField targetOneMisses = (PDTextField) acroForm.getField("targetOneMisses");
				targetOneMisses.setValue("5");
				
				PDTextField targetOneTotal = (PDTextField) acroForm.getField("targetOneTotal");
				targetOneTotal.setValue("6");
				
				//
				
				PDTextField targetTwoX = (PDTextField) acroForm.getField("targetTwoX");
				targetTwoX.setValue("7");
				
				PDTextField targetTwoTen = (PDTextField) acroForm.getField("targetTwoTen");
				targetTwoTen.setValue("8");
				
				PDTextField targetTwoEight = (PDTextField) acroForm.getField("targetTwoEight");
				targetTwoEight.setValue("9");
				
				PDTextField targetTwoFive = (PDTextField) acroForm.getField("targetTwoFive");
				targetTwoFive.setValue("10");
				
				PDTextField targetTwoMisses = (PDTextField) acroForm.getField("targetTwoMisses");
				targetTwoMisses.setValue("11");
				
				PDTextField targetTwoTotal = (PDTextField) acroForm.getField("targetTwoTotal");
				targetTwoTotal.setValue("12");
				
				//
				
				PDTextField sumX = (PDTextField) acroForm.getField("sumX");
				sumX.setValue("13");
				
				PDTextField sumTen = (PDTextField) acroForm.getField("sumTen");
				sumTen.setValue("14");
				
				PDTextField sumEight = (PDTextField) acroForm.getField("sumEight");
				sumEight.setValue("15");
				
				PDTextField sumFive = (PDTextField) acroForm.getField("sumFive");
				sumFive.setValue("16");
				
				PDTextField sumMisses = (PDTextField) acroForm.getField("sumMisses");
				sumMisses.setValue("17");
				
				PDTextField sumTotal = (PDTextField) acroForm.getField("sumTotal");
				sumTotal.setValue("18");
				
				//
				
				PDTextField totalX = (PDTextField) acroForm.getField("totalX");
				totalX.setValue("19");
				
				PDTextField totalTen = (PDTextField) acroForm.getField("totalTen");
				totalTen.setValue("20");
				
				PDTextField toalEight = (PDTextField) acroForm.getField("totalEight");
				toalEight.setValue("21");
				
				PDTextField totalFive = (PDTextField) acroForm.getField("totalFive");
				totalFive.setValue("22");
				
				PDTextField penalty = (PDTextField) acroForm.getField("penalty");
				penalty.setValue("23");
				
				PDTextField finalScore = (PDTextField) acroForm.getField("finalScore");
				finalScore.setValue("24");
				
				//
				
				PDTextField rangeOfficerInitials = (PDTextField) acroForm.getField("rangeOfficerInitials");
				rangeOfficerInitials.setValue("BD");
				
				PDTextField competitorInitials = (PDTextField) acroForm.getField("competitorInitials");
				competitorInitials.setValue("VD");

			}

			// Save and close the filled out form.
			pdfDocument.save("target/FillFormField.pdf");
		}
	}

}
