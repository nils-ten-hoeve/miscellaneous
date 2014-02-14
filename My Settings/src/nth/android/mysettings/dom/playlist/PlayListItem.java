package nth.android.mysettings.dom.playlist;

import java.io.File;
import java.util.Calendar;

import nth.android.mysettings.dom.Rating;

public class PlayListItem {

	public static final String NEVER_VIEWED = "00000000";
	public static final Rating NEVER_RATED = Rating._0;

	private File file;
	private String name;
	private Rating rating;
	private String lastViewed;

	public PlayListItem(File file) {
		initFields(file);
	}

	public void initFields(File file) {
		this.file = file;
		String fileName = file.getName();

		boolean fileNameContainsRatingAndLastViewedDate=fileName.length()>15&& fileName.substring(1, 2).equals(FileService.PARAMETER_SEPARATOR) && fileName.substring(10, 11).equals(FileService.PARAMETER_SEPARATOR);
		
		if (fileNameContainsRatingAndLastViewedDate) {
			rating = Rating.valueOf("_" + fileName.charAt(0));
			lastViewed = fileName.substring(2, 10);
			name = fileName.substring(11);
		} else {
			rating = NEVER_RATED;
			lastViewed = NEVER_VIEWED;
			name = fileName;
		}
	}

	public String getLastViewed() {
		if (lastViewed == null || lastViewed.length() == 0) {
			lastViewed = NEVER_VIEWED;
		}
		return lastViewed;
	}

	public Rating getRating() {
		return rating;
	}

	public String getName() {
		return name;
	}

	/**
	 * Note! you can only update the filename when it is not locked by the viewer
	 * @return 
	 * @return true if successful
	 */
	public boolean updateFileName() {
		StringBuffer newFilePath = new StringBuffer();
		newFilePath.append(file.getParentFile().getPath());
		newFilePath.append(FileService.FILE_SEPRATOR);
		newFilePath.append(getRating().toString().substring(1));
		newFilePath.append(FileService.PARAMETER_SEPARATOR);
		newFilePath.append(getLastViewed());
		newFilePath.append(FileService.PARAMETER_SEPARATOR);
		newFilePath.append(getName());
		if (file.getPath().equals(newFilePath.toString())) {
			//unchanged filename, return success
			return true;
		} else {
			//filename has changed so update it
			File newFile = new File(newFilePath.toString());
			boolean succes = file.renameTo(newFile);
			if (succes) {
				initFields(newFile);
			}
			return succes;
		}
	}

	public void setRating(Rating newRating) {
		if (rating != newRating) {
			rating = newRating;
			// filename will be updated when just viewed is called (when file is released by viewer)
		}
	}

	public void justViewed() {
		StringBuffer currentDate = new StringBuffer();
		Calendar calendar = Calendar.getInstance();
		// year
		int year = calendar.get(Calendar.YEAR);
		currentDate.append(year);
		// month
		int month = calendar.get(Calendar.MONTH) + 1;
		if (month < 10) {
			currentDate.append("0");
		}
		currentDate.append(month);
		// day
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		if (day < 10) {
			currentDate.append("0");
		}
		currentDate.append(day);
		// update lastviewed
		lastViewed = currentDate.toString();
		// update file
		updateFileName();
	}

	public File getFile() {
		return file;
	}

	@Override
	public boolean equals(Object playListItem) {
		if (playListItem!=null && playListItem instanceof PlayListItem) {
			return ((PlayListItem)playListItem).getFile().equals(file);
		} else {
			return false;
		}
	}
	
	
}
