package nth.msofficefile2clipboard;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.POIOLE2TextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.xmlbeans.XmlException;

public class MSOfficeFile2Clipboard {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (containsCommand(args, '?')) {
			showHelp();
			System.exit(0);
		}
		File powerPointFile = null;
		try {
			powerPointFile = new File(getFilePath(args));
		} catch (Exception e) {
		}
		if (powerPointFile == null || !powerPointFile.exists()) {
			showHelp();
		} else {
			String text = null;
			try {
				text = getTextFromMSOfficeFile(powerPointFile);
			} catch (Exception e) {
				System.out.println("Failed to read text from file!");
				System.exit(0);
			}
			if (text == null || text.trim().length() == 0) {
				System.out.println("Could not find any text!");
				System.exit(0);
			}
			putPlainTextOnClipBoard(text);
			System.out.println("Text of document is placed on clipboard.");
		}
		System.exit(0);
	}

	private static void putPlainTextOnClipBoard(String plainText) {
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection data = new StringSelection(plainText);
		clipboard.setContents(data, data);
	}

	private static String getTextFromMSOfficeFile(File msOfficeFile) throws IOException, InvalidFormatException, OpenXML4JException, XmlException {
		FileInputStream fis = new FileInputStream(msOfficeFile);
		POIFSFileSystem fileSystem = new POIFSFileSystem(fis);
		// Firstly, get an extractor for the Workbook
		POIOLE2TextExtractor oleTextExtractor = ExtractorFactory.createExtractor(fileSystem);
		return oleTextExtractor.getText();
	}

	private static String getFilePath(String[] args) {
		for (String arg : args) {
			if (arg.length() > 0 && !(arg.startsWith("-") || arg.startsWith("/"))) {
				return arg;
			}
		}
		return null;
	}

	private static void showHelp() {
		System.out.println("This application reads a given MicroSoft Office document");
		System.out.println("It will get the simplified texts of this document and put them on the clipboard");
		System.out.println();
		System.out.println("Usage: MSOfficeFile2Clipboard \"msOfficefile\" [/?] ");
		System.out.println();
		System.out.println(" \"msOfficefile\" MicroSoft Office file to read");
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
