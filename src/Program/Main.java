package Program;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

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

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.text.Element;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.io.font.constants.StandardFonts;

public class Main extends Selenium implements Elements {

	// add area code detector
	// add carrier detector
	//

	// 8007597243
	// 8042221111
	// 804-222-1111

	static String phoneNumber;

	static DefaultTableModel model;

	static PdfDocument pdf;
	static Document document;
	static PdfFont regular;
	static PdfFont bold;

	static PdfFormXObject template;
	static Canvas templateCanvas;

	public static void main(String[] args) throws DocumentException, IOException {

		String dest = "Results\\NumberResults.pdf";

		pdf = new PdfDocument(new PdfWriter(dest));
		regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
		bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
		document = new Document(pdf);
		// topOfPage
		template = new PdfFormXObject(new Rectangle(0, 0, 523, 50));
		templateCanvas = new Canvas(template, pdf);
		document.add(new Image(template));

		JFrame frame = new JFrame("Caller Fetcher");
		frame.setSize(400, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new FlowLayout());

		JLabel label = new JLabel("Enter Phone Number:");
		JTextField numberInput = new JTextField("", 10);
		label.setLabelFor(numberInput);

		model = new DefaultTableModel();
		model.addColumn("<html><font size=\"4\"><b>Lookup Source:</b></font></html>");
		model.addColumn("<html><font size=\"4\"><b>Caller:</b></font></html>");
		model.addColumn("<html><font size=\"4\"><b>Location:</b></font></html>");

		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				phoneNumber = numberInput.getText();

				Selenium.startIncognitoSession();

				try {
					shouldIAnswer(phoneNumber);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block

				}

				try {
					fastPeopleSearch(phoneNumber);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block

				}

				/**
				 * JTable jtable = new JTable(model);
				 * 
				 * jtable.setBounds(30, 40, 200, 300); JScrollPane sp = new JScrollPane(jtable);
				 * 
				 * label.setVisible(false); numberInput.setVisible(false);
				 * searchButton.setVisible(false); frame.add(sp);
				 **/

				/**
				 * JLabel linkToResultsLabel = new JLabel("Click here to view a <b>PDF</b> of
				 * the search results"); JScrollPane linkToResultsPanel = new
				 * JScrollPane(linkToResultsLabel);
				 * linkToResultsLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				 * linkToResultsLabel.addMouseListener(new MouseAdapter() {
				 * 
				 * @Override public void mouseClicked(MouseEvent e) { try {
				 *           Desktop.getDesktop().open( new File("Results\\NumberResults.pdf"));
				 *           } catch (IOException e1) {
				 * 
				 *           e1.printStackTrace(); } } }); frame.add(linkToResultsPanel);
				 **/

				try {
					Desktop.getDesktop().open(new File("Results\\NumberResults.pdf"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				frame.pack();
				frame.setVisible(true);

				templateCanvas.close();
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

	public static void topOfPage() throws InterruptedException {

		try {

			Table topTable = new Table(UnitValue.createPercentArray(2));
			topTable.setWidth(UnitValue.createPercentValue(70)).setMarginBottom(10);
			topTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

			topTable.addHeaderCell(
					new Cell().setFont(bold).add(new Paragraph("Carrier:").setTextAlignment(TextAlignment.CENTER)));
			topTable.addCell(new Cell().setFont(regular)
					.add(new Paragraph("AT&TMobizon").setTextAlignment(TextAlignment.CENTER)));

			topTable.addHeaderCell(
					new Cell().setFont(bold).add(new Paragraph("Area Code: ").setTextAlignment(TextAlignment.CENTER)));
			topTable.addCell(new Cell().setFont(regular)
					.add(new Paragraph("Dummystate").setTextAlignment(TextAlignment.CENTER)));

			templateCanvas.add(topTable);

		} catch (Exception e) {
			System.out.println("topOfPage Failed");

		}

	}

	public static void resultsToPDF(String source, String caller, String location) throws InterruptedException {

		try {

			Paragraph lookupSource = new Paragraph(source);
			lookupSource.setTextAlignment(TextAlignment.CENTER);
			document.add(lookupSource);

			Table table = new Table(UnitValue.createPercentArray(2));
			table.setWidth(UnitValue.createPercentValue(70)).setMarginBottom(10);
			table.setHorizontalAlignment(HorizontalAlignment.CENTER);

			table.addHeaderCell(
					new Cell().setFont(bold).add(new Paragraph("Caller:").setTextAlignment(TextAlignment.CENTER)));
			table.addHeaderCell(
					new Cell().setFont(bold).add(new Paragraph("Location:").setTextAlignment(TextAlignment.CENTER)));

			table.addCell(
					new Cell().setFont(regular).add(new Paragraph(caller).setTextAlignment(TextAlignment.CENTER)));
			table.addCell(
					new Cell().setFont(regular).add(new Paragraph(location).setTextAlignment(TextAlignment.CENTER)));

			document.add(table);

		} catch (Exception e) {
			System.out.println("resultsToPDF Failed");

		}

	}

	public static String numberProcessor(String number, String source) throws InterruptedException {

		char[] phoneArray_Initial = new char[number.length()];
		String phoneNumber = "";

		for (int i = 0; i < number.length(); i++) {
			phoneArray_Initial[i] = number.charAt(i);
		}

		if (phoneArray_Initial[0] == '+' && phoneArray_Initial[1] == '1') {
			phoneArray_Initial = Arrays.copyOfRange(phoneArray_Initial, 2, number.length());
			System.out.println("phoneArray_Initial: " + new String(phoneArray_Initial));
		}

		String phoneArray_String = new String(phoneArray_Initial);

		if (source.trim().equals("Fast People Search") && !phoneArray_String.contains("-")) {

			StringBuilder sb = new StringBuilder(phoneArray_String);
			sb.insert(3, '-');
			sb.insert(7, '-');

			phoneNumber = sb.toString();

			System.out.println("phoneNumber" + sb.toString());

		} else {

			phoneNumber = phoneArray_String;

		}

		return phoneNumber;

	}

	public static void shouldIAnswer(String number) throws InterruptedException {

		String source = "Should I Answer?";
		String sourceLink = "https://www.shouldianswer.com/phone-number/";
		String shouldIAnswerRatingPath = sia_result;

		String reNumber = numberProcessor(number, source);
		Data data = new Data();
		try {

			Selenium.goTo(sourceLink + reNumber);

			if (Selenium.isDisplayed(shouldIAnswerRatingPath)) {

				String rating = Selenium.getText(shouldIAnswerRatingPath);
				data.setShouldIAnswerRating(rating);
				System.out.println("Rating:" + Selenium.getText(shouldIAnswerRatingPath));

			} else {
				System.out.println(source + ": Could not locate Should I Answer? Rating");
			}

			Text coloredText = null;

			if (data.getShouldIAnswerRating().toLowerCase().trim().contains("positive")) {
				coloredText = new Text(data.getShouldIAnswerRating()).setFontColor(ColorConstants.GREEN)
						.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
			} else if (data.getShouldIAnswerRating().toLowerCase().trim().contains("negative")) {
				coloredText = new Text(data.getShouldIAnswerRating()).setFontColor(ColorConstants.RED)
						.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA));
			}

			Table ratingTable = new Table(UnitValue.createPercentArray(2));
			ratingTable.setWidth(UnitValue.createPercentValue(70)).setMarginBottom(10);
			ratingTable.setHorizontalAlignment(HorizontalAlignment.CENTER);

			ratingTable.addHeaderCell(new Cell().add(new Paragraph("Should I Answer? Rating:")
					.setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
					.setTextAlignment(TextAlignment.CENTER)));
			ratingTable
					.addHeaderCell(new Cell().add(new Paragraph(coloredText).setTextAlignment(TextAlignment.CENTER)));

			templateCanvas.add(ratingTable);

		} catch (Exception e) {
			System.out.println(source + " Failed");

		}

	}

	public static void fastPeopleSearch(String number) throws InterruptedException {

		String source = "Fast People Search";
		String sourceLink = "https://www.fastpeoplesearch.com/";
		String callerPath = fps_caller;
		String callerLocationPath = fps_caller_location;

		String reNumber = numberProcessor(number, source);
		Data data = new Data();
		try {

			Selenium.goTo(sourceLink + reNumber);

			if (Selenium.isDisplayed(callerPath)) {
				data.setCaller(Selenium.getText(callerPath));
			} else {
				System.out.println(source + ": Could not locate Caller Path");
			}

			if (Selenium.isDisplayed(callerLocationPath)) {
				data.setCallerLocation(Selenium.getText(callerLocationPath));
			} else {

				System.out.println(source + ": Could not locate Caller Location Path");
			}

			resultsToPDF(source, data.getCaller(), data.getCallerLocation());

		} catch (InterruptedException e) {
			System.out.println(source + " Failed");

		}

	}

}
