package nth.hsl.pivot.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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

public class HSLPivotReport {

	/**
	 * @param args
	 *            see {@linkplain #showHelp}
	 */
	public static void main(String[] args) {
		if (containsCommand(args, '?')) {
			showHelp();
		}
		File excelFile = null;
		try {
			excelFile = new File(getExcelFile(args));
		} catch (Exception e) {
		}
		if (excelFile == null || !excelFile.exists()) {
			showHelp();
		} else {
			try {
				List<Quote> quotes = readExcelFile(excelFile);
				orderQuotes(quotes);
				createExcelSummaryDocument(excelFile.getParentFile(), quotes);
			} catch (Exception e) {
				System.out.println("Failed to create the report!");
				e.printStackTrace();
			}
		}
	}

	private static void orderQuotes(List<Quote> quotes) {
		Collections.sort(quotes, new Comparator<Quote>() {

			@Override
			public int compare(Quote quote1, Quote quote2) {
				int compare = quote1.getProductGroup().compareTo(quote2.getProductGroup());
				if (compare != 0) {
					return compare;
				}
				compare = quote1.getSequanceNr().compareTo(quote2.getSequanceNr());
				if (compare != 0) {
					return compare;
				}
				compare = quote1.getProduct().compareTo(quote2.getProduct());
				if (compare != 0) {
					return compare;
				}
				compare = quote1.getRemarkType().compareTo(quote2.getRemarkType());
				if (compare != 0) {
					return compare;
				}
				return 0;
			}

		});
	}

