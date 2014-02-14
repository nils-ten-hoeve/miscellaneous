package nth.android.mysettings.dom.playlist;

import java.io.File;

import android.os.Build;

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
		return new File(getInternalSdCardPath("/download"));
	}


	public static File getExternalDownloadFolder() {
		return new File(getExternalSdCard("/download"));
	}

	public static  File getMySettingsFolder() {
		return new File(getExternalSdCard("/.My Settings"));
	}



	public static void createMySettingsFolder() {
		// create external_ds folder when needed (only for debugging)
		File MY_SETTINGS_FOLDER=getMySettingsFolder();
//		File EXTERNAL_DS_FOLDER = MY_SETTINGS_FOLDER.getParentFile();
//		if (!EXTERNAL_DS_FOLDER.exists()) {
//			EXTERNAL_DS_FOLDER.mkdir();
//		}
		// create my settings folder if needed
		if (!MY_SETTINGS_FOLDER.exists()) {
			MY_SETTINGS_FOLDER.mkdir();
		}
	}

	
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
