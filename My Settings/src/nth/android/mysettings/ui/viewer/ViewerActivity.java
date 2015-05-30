package nth.android.mysettings.ui.viewer;

import java.io.File;
import java.util.List;

import nth.android.mysettings.R;
import nth.android.mysettings.dom.PlayListType;
import nth.android.mysettings.dom.Rating;
import nth.android.mysettings.dom.playlist.FileService;
import nth.android.mysettings.dom.playlist.PlayListItem;
import nth.android.mysettings.dom.playlist.PlayListService;
import nth.android.mysettings.ui.dialog.Dialog;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class ViewerActivity extends Activity {
	private List<PlayListItem> playList;
	private PlayListItem playListItem;
	private PlayListType playListType;

	private void initDeleteButton() {
		ImageButton button = (ImageButton) findViewById(R.id.ButtonDelete);
		button.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				onDeleteItem(null);
				return true;
			}
		});
	}

	public void initMediaPlayer() {
		VideoView viewer = (VideoView) findViewById(R.id.VideoPlayer);
		viewer.setMediaController(new MediaController(this));

		// volume off by default
		AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audio.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_PLAY_SOUND);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewer);

		initMediaPlayer();
		initDeleteButton();

		setPlayListType(PlayListType.NEW);
		if (playList.size() == 0) {
			setPlayListType(PlayListType.RANDOM);
		}

		// FileService.createMySettingsFolder();
		// startService(new Intent(this, MoveDownloadedFiles.class));
		// TODO create a menu with a info item
		// TODO create a menu with a move downloaded files item: startService(new Intent(this, MoveDownloadedFiles.class));
		// TODO create a menu with a "make space" menu item (delete 20 worst and biggest files)

	}

	public void onDeleteItem(View v) {
		// remove item from playlist
		PlayListItem itemToDelete = playListItem;
		int index = playList.indexOf(itemToDelete);
		playList.remove(itemToDelete);

		// get next item
		PlayListItem nextItem = null;
		if (playList.size() > 0) {
			if (index >= playList.size()) {
				index = playList.size() - 1;
			}
			nextItem = playList.get(index);
		}

		// start new video
		setPlayListItem(nextItem);

		// remove file
		try {
			boolean succes = itemToDelete.getFile().delete();
			if (succes) {
				Toast.makeText(this, "Item deleted", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "Failed to delete", Toast.LENGTH_LONG).show();
			}
		} catch (Exception exception) {
			Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG);
		}

	}

	public void onExitApplication(View v) {
		if (playListItem != null) {
			playListItem.justViewed();// update filename
		}
		finish();
		System.exit(0);
	}

	public void onMenu(View v) {
		showMenuDialog();
	}

	public void onNextItem(View v) {
		int index = playList.indexOf(playListItem);
		if (playList.size() == 0) {
			Toast.makeText(this, "No items in play list", Toast.LENGTH_LONG).show();
		} else if (index == playList.size() - 1) {
			Toast.makeText(this, "Last item in play list", Toast.LENGTH_LONG).show();
		} else {
			index++;
			setPlayListItem(playList.get(index));
		}
	}

	public void onPreviousItem(View v) {
		int index = playList.indexOf(playListItem);
		if (playList.size() == 0) {
			Toast.makeText(this, "No items in play list", Toast.LENGTH_LONG).show();
		} else if (index < 1) {
			Toast.makeText(this, "First item in play list", Toast.LENGTH_LONG).show();
			index = 0;
		} else {
			index--;
			setPlayListItem(playList.get(index));
		}
	}

	public void onSelectPlayListType(View v) {
		showPlayListTypeDialog();
	}

	public void onSelectRating(View v) {
		if (playListItem != null) {
			showRatingDialog();
		}
	}

	public void setPlayListItem(PlayListItem newPlayListItem) {
		VideoView viewer = (VideoView) findViewById(R.id.VideoPlayer);

		if (newPlayListItem != null) {

			// start video
			Uri video = Uri.parse(newPlayListItem.getFile().getPath());
			StringBuffer message = new StringBuffer();
			message.append("Item ");
			message.append(playList.indexOf(playListItem));
			message.append(" of ");
			message.append(playList.size());
			Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show();
			viewer.setVideoURI(video);
			viewer.requestFocus();
			viewer.start();

			if (playListItem != null) {
				playListItem.justViewed();// update filename of previous item
			}

			this.playListItem = newPlayListItem;
			// set rating button
			Rating rating = newPlayListItem.getRating();
			setRating(rating);
		} else {
			Toast.makeText(this, "No items in play list", Toast.LENGTH_LONG).show();
			this.playListItem = null;
			viewer.stopPlayback();
			// set rating button
			setRating(Rating._0);
		}
	}

	public void setPlayListType(PlayListType playListType) {
		this.playListType = playListType;
		// set text of selection button
		ImageButton button = (ImageButton) findViewById(R.id.ButtonSelectPlayList);
		button.setImageDrawable(new IconText(playListType.toString(), 90 / playListType.toString().length()));

		// get new play list
		playList = PlayListService.getPlayList(playListType, this);
		if (playList.size() > 0) {
			setPlayListItem(playList.get(0));
		} else {
			setPlayListItem(null);
		}
	}

	protected void setRating(Rating rating) {
		// set text of selection button
		ImageButton button = (ImageButton) findViewById(R.id.ButtonSelectRating);
		button.setImageDrawable(new IconText(rating.toString().substring(1), 40));

		// set rating of play list item
		if (playListItem != null) {
			playListItem.setRating(rating);
		}
	}

	public void showInfoDialog() {
		// AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
		StringBuffer message = new StringBuffer();

		File path = FileService.getMoviesFolder(this);
		message.append("\nMy Settings folder:\n  Path:");
		message.append(path.getAbsolutePath());
		message.append("\n  Nr of Files:");
		message.append(path.listFiles().length);
		message.append("\n  Free space:");
		message.append(path.getFreeSpace() / 1024 / 1024);
		message.append(" Mb");
		message.append("\n  Used space:");
		message.append(FileService.getDirSize(path) / 1024 / 1024);
		message.append(" Mb");

		path = FileService.getExternalDownloadFolder();
		message.append("\nExternal download folder:\n  Path:");
		message.append(path.getAbsolutePath());
		message.append("\n  Nr of Files:");
		message.append(path.listFiles().length);
		message.append("\n  Free space:");
		message.append(path.getFreeSpace() / 1024 / 1024);
		message.append(" Mb");
		message.append("\n  Used space:");
		message.append(FileService.getDirSize(path) / 1024 / 1024);
		message.append(" Mb");

		path = FileService.getInternalDownloadFolder();
		message.append("\nInternal download folder:\n  Path:");
		message.append(path.getAbsolutePath());
		message.append("\n  Nr of Files:");
		message.append(path.listFiles().length);
		message.append("\n  Free space:");
		message.append(path.getFreeSpace() / 1024 / 1024);
		message.append(" Mb");
		message.append("\n  Used space:");
		message.append(FileService.getDirSize(path) / 1024 / 1024);
		message.append(" Mb");

		message.append("\n");

		List<PlayListItem> playListItems = PlayListService.getPlayListWithAllItems(this);

		// for each rating
		for (Rating rating : Rating.values()) {

			message.append("\nNumber of files with rating ");
			message.append(rating.toString().substring(1));
			message.append(":");
			int itemsFound = 0;
			for (PlayListItem playListItem : playListItems) {
				if (playListItem.getRating().equals(rating)) {
					itemsFound++;
				}
			}
			message.append(itemsFound);

		}

		message.append("\n\nPlaylist:");
		if (playList.size() > 0) {
			message.append("\nCurrent item name:");
			message.append(playListItem.getFile().getName());
			message.append("\nCurrent item index:");
			message.append(playList.indexOf(playListItem));
		}
		message.append("\nNumber of items:");
		message.append(playList.size());

		for (PlayListItem playListItem : playList) {
			message.append("\n");
			message.append(playListItem.getFile().getName());
		}

		Dialog dialog = new Dialog(this, "Info", message);
		dialog.show();
	}

	private void showPlayListTypeDialog() {
		PlayListType[] playListTypes = PlayListType.values();
		int i = 0;
		int selectedItem = -1;
		for (PlayListType playListType : playListTypes) {
			if (playListType.equals(this.playListType)) {
				selectedItem = i;
			}
			i++;
		}
		new Dialog(this, "Select Playlist", ItemFactory.createPlayListTypeItems(this), selectedItem).show();
	}

	private void showMenuDialog() {
		new Dialog(this, "Menu", ItemFactory.createMenuItems(this)).show();
	}

	private void showRatingDialog() {
		Rating[] ratings = Rating.values();
		int i = 0;
		int selectedItem = -1;
		for (Rating rating : ratings) {
			if (playListItem != null && rating.toString().contains(playListItem.getRating() + "")) {
				selectedItem = i;
			}
			i++;
		}
		new Dialog(this, "Select rating", ItemFactory.createRatingItems(this), selectedItem).show();
	}

}