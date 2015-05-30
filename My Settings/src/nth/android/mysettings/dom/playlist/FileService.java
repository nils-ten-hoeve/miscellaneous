package nth.android.mysettings.dom.playlist;

import java.io.File;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.text.GetChars;

public class FileService {
	public static final String PARAMETER_SEPARATOR = "-";
	public static final String EXTENTION_SEPERATOR = ".";
	public static final String FILE_SEPRATOR = "/";

	
	private static String getInternalSdCardPath(String relativePath) {
		if (Build.VERSION.SDK_INT<16) {
			return "/mnt/sdcard"+relativePath	;
		} else {//4.1 and higher
			return "/storage/sdcard0"+relativePath;
		}
	}

	private static String getExternalSdCard(String relativePath) {
		if (Build.VERSION.SDK_INT<16) {
			//for galaxi: return "/mnt/sdcard/external_sd"+relativePath	
			return "/mnt/external_sd"+relativePath; //for lenco
		} else {//4.1 and higher
			return "/storage/extSdCard"+relativePath;
		}
	}

	
	public static File getInternalDownloadFolder() {
		return new File(getInternalSdCardPath("/Download"));
	}


	public static File getExternalDownloadFolder() {
		return new File(getExternalSdCard("/Download"));
	}

	public static  File getMoviesFolder(Context context) {
		//TODO using application folder because API19 does not allow apps to modify (rename, delete) files in folders that aren't owned by the app. See http://www.androidcentral.com/kitkat-sdcard-changes
		File[] movieFolders = context.getExternalFilesDirs(Environment.DIRECTORY_MOVIES);
		File movieFolder;
		if (movieFolders.length>1) {
			movieFolder=movieFolders[1];//external sd card
		} else {
			movieFolder=movieFolders[1];//internal device memory
		}
		
		movieFolder = new File(movieFolder.getAbsolutePath().replace("Movies", ".movies"));
		if (!movieFolder.exists()) {
			movieFolder.mkdir();
		}
		return movieFolder;
	}



//	public static void createMySettingsFolder(Context context) {
//		// create external_ds folder when needed (only for debugging)
//		File MOVIES_FOLDER=getMoviesFolder(context);
////		File EXTERNAL_DS_FOLDER = MY_SETTINGS_FOLDER.getParentFile();
////		if (!EXTERNAL_DS_FOLDER.exists()) {
////			EXTERNAL_DS_FOLDER.mkdir();
////		}
//		// create my settings folder if needed
//		if (!MOVIES_FOLDER.exists()) {
//			MOVIES_FOLDER.mkdir();
//		}
//	}

	
	public static long getDirSize(File dir) {
		long size = 0;
//		if (dir.isFile()) {
//			size = dir.length();
//		} else {
			File[] subFiles = dir.listFiles();

			for (File file : subFiles) {
				if (file.isFile()) {
					size += file.length();
				} else {
					size += getDirSize(file);
				}
			}
//		}
		return size;
	}

}
