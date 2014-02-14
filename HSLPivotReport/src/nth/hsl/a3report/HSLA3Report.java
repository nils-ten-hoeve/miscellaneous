package nth.hsl.a3report;

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

import javax.management.RuntimeErrorException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class HSLA3Report {

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
				List<Product> products = readExcelFile(excelFile);
				createExcelSummaryDocument(excelFile.getParentFile(), products);
			} catch (Exception e) {
				System.out.println("Failed to create the report!");
				e.printStackTrace();
			}
		}
	}

	private static void createExcelSummaryDocument(File rootFolder, List<Product> products) {

		// create workbook and sheet
		HSSFWorkbook wb = new HSSFWorkbook();

		for (Product product : products) {
			createSheet(product, products.indexOf(product), wb);
		}

		// saveDoc
		try {
			String title = HSLA3Report.class.getSimpleName();
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

	private static void createSheet(Product product, int index, Workbook wb) {
		String sheetName = Integer.toString(index);
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
		printSetup.setHeaderMargin(0);
		printSetup.setFooterMargin(0);
		printSetup.setFitHeight(Short.MAX_VALUE);
		printSetup.setFitWidth((short) 1);
		// printSetup.setLandscape(true);
		// repeat header when printing
		wb.setRepeatingRowsAndColumns(0, 0, 0, 0, 1);

		// // set footer
		// Footer footer = sheet.getFooter();
		// // add a fixed export date and time (not a dynamic HeaderFooter.date() )
		// SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Date EXPORT_DATE_TIME = new Date();
		// footer.setLeft(dateFormat.format(EXPORT_DATE_TIME));
		// footer.setRight("Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages());

		// hide remaining columns
		int NR_OF_COLUMNS = 4;
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
		StringBuffer text = new StringBuffer();

		text.append(product.getProductGroup());
		text.append(" - ");
		text.append(product.getProduct());
		text.append("   (");
		text.append(index);
		text.append(")\n\n");
		boolean needsSeperator = false;
		for (Rank rank : product.getRanks()) {
			if (needsSeperator) {
				text.append(",  ");
			}
			text.append(rank.getPerson());
			text.append("=");
			text.append(rank.getPriority());
			needsSeperator = true;
		}

		cell.setCellValue(text.toString());
		cell.setCellStyle(HEADER_COLUMNS_STYLE);
		row.setHeight((short) 800);

		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, NR_OF_COLUMNS));

		// Freeze header rows
		sheet.createFreezePane(0, rowNr, 0, rowNr);

		row = sheet.createRow(rowNr++);
		sheet.addMergedRegion(new CellRangeAddress(rowNr - 1, rowNr - 1, 0, NR_OF_COLUMNS));
		row.setHeight((short) 500);

		row = sheet.createRow(rowNr++);
		columnNr = 0;
		cell = row.createCell(columnNr++);
		String columnHeaderText = "Marked by";
		cell.setCellValue(columnHeaderText);
		cell.setCellStyle(HEADER_COLUMNS_STYLE);

		cell = row.createCell(columnNr++);
		columnHeaderText = "Remark type";
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

		// add rows
		for (Remark remark : product.getRemarks()) {
			row = sheet.createRow(rowNr++);
			columnNr = 0;
			cell = row.createCell(columnNr++);
			text = new StringBuffer();
			for (String person : remark.getVotedBy()) {
				if (text.length() > 0) {
					text.append(", ");
				}
				text.append(person);
			}
			cell.setCellValue(text.toString());

			cell = row.createCell(columnNr++);
			cell.setCellValue(remark.getRemarkType());

			cell = row.createCell(columnNr++);
			cell.setCellValue(remark.getRemark());

			cell = row.createCell(columnNr++);
			cell.setCellValue(remark.getSource());

		}

		row = sheet.createRow(rowNr++);
		sheet.addMergedRegion(new CellRangeAddress(rowNr - 1, rowNr - 1, 0, NR_OF_COLUMNS));
		row.setHeight((short) 500);

		row = sheet.createRow(rowNr++);
		columnNr = 0;
		cell = row.createCell(columnNr++);
		cell.setCellValue("13500 BPH/ 15000 BPH");
		cell.setCellStyle(HEADER_COLUMNS_STYLE);
		sheet.addMergedRegion(new CellRangeAddress(rowNr - 1, rowNr - 1, 0, 1));

		cell = row.createCell(2);
		cell.setCellValue("TODO");
		cell.setCellStyle(HEADER_COLUMNS_STYLE);
		sheet.addMergedRegion(new CellRangeAddress(rowNr - 1, rowNr - 1, 2, 3));

		// row = sheet.createRow(rowNr++);
		// sheet.addMergedRegion(new CellRangeAddress(rowNr - 1, rowNr - 1, 0, 1));
		// sheet.addMergedRegion(new CellRangeAddress(rowNr - 1, rowNr - 1, 2, 3));
		// row.setHeight((short) 5000);

	}

	// private static void mergeCells(Sheet sheet, List<Quote> quotes, int colNr) {
	// Quote previousQuote = null;
	// int rowNr = -1;
	// int rowStart = 0;
	// for (Quote quote : quotes) {
	// rowNr = quotes.indexOf(quote);
	// boolean columnValueChanged = false;
	// // if (quote.getProductGroup().equals("chilling")) {
	// // System.out.println();
	// // }
	// if (previousQuote != null) {
	// switch (colNr) {
	// case 0:
	// case 1:
	// columnValueChanged = !quote.getProduct().equals(previousQuote.getProduct()) && !quote.getProduct().equals("");
	// break;
	// case 2:
	// columnValueChanged = (!quote.getProduct().equals(previousQuote.getProduct()) && !quote.getProduct().equals("")) || (!quote.getRemarkType().equals(previousQuote.getRemarkType()) && !quote.getRemarkType().equals(""));
	// break;
	// default:
	// break;
	// }
	// // }
	// if (columnValueChanged) {
	// System.out.println(quote.getProductGroup() + ":" + rowStart + ":" + (rowNr - 1));
	// sheet.addMergedRegion(new CellRangeAddress(rowStart + 1, rowNr, colNr, colNr));
	// rowStart = rowNr;
	// }
	// }
	// previousQuote = quote;
	// }
	// System.out.println(quotes.get(0).getProductGroup() + ":" + rowStart + ":" + rowNr);
	// sheet.addMergedRegion(new CellRangeAddress(rowStart + 1, rowNr + 1, colNr, colNr));
	//
	// }

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

	private static List<Product> readExcelFile(File excelFile) throws IOException {

		List<Product> products = new ArrayList<Product>();
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
			workBook.getNumberOfSheets();
			for (int sheetNr = 0; sheetNr < workBook.getNumberOfSheets(); sheetNr++) {
				HSSFSheet sheet = workBook.getSheetAt(sheetNr);
				products.addAll(readSheet(sheet));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return products;
	}

	private static List<Product> readSheet(HSSFSheet sheet) {
		List<Product> products = new ArrayList<Product>();

		int productColumn = getProductColumn(sheet);
		int remarkTypeColumn = productColumn + 1;
		int remarkColumn = getRemarkColumn(sheet);
		String productGroup = sheet.getSheetName();

		int regionCount = sheet.getNumMergedRegions();
		List<CellRangeAddress> regions = new ArrayList<CellRangeAddress>();
		for (int regionIndex = 0; regionIndex < regionCount; regionIndex++) {
			CellRangeAddress region = (sheet.getMergedRegion(regionIndex));
			regions.add(region);
		}

		for (int rowNr = 1; rowNr < sheet.getLastRowNum(); rowNr++) {
			CellRangeAddress region = findRegion(rowNr, regions);
			if (region != null) {
				if (region.getFirstRow() != rowNr) {// only for first row
					region = null;
				}
			} else {
				region = new CellRangeAddress(rowNr, rowNr, 0, 0);
			}
			if (region != null) {
				int firstRow = region.getFirstRow();

				String productName = sheet.getRow(firstRow).getCell(productColumn).getStringCellValue();
				List<Rank> ranks = readRanks(sheet, region, productColumn);
				List<Remark> remarks = readRemarks(sheet, region, remarkTypeColumn, remarkColumn);

				if (ranks.size() > 0 && remarks.size() > 0) {
					Product product = new Product(productGroup, productName, remarks, ranks);
					products.add(product);
				}

			}
		}
		return products;
	}

	private static CellRangeAddress findRegion(int rowNr, List<CellRangeAddress> regions) {
		for (CellRangeAddress region : regions) {
			if (region.getFirstColumn() == 0 && region.getFirstRow() <= rowNr && region.getLastRow() >= rowNr) {
				return region;
			}
		}
		return null;
	}

	private static List<Remark> readRemarks(HSSFSheet sheet, CellRangeAddress region, int remarkTypeColumn, int remarkColumn) {
		List<Remark> remarks = new ArrayList<Remark>();
		for (int rowNr = region.getFirstRow(); rowNr <= region.getLastRow(); rowNr++) {
			HSSFRow row = sheet.getRow(rowNr);
			List<String> votedBy = new ArrayList<String>();
			for (int colNr = remarkTypeColumn + 1; colNr < remarkColumn; colNr++) {
				String cellValue = row.getCell(colNr).getStringCellValue().trim();
				if (cellValue != null && cellValue.length() > 0) {
					String person = sheet.getRow(0).getCell(colNr).getStringCellValue();
					votedBy.add(person);
				}
			}
			if (votedBy.size() > 0) {
				String remarkText = row.getCell(remarkColumn).getStringCellValue();
				String remarkType = row.getCell(remarkTypeColumn).getStringCellValue();
				String source = row.getCell(remarkColumn + 1).getStringCellValue();
				Remark remark = new Remark(remarkText, remarkType, source, votedBy);
				remarks.add(remark);
			}

		}
		return remarks;
	}

	private static List<Rank> readRanks(HSSFSheet sheet, CellRangeAddress region, int productColumn) {
		List<Rank> ranks = new ArrayList<Rank>();
		int rowNr = region.getFirstRow();
		HSSFRow row = sheet.getRow(rowNr);
		for (int colNr = 0; colNr <= productColumn; colNr++) {
			HSSFCell cell = row.getCell(colNr);
			int cellType = cell.getCellType();
			if (HSSFCell.CELL_TYPE_NUMERIC == cellType) {
				double priority = cell.getNumericCellValue();
				String person = sheet.getRow(0).getCell(colNr).getStringCellValue();
				Rank rank = new Rank(person, (int) priority);
				ranks.add(rank);
			}
		}
		return ranks;
	}

	private static int getProductColumn(HSSFSheet sheet) {
		HSSFRow row = sheet.getRow(0);
		for (int col = 0; col <= row.getLastCellNum(); col++) {
			String value = row.getCell(col).getStringCellValue().trim().toLowerCase();
			if ("product".equals(value.toLowerCase())) {
				return col;
			}
		}
		throw new RuntimeException("Could not find product column for sheet " + sheet.getSheetName());
	}

	private static int getRemarkColumn(HSSFSheet sheet) {
		HSSFRow row = sheet.getRow(0);
		for (int col = 0; col < row.getLastCellNum(); col++) {
			String value = row.getCell(col).getStringCellValue().trim().toLowerCase();
			if ("remark".equals(value)) {
				return col;
			}
		}
		throw new RuntimeException("Could not find remark column for sheet" + sheet.getSheetName());
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
