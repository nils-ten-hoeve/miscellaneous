package nth.foldercompare;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderCompare {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (containsCommand(args, '?')) {
			showHelp();
		}
		File folder1 = null;
		File folder2 = null;
		try {
			folder1 = new File(getFolder1(args));
			folder2 = new File(getFolder2(args));
		} catch (Exception e) {
		}
		if (folder1 == null || !folder1.exists() || folder2 == null
				|| !folder2.exists()) {
			showHelp();
		} else {
			boolean includeHiddenFilesAndFolders = containsCommand(args, 'H');
			boolean includeFolders = containsCommand(args, 'F');

			compare(folder1, folder2, includeHiddenFilesAndFolders,
					includeFolders);
		}
	}

	private static void compare(File folder1, File folder2,
			boolean includeHiddenFilesAndFolders, boolean includeFolders) {

		List<File> filesFromFolder1 = getFilesFromFolder(folder1,
				includeHiddenFilesAndFolders, includeFolders);
		List<File> filesFromFolder2 = getFilesFromFolder(folder2,
				includeHiddenFilesAndFolders, includeFolders);

		System.out.println(folder1);
		System.out.println();
		printMissingFilesInFolder(folder1, filesFromFolder1, folder2,
				filesFromFolder2);
		// TODO also show which file is newer

		System.out.println();
		System.out.println();
		System.out.println(folder2);
		System.out.println();
		printMissingFilesInFolder(folder2, filesFromFolder2, folder1,
				filesFromFolder1);
		// TODO also show which file is newer

	}

	private static void printMissingFilesInFolder(File filesPath,
			List<File> files, File filesToFindPath, List<File> filesToFind) {
		for (File fileToFind : filesToFind) {
			if (!contains(filesPath, files, filesToFindPath, fileToFind)) {
				System.out.println("Missing: " + getRelativePath(filesToFindPath, fileToFind));
			}
		}
	}

	private static boolean contains(File filesPath, List<File> files,
			File fileToFindPath, File fileToFind) {
		String relativePathToFind = getRelativePath(fileToFindPath, fileToFind);// remove
																				// path
		for (File file : files) {
			String relativePath = getRelativePath(filesPath, file);
			if (relativePathToFind.equals(relativePath)) {
				return true;
			}
		}
		return false;
	}

	private static String getRelativePath(File filePath, File file) {
		return file.getAbsolutePath().replace(filePath.getAbsolutePath(), "");
	}

	private static List<File> getFilesFromFolder(File folder,
			boolean includeHiddenFilesAndFolders, boolean includeFolders) {
		List<File> foundFiles = new ArrayList<File>();
		File[] files = folder.listFiles();
		for (File file : files) {
			if (includeHiddenFilesAndFolders || !file.isHidden())
				if (file.isFile()) {
					foundFiles.add(file);
				} else if (file.isDirectory()) {
					if (includeFolders) {
						foundFiles.add(file);
					}
					foundFiles.addAll(getFilesFromFolder(file,
							includeHiddenFilesAndFolders, includeFolders));
				}
		}
		return foundFiles;
	}

	private static String getFolder1(String[] args) {
		for (String arg : args) {
			if (arg.length() > 2
					&& !(arg.startsWith("-") || arg.startsWith("/"))) {
				return arg;
			}
		}
		return null;
	}

	private static String getFolder2(String[] args) {
		int foldersFound = 0;
		for (String arg : args) {
			if (arg.length() > 2
					&& !(arg.startsWith("-") || arg.startsWith("/"))) {
				foldersFound++;
				if (foldersFound > 1) {
					return arg;
				}
			}
		}
		return null;
	}

	private static void showHelp() {
		System.out
				.println("Usage: FolderCompare \"folder1\" \"folder2\" [/H] [/F]");
		System.out.println();
		System.out.println(" /H to include hidden files and folders");
		System.out.println(" /F to include folders");
		System.out.println(" /? gets this help");
	}

	private static boolean containsCommand(String[] args, char command) {
		command = Character.toUpperCase(command);
		for (String arg : args) {
			String upperCaseArg = arg.toUpperCase().trim();
			if (upperCaseArg.length() == 2
					&& upperCaseArg.charAt(2) == command
					&& (upperCaseArg.startsWith("-") || upperCaseArg
							.startsWith("/"))) {
				return true;
			}
		}
		return false;
	}

}
