package nth.wordhighlightsummary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.CharacterProperties;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.ParagraphProperties;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class WordHighLightSummary {

	/**
	 * @param args
	 *            see {@linkplain #showHelp}
	 */
	public static void main(String[] args) {
		if (containsCommand(args, '?')) {
			showHelp();
		}
		File folder = null;
		try {
			folder = new File(getFolder(args));
		} catch (Exception e) {
		}
		if (folder == null || !folder.exists()) {
			showHelp();
		} else {
			try {
				List<Quote> summary = createSummaryOfDirectory(folder, folder);
				createExcelSummaryDocument(folder, summary);
			} catch (Exception e) {
				System.out.println("Failed to create the report!");
				e.printStackTrace();
			}
		}
	}

	private static void createExcelSummaryDocument(File rootFolder, List<Quote> summary) {

		// create workbook and sheet
		Workbook wb;
		wb = new HSSFWorkbook();
		String title = WordHighLightSummary.class.getSimpleName();
		Sheet sheet = wb.createSheet(title);

		// create styles
		CellStyle HEADER_TITLE_STYLE = createHeaderTitleStyle(wb);
		CellStyle HEADER_COLUMNS_STYLE = createHeaderColumnsStyle(wb);
		CellStyle CELL_STYLE = createCellStyle(wb);

		// print and page setup
		sheet.setPrintGridlines(true);
		sheet.setDisplayGridlines(true);
		sheet.setAutobreaks(true);
		sheet.setHorizontallyCenter(true);
		// set content size when printing
		PrintSetup printSetup = sheet.getPrintSetup();
		sheet.setFitToPage(true);
		printSetup.setFitHeight(Short.MAX_VALUE);
		printSetup.setFitWidth((short) 1);
		// repeat header when printing
		wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 1);

		// set footer
		Footer footer = sheet.getFooter();
		// add a fixed export date and time (not a dynamic HeaderFooter.date() )
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date EXPORT_DATE_TIME = new Date();
		footer.setLeft(dateFormat.format(EXPORT_DATE_TIME));
		footer.setRight("Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages());

		// hide remaining columns
		int NR_OF_COLUMNS = 2;
		for (int c = NR_OF_COLUMNS; c < 256; c++) {
			sheet.setColumnHidden(c, true);
		}

		sheet.setDefaultColumnStyle(0, CELL_STYLE);
		sheet.setDefaultColumnStyle(1, CELL_STYLE);

		// header title
		int rowNr = 0;
		Row row = sheet.createRow(rowNr++);
		row.setHeightInPoints(25);
		Cell titleCell = row.createCell(0);
		titleCell.setCellValue(title);
		titleCell.setCellStyle(HEADER_TITLE_STYLE);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, NR_OF_COLUMNS));

		// header columns
		row = sheet.createRow(rowNr++);
		int columnNr = 0;
		Cell cell;
		cell = row.createCell(columnNr++);
		String columnHeaderText = "Source";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		cell = row.createCell(columnNr++);
		columnHeaderText = "Qoute";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		// auto filter
		sheet.setAutoFilter(new CellRangeAddress(1, 1, 0, NR_OF_COLUMNS));

		// Freeze header rows
		sheet.createFreezePane(0, rowNr, 0, rowNr);

		// add rows
		for (Quote highLight : summary) {
			String text=highLight.getText();
			if (text.length() > 0) {
				row = sheet.createRow(rowNr++);
				columnNr = 0;
				cell = row.createCell(columnNr++);
				cell.setCellValue(highLight.getSource());

				cell = row.createCell(columnNr++);
				cell.setCellValue(text);
			}

		}

		// set column size
		sheet.autoSizeColumn(0);

		// saveDoc
		try {
			StringBuffer fileName = new StringBuffer(rootFolder.getAbsolutePath());
			fileName.append("/");
			fileName.append(title);
			fileName.append(".xls");
			File file = new File(fileName.toString());
			file.delete();
			FileOutputStream stream = new FileOutputStream(file);
			wb.write(stream);
			System.out.println("Created: " + file.getAbsolutePath());
		} catch (Exception e) {
		}

	}

	private static CellStyle createDateStyle(Workbook wb) {
		CreationHelper createHelper = wb.getCreationHelper();
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
		return cellStyle;
	}

	private static CellStyle createHeaderTitleStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeight((short) (font.getFontHeight() * 1.5));
		style.setFont(font);
		return style;
	}

	private static CellStyle createCellStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_TOP);
		// style.setWrapText(true);
		return style;
	}

	private static CellStyle createHeaderColumnsStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		return style;
	}

	private static List<Quote> createSummaryOfDirectory(File rootFolder, File location) throws IOException {
		List<Quote> summary = new ArrayList<Quote>();
		for (File file : location.listFiles()) {
			if (file.isDirectory()) {
				summary.addAll(createSummaryOfDirectory(rootFolder, file));
				// recursive call for sub directories
			} else {
				summary.addAll(createSummaryOfWordDocument(rootFolder, file));
			}
		}
		return summary;
	}

	private static List<Quote> createSummaryOfWordDocument(File rootFolder, File file) throws IOException {
		List<Quote> summary = new ArrayList<Quote>();
		try {

			String source = file.getAbsolutePath().replace(rootFolder.getAbsolutePath(), "");

			InputStream stream = new FileInputStream(file);

			HWPFDocument document = new HWPFDocument(stream);

			Range range = document.getRange();

			Quote quote = null;
			int numCharacterRuns = range.numCharacterRuns();
			for (int i = 0; i < numCharacterRuns; i++) {
				CharacterRun characterRun = range.getCharacterRun(i);
				if (characterRun.isHighlighted()) {
					// create new highlight when needed
					if (quote == null) {
						quote = new Quote(source);
						summary.add(quote);
					}
					String text = characterRun.text();
					// Fix the line ending
					// if (text.endsWith("\r")) {
					// text = text + "\n";
					// }
					quote.appendText(text);
				} else {
					// not highlighted, so start collecting the following quote
					quote = null;
				}
			}

		} catch (Exception e) {
		}

		return summary;
	}

	private static String getFolder(String[] args) {
		for (String arg : args) {
			if (arg.length() > 0 && !(arg.startsWith("-") || arg.startsWith("/"))) {
				return arg;
			}
		}
		return null;
	}

	private static void showHelp() {
		System.out.println("This application goes trough all word documents in a given folder (and its sub folders)");
		System.out.println("It will look for highlighted texts and put them al in one report (with reference to the source)");
		System.out.println();
		System.out.println("Usage: WordHighLightSummary \"folder\" [/?] ");
		System.out.println();
		System.out.println(" \"folder\" folder to search for word documents");
		System.out.println(" /? gets this help");
	}

	private static boolean containsCommand(String[] args, char command) {
		command = Character.toUpperCase(command);
		for (String arg : args) {
			String upperCaseArg = arg.toUpperCase().trim();
			if (upperCaseArg.length() == 2 && upperCaseArg.charAt(2) == command && (upperCaseArg.startsWith("-") || upperCaseArg.startsWith("/"))) {
				return true;
			}
		}
		return false;
	}
}
