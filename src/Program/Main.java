package Program;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.io.font.constants.StandardFonts;

public class Main extends Selenium implements Elements {

	
	// add area code detector
	// add carrier detector
	//
	static Document document;
	static PdfFont regular;
	static PdfFont bold;

	public static void main(String[] args) throws DocumentException, IOException {

		Selenium.startIncognitoSession();


		String dest = "Results\\NumberResults.pdf";

		PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
		regular = PdfFontFactory.createFont(StandardFonts.COURIER);
		bold = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
		document = new Document(pdf);

		String phoneNumber = "";
		
		try {
			telephoneDirectories(phoneNumber);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block

		}

		document.close();
	}



	public static void telephoneDirectories(String number) throws InterruptedException {

		// process number method for telephoneDirectories format
	
		try {

		TelephoneDirectories td = new TelephoneDirectories();

		Selenium.goTo("https://www.telephonedirectories.us/");

		Selenium.click(td_search_button);

		Selenium.sendKeys(td_phoneNumber_text, number);

		Selenium.click(td_submit_button);

		Thread.sleep(3000);

		String Caller1 = Selenium.getText(td_caller_1);
		String Caller1Location = Selenium.getText(td_caller_1_location);
		String Caller2 = Selenium.getText(td_caller_2);
		String Caller2Location = Selenium.getText(td_caller_2_location);
		String Caller3 = Selenium.getText(td_caller_3);
		String Caller3Location = Selenium.getText(td_caller_3_location);

		td.setCaller1(Caller1);
		td.setCaller1Location(Caller1Location);
		td.setCaller2(Caller2);
		td.setCaller2Location(Caller2Location);
		td.setCaller3(Caller3);
		td.setCaller3Location(Caller3Location);
		
		Table table = new Table(UnitValue.createPercentArray(2));
		table.setWidth(UnitValue.createPercentValue(70)).setMarginBottom(10);

		table.addHeaderCell(new Cell().setFont(bold).add(new Paragraph("Lookup Source 1:").setTextAlignment(TextAlignment.CENTER)));
		table.addHeaderCell(new Cell().setFont(bold).add(new Paragraph("Information").setTextAlignment(TextAlignment.CENTER)));

		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 1:")));
		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller1())));
		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 1 Location:")));
		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller1Location())));

		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 2:")));
		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller2())));
		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 2 Location:")));
		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller2Location())));

		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 3:")));
		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller3())));
		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 3 Location:")));
		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller3Location())));

		document.add(table);
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("telephoneDirectories Failed");

		}


	}
	

	

}