	private static void createExcelSummaryDocument(File rootFolder, List<Quote> quotes) {

		// create workbook and sheet
		Workbook wb;
		wb = new HSSFWorkbook();

		Quote previousQuote = null;
		List<Quote> workSheetQoutes = new ArrayList<Quote>();
		for (Quote quote : quotes) {
			if (previousQuote != null && !quote.getProductGroup().equals(previousQuote.getProductGroup())) {
				createSheet(workSheetQoutes, wb);
				workSheetQoutes.clear();
			}
			workSheetQoutes.add(quote);
			previousQuote = quote;
		}
		createSheet(workSheetQoutes, wb);
		workSheetQoutes.clear();

		// saveDoc
		try {
			String title = HSLPivotReport.class.getSimpleName();
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

	private static void createSheet(List<Quote> quotes, Workbook wb) {
		String sheetName = quotes.get(0).getProductGroup();
		Sheet sheet = wb.createSheet(sheetName);

		// create styles
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
		int NR_OF_COLUMNS = 6;
		for (int c = NR_OF_COLUMNS; c < 256; c++) {
			sheet.setColumnHidden(c, true);
		}

		sheet.setDefaultColumnStyle(0, CELL_STYLE);
		sheet.setDefaultColumnStyle(1, CELL_STYLE);

		// header columns
		int rowNr = 0;
		Row row = sheet.createRow(rowNr++);
		int columnNr = 0;
		Cell cell;

		cell = row.createCell(columnNr++);
		String columnHeaderText = "Product Priority";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		cell = row.createCell(columnNr++);
		columnHeaderText = "Product";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		cell = row.createCell(columnNr++);
		columnHeaderText = "Remark Type";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		cell = row.createCell(columnNr++);
		columnHeaderText = "Needs Fixing";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		cell = row.createCell(columnNr++);
		columnHeaderText = "Remark";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		cell = row.createCell(columnNr++);
		columnHeaderText = "Source";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);
		// auto filter
		sheet.setAutoFilter(new CellRangeAddress(1, 1, 0, NR_OF_COLUMNS));

		// Freeze header rows
		sheet.createFreezePane(0, rowNr, 0, rowNr);

		// add rows
		for (Quote quote : quotes) {
			row = sheet.createRow(rowNr++);
			columnNr = 0;
			cell = row.createCell(columnNr++);
			cell.setCellValue("");

			cell = row.createCell(columnNr++);
			cell.setCellValue(quote.getProduct());

			cell = row.createCell(columnNr++);
			cell.setCellValue(quote.getRemarkType());

			cell = row.createCell(columnNr++);
			cell.setCellValue("");

			cell = row.createCell(columnNr++);
			cell.setCellValue(quote.getRemark());

			cell = row.createCell(columnNr++);
			cell.setCellValue(quote.getSource());

		}

		mergeCells(sheet, quotes, 0);
		 mergeCells(sheet, quotes, 1);
		 mergeCells(sheet, quotes, 2);

		// set column size
		sheet.setColumnWidth(0, 500);
		sheet.setColumnWidth(1, 500);

	}

	private static void mergeCells(Sheet sheet, List<Quote> quotes, int colNr) {
		Quote previousQuote = null;
		int rowNr = -1;
		int rowStart = 0;
		for (Quote quote : quotes) {
			rowNr = quotes.indexOf(quote);
			boolean columnValueChanged = false;
			// if (quote.getProductGroup().equals("chilling")) {
			// System.out.println();
			// }
			if (previousQuote != null) {
								switch (colNr) {
				case 0:
				case 1:
					columnValueChanged = !quote.getProduct().equals(previousQuote.getProduct()) && !quote.getProduct().equals("");
					break;
				case 2:
					columnValueChanged = (!quote.getProduct().equals(previousQuote.getProduct()) && !quote.getProduct().equals("")) || (!quote.getRemarkType().equals(previousQuote.getRemarkType()) && !quote.getRemarkType().equals(""));
					break;
				default:
					break;
				}
				// }
				if (columnValueChanged) {
					System.out.println(quote.getProductGroup() + ":" + rowStart + ":" + (rowNr - 1));
					sheet.addMergedRegion(new CellRangeAddress(rowStart + 1, rowNr, colNr, colNr));
					rowStart = rowNr;
				}
			}
			previousQuote = quote;
		}
		System.out.println(quotes.get(0).getProductGroup() + ":" + rowStart + ":" + rowNr );
		sheet.addMergedRegion(new CellRangeAddress(rowStart + 1, rowNr+1, colNr, colNr));
		
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

	private static List<Quote> readExcelFile(File excelFile) throws IOException {

		List<Quote> quotes = new ArrayList<Quote>();
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(excelFile);
		} catch (FileNotFoundException e) {
			System.out.println("File not found in the specified path.");
			e.printStackTrace();
		}

		POIFSFileSystem fileSystem = null;
		int t = 0;
		try {
			fileSystem = new POIFSFileSystem(inputStream);

			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			HSSFSheet sheet = workBook.getSheetAt(0);
			Iterator<Row> rows = sheet.rowIterator();
			// skip the first row (header)
			rows.next();
			while (rows.hasNext()) {
				Row row = rows.next();

				// once get a row its time to iterate through cells.
				Iterator<Cell> cells = row.cellIterator();

				String productGroup = null;
				String product = null;
				String remark = null;
				String remarkType = null;
				Integer sequanceNr = null;
				String source = null;

				while (cells.hasNext()) {
					Cell cell = cells.next();
					t = cell.getCellType();

					switch (cell.getColumnIndex()) {
					case 0:
						source = cell.getStringCellValue();
						break;
					case 1:
						remark = cell.getStringCellValue();
						break;
					case 2:
						product = cell.getStringCellValue();
						break;
					case 3:
						remarkType = cell.getStringCellValue();
						break;
					case 4:
						productGroup = cell.getStringCellValue();
						break;
					case 5:
						sequanceNr = (int) cell.getNumericCellValue();
						break;
					default:
						break;
					}
				}
				Quote quote = new Quote(productGroup, product, remark, remarkType, sequanceNr, source);
				quotes.add(quote);

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("T=" + t);
		}
		return quotes;
	}

	private static String getExcelFile(String[] args) {
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
