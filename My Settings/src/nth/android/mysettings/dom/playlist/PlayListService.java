package nth.android.mysettings.dom.playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import nth.android.mysettings.dom.PlayListType;
import nth.android.mysettings.dom.Rating;
import android.annotation.SuppressLint;
import android.content.Context;

public class PlayListService {

	public static List<PlayListItem> getPlayList(PlayListType playListType, Context context) {
		switch (playListType) {
		case NEW:
			return getPlayListWithNewItems(context);
		case RANDOM:
			return getPlayListWithRandomItems(context);
		case BEST:
			return getPlayListWithBestItems(context);
		case RATING_5:
			return getPlatListWithRating(Rating._5, context);
		case RATING_4:
			return getPlatListWithRating(Rating._4, context);
		case RATING_3:
			return getPlatListWithRating(Rating._3, context);
		case RATING_2:
			return getPlatListWithRating(Rating._2, context);
		case RATING_1:
			return getPlatListWithRating(Rating._1, context);
		case WORST:
			return getPlayListWithWorstItems(context);
		case LAST:
			return getPlatListWithLatestViewedItems(context);
		case FIRST:
			return getPlatListWithFirstViewedItems(context);
		case BIGGEST:
			return getPlatListWithBigestItems( context);
		case DOUBLES:
			return getPlayListWithDoubles(context);
		default:
			return null;
		}

	}

