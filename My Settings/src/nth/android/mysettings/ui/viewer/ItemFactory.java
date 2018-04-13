package nth.android.mysettings.ui.viewer;

import java.util.ArrayList;
import java.util.List;

import nth.android.mysettings.dom.PlayListType;
import nth.android.mysettings.dom.Rating;
import nth.android.mysettings.service.DownRateAllFiles;
import nth.android.mysettings.service.FindComparableFiles;
import nth.android.mysettings.service.MoveDownloadedFiles;
import nth.android.mysettings.service.OpenUrlInIncogintoBrowser;
import nth.android.mysettings.ui.dialog.Item;

public class ItemFactory {
	public static List<Item> createMenuItems(final ViewerActivity viewerActivity) {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new OpenUrlInIncogintoBrowserItem("http://perfectgirls.net", viewerActivity));

		items.add(new OpenUrlInIncogintoBrowserItem("https://www.pornjam.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("http://www.slutload.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("https://www.fux.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("https://www.pornozot.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("https://www.empflix.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("http://vnjav.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("http://www.runporn.com/", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("https://xhamster.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("https://youporn.com", viewerActivity));
		items.add(new OpenUrlInIncogintoBrowserItem("https://opwindend.net", viewerActivity));

		items.add(new Item("Show info", new Runnable() {
			@Override
			public void run() {
				viewerActivity.showInfoDialog();
			}
		}));
		items.add(new Item("Move downloaded files", new Runnable() {
			@Override
			public void run() {
				new MoveDownloadedFiles(viewerActivity).execute();
			}
		}));
		items.add(new Item("Down rate all files", new Runnable() {
			@Override
			public void run() {
				new DownRateAllFiles(viewerActivity).execute();
				//new Delete5WorstAndBigestFiles(viewerActivity).execute();
			}
		}));
		items.add(new Item("Find comparable files", new Runnable() {
			@Override
			public void run() {
				new FindComparableFiles(viewerActivity).execute();
			}
		}));
		
		// CharSequence[] options = {SHOW_INFO,MOVE_DOWNLOADED_FILES,DELETE_5WORST_FILES,DELETE_DOUBLE_FILE};
				// final ViewerActivity viewActivity = this;
				// AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
				// dialogBuilder.setSingleChoiceItems(options, -1, new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface dialog, int item) {
				// dialog.dismiss();
				// switch (item) {
				// case 0:
				// showInfoDialog();
				// break;
				// case 1:
				// new MoveDownloadedFiles(viewActivity).execute();
				// break;
				// case 2:
				// new Delete5WorstAndBigestFiles(viewActivity).execute();
				// break;
				// case 3:
				// new FindComparableFiles(viewActivity).execute();
				// break;
				
		
		
		return items;
	}

	public static List<Item> createRatingItems(final ViewerActivity viewerActivity) {
		ArrayList<Item> items = new ArrayList<Item>();
		Rating[] ratings = Rating.values();
		for (final Rating rating : ratings) {
			Item item = new Item(rating.toString().replace("_", ""), new Runnable() {
				@Override
				public void run() {
					viewerActivity.setRating(rating);
				}
			});
			items.add(item);
		}
		return items;
	}
	
	public static List<Item> createPlayListTypeItems(final ViewerActivity viewerActivity) {
		ArrayList<Item> items = new ArrayList<Item>();
		PlayListType[] playListTypes = PlayListType.values();
		for (final PlayListType playListType : playListTypes) {
			Item item = new Item(playListType.toString(), new Runnable() {
				@Override
				public void run() {
					viewerActivity.setPlayListType(playListType);
				}
			});
			items.add(item);
		}
		return items;
	}
}
