package Program;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

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

	// 8042221111
	static String phoneNumber;

	static DefaultTableModel model;

	static Document document;
	static PdfFont regular;
	static PdfFont bold;

	public static void main(String[] args) throws DocumentException, IOException {

		String dest = "Results\\NumberResults.pdf";

		PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
		regular = PdfFontFactory.createFont(StandardFonts.COURIER);
		bold = PdfFontFactory.createFont(StandardFonts.COURIER_BOLD);
		document = new Document(pdf);

		JFrame frame = new JFrame("Caller Fetcher");
		frame.setSize(400, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());

		JLabel label = new JLabel("Enter Phone Number:");
		JTextField numberInput = new JTextField("", 10);
		label.setLabelFor(numberInput);

		model = new DefaultTableModel();
		model.addColumn("<html><font size=\"4\"><b>Caller:</b></font></html>");
		model.addColumn("<html><font size=\"4\"><b>Location:</b></font></html>");

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {


				phoneNumber = numberInput.getText();

				Selenium.startIncognitoSession();

				try {
					telephoneDirectories(phoneNumber);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					fastPeopleSearch(phoneNumber);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block

				}

				JTable jtable = new JTable(model);

				jtable.setBounds(30, 40, 200, 300);
				JScrollPane sp = new JScrollPane(jtable);

				label.setVisible(false);
				numberInput.setVisible(false);
				searchButton.setVisible(false);
				frame.add(sp);

				frame.pack();
				frame.setVisible(true);

				document.close();
				Selenium.endSession();

			}
		});

		frame.getContentPane().add(label);
		frame.getContentPane().add(numberInput);
		frame.getContentPane().add(searchButton);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

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

			// JTable ********************************************

			// String htmlTest = "<html><font size=\"3\">"+td.getCaller1()+"</font></html>";
			// model.addRow(new Object[] {htmlTest,td.getCaller1Location()});
			model.addRow(new Object[] { td.getCaller1(), td.getCaller1Location() });

			// pdf ********************************************

			Table table = new Table(UnitValue.createPercentArray(2));
			table.setWidth(UnitValue.createPercentValue(70)).setMarginBottom(10);

			table.addHeaderCell(
					new Cell().setFont(bold).add(new Paragraph("Caller:").setTextAlignment(TextAlignment.CENTER)));
			table.addHeaderCell(
					new Cell().setFont(bold).add(new Paragraph("Location:").setTextAlignment(TextAlignment.CENTER)));

			table.addCell(new Cell().setFont(regular)
					.add(new Paragraph(td.getCaller1()).setTextAlignment(TextAlignment.CENTER)));
			table.addCell(new Cell().setFont(regular)
					.add(new Paragraph(td.getCaller1Location()).setTextAlignment(TextAlignment.CENTER)));

//		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 2:")));
//		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller2())));
//		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 2 Location:")));
//		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller2Location())));
//
//		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 3:")));
//		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller3())));
//		table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller 3 Location:")));
//		table.addCell(new Cell().setFont(regular).add(new Paragraph(td.getCaller3Location())));

			document.add(table);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("telephoneDirectories Failed");

		}

	}

	public static void fastPeopleSearch(String number) throws InterruptedException {

		try {

			FastPeopleSearch fps = new FastPeopleSearch();

			Selenium.goTo("https://www.fastpeoplesearch.com/reverse-phone-lookup");

			Selenium.sendKeys(fps_phoneNumber_text, number);

			Selenium.click(fps_submit_button);

			fps.setCaller1(Selenium.getText(fps_caller));
			fps.setCaller1Location(Selenium.getText(fps_caller_location));

			// JTable ********************************************

			model.addRow(new Object[] { fps.getCaller1(), fps.getCaller1Location() });

			// pdf ********************************************

			Table table = new Table(UnitValue.createPercentArray(2));
			table.setWidth(UnitValue.createPercentValue(70)).setMarginBottom(10);

			table.addHeaderCell(new Cell().setFont(bold)
					.add(new Paragraph("Lookup Source 2:").setTextAlignment(TextAlignment.CENTER)));
			table.addHeaderCell(
					new Cell().setFont(bold).add(new Paragraph("Information").setTextAlignment(TextAlignment.CENTER)));

			table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller:")));
			table.addCell(new Cell().setFont(regular).add(new Paragraph(fps.getCaller1())));
			table.addCell(new Cell().setFont(bold).add(new Paragraph("Caller Location:")));
			table.addCell(new Cell().setFont(regular).add(new Paragraph(fps.getCaller1Location())));

			document.add(table);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("fastPeopleSearch Failed");

		}

	}

}
