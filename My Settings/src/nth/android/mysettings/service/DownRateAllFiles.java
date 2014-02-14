package nth.android.mysettings.service;

import java.util.List;

import nth.android.mysettings.dom.Rating;
import nth.android.mysettings.dom.playlist.PlayListItem;
import nth.android.mysettings.dom.playlist.PlayListService;
import nth.android.mysettings.ui.dialog.Dialog;
import android.content.Context;
import android.os.AsyncTask;

public class DownRateAllFiles extends AsyncTask<Void, Void, String> {

	private final Context context;

	public DownRateAllFiles(Context context) {
		this.context = context;
	}

	@Override
	protected void onPostExecute(String message) {
		new Dialog(context, "Down rated all files", message).show();
	}

	@Override
	protected String doInBackground(Void... context) {
		List<PlayListItem> playListItems = PlayListService.getPlayListWithAllItems();

		int succesCount=0;
		StringBuffer message = new StringBuffer();
		for (PlayListItem playListItem : playListItems) {
			Rating rating = playListItem.getRating();
			Rating loweredRating = getLowerRating(rating);
			playListItem.setRating(loweredRating);
			boolean succes = playListItem.updateFileName();
			if (succes) {
				succesCount++;
			} else {
				message.append("Failed: ");
				message.append(playListItem.getFile().getName());
				message.append("\n");
			}
		}
		message.append("Nr of down rated files: ");
		message.append(succesCount);		
		message.append("\n");
		return message.toString();
	}

	private Rating getLowerRating(Rating rating) {
		switch (rating) {
		case _1:
			return Rating._1;// rank1 stays rank 1 because rank 0 means unranked 
		case _2:
			return Rating._1;
		case _3:
			return Rating._2;
		case _4:
			return Rating._3;
		case _5:
			return Rating._4;
		}
		//else return unranked
		return Rating._0; 
	}

}