	private static List<PlayListItem> getPlatListWithBigestItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return true;// all items
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@SuppressLint("UseValueOf")
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return new Long(playListItem2.getFile().length()).compareTo(playListItem1.getFile().length());
			}
		};
		return getPlayList(filter, comparator, context);
	}

	private static List<PlayListItem> getPlatListWithRating(final Rating rating, Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return playListItem.getRating().equals(rating);
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return playListItem1.getLastViewed().compareTo(playListItem2.getLastViewed());
			}
		};
		return getPlayList(filter, comparator, context);
	}

	private static List<PlayListItem> getPlatListWithLatestViewedItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return !playListItem.getLastViewed().equals(PlayListItem.NEVER_VIEWED);
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return playListItem2.getLastViewed().compareTo(playListItem1.getLastViewed());
			}
		};
		return getPlayList(filter, comparator, context);
	}

	private static List<PlayListItem> getPlatListWithFirstViewedItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return !playListItem.getLastViewed().equals(PlayListItem.NEVER_VIEWED);
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return playListItem1.getLastViewed().compareTo(playListItem2.getLastViewed());
			}
		};
		return getPlayList(filter, comparator, context);
	}

	private static List<PlayListItem> getPlayListWithBestItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return playListItem.getRating() != PlayListItem.NEVER_RATED;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				int order = playListItem1.getRating().compareTo(playListItem2.getRating());
				if (order == 0) {// =same rating
					order = playListItem1.getLastViewed().compareTo(playListItem2.getLastViewed());
				}
				return order;
			}
		};
		return getPlayList(filter, comparator, context);
	}

	private static List<PlayListItem> getPlayListWithWorstItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return playListItem.getRating() != PlayListItem.NEVER_RATED;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@SuppressLint("UseValueOf")
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				int order = playListItem2.getRating().compareTo(playListItem1.getRating());
				if (order == 0) {// =same rating
					// biggest files first
					return new Long(playListItem2.getFile().length()).compareTo(playListItem1.getFile().length());
				}
				return order;
			}
		};
		return getPlayList(filter, comparator, context);
	}

	public static List<PlayListItem> getPlayListWith5WorstBiggestItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return playListItem.getRating() != Rating._0 && playListItem.getRating() != Rating._4 && playListItem.getRating() != Rating._5;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@SuppressLint("UseValueOf")
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				int order = playListItem2.getRating().compareTo(playListItem1.getRating());
				if (order == 0) {// =same rating
					// biggest files first
					return new Long(playListItem2.getFile().length()).compareTo(playListItem1.getFile().length());
				}
				return order;
			}
		};

		List<PlayListItem> items = getPlayList(filter, comparator, context);
		while (items.size() > 5) {
			items.remove(items.size() - 1);
		}
		return items;
	}

	private static List<PlayListItem> getPlayListWithRandomItems(Context context) {

		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return playListItem.getRating() != PlayListItem.NEVER_RATED;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return 0;
			}
		};
		List<PlayListItem> randomItems = getPlayList(filter, comparator, context);
		Collections.shuffle(randomItems, new Random(System.nanoTime()));
		return randomItems;
	}

	private static List<PlayListItem> getPlayListWithNewItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return playListItem.getRating() == PlayListItem.NEVER_RATED;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return 0;// no need to order items
			}
		};
		return getPlayList(filter, comparator, context);
	}

	public static List<PlayListItem> getPlayListWithAllItems(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return true;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return 0;// no need to order items
			}
		};
		return getPlayList(filter, comparator, context);
	}

	public static List<PlayListItem> getPlayListWithAllItemsOrderdByName(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return true;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return playListItem1.getName().compareTo(playListItem2.getName());
			}
		};
		return getPlayList(filter, comparator, context);
	}

	public static List<PlayListItem> getPlayListWithAllItemsOrderdBySize(Context context) {
		PlayListFilter filter = new PlayListFilter() {
			@Override
			public boolean isIncluded(PlayListItem playListItem) {
				return true;
			}
		};
		Comparator<PlayListItem> comparator = new Comparator<PlayListItem>() {
			@SuppressLint("UseValueOf")
			@Override
			public int compare(PlayListItem playListItem1, PlayListItem playListItem2) {
				return new Long(playListItem2.getFile().length()).compareTo(playListItem1.getFile().length());
			}
		};
		return getPlayList(filter, comparator, context);
	}

	private static List<PlayListItem> getPlayList(PlayListFilter filter, Comparator<PlayListItem> comparator, Context context) {
		List<PlayListItem> playList = new ArrayList<PlayListItem>();
		File[] children = FileService.getMoviesFolder(context).listFiles();
		if (children != null) {
			for (File child : children) {
				if (child.isFile()) {
					PlayListItem item = new PlayListItem(child);
					if (filter.isIncluded(item)) {
						playList.add(item);
					}
				}
			}
		}
		Collections.sort(playList, comparator);
		return playList;
	}

	private static List<PlayListItem> getPlayListWithDoubles(Context context) {
		// Same file names
		List<PlayListItem> allItems = PlayListService.getPlayListWithAllItemsOrderdByName(context);
		List<PlayListItem> doubleItems = new ArrayList<PlayListItem>();
		for (int i = 0; i < allItems.size() - 1; i++) {// skip last one because we have none to compare it with
			PlayListItem playListItem1 = allItems.get(i);
			PlayListItem playListItem2 = allItems.get(i + 1);
			if (playListItem1.getName().equalsIgnoreCase(playListItem2.getName())) {
				if (!doubleItems.contains(playListItem1)) {
					doubleItems.add(playListItem1);
				}
				if (!doubleItems.contains(playListItem2)) {
					doubleItems.add(playListItem2);
				}
			}
		}

		// Same file sizes
		allItems = PlayListService.getPlayListWithAllItemsOrderdBySize(context);
		for (int i = 0; i < allItems.size() - 1; i++) {// skip last one because we have none to compare it with
			PlayListItem playListItem1 = allItems.get(i);
			PlayListItem playListItem2 = allItems.get(i + 1);
			if (playListItem1.getFile().length() == playListItem2.getFile().length()) {
				if (!doubleItems.contains(playListItem1)) {
					doubleItems.add(playListItem1);
				}
				if (!doubleItems.contains(playListItem2)) {
					doubleItems.add(playListItem2);
				}
			}
		}

		return doubleItems;
	}
}
