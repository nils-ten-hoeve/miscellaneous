package nth.android.mysettings.service;

import java.util.List;

import nth.android.mysettings.dom.playlist.PlayListItem;
import nth.android.mysettings.dom.playlist.PlayListService;
import nth.android.mysettings.ui.dialog.Dialog;
import android.content.Context;
import android.os.AsyncTask;

public class Delete5WorstAndBigestFiles extends AsyncTask<Void, Void, String> {

	
	private final Context context;


	public Delete5WorstAndBigestFiles(Context context) {
		this.context = context;
	}

	@Override
	protected void onPostExecute(String message) {
		new Dialog(context, "Deleted 5 worst files", message).show();
	}
	
	@Override
	protected String doInBackground(Void... context) {
		List<PlayListItem> itemsToDelete = PlayListService.getPlayListWith5WorstBiggestItems();

		StringBuffer message = new StringBuffer();
		message.append("Deleted 5 worst and biggest files:\n");
		for (PlayListItem playListItem : itemsToDelete) {
			boolean succes =  playListItem.getFile().delete();
			if (succes) {
				message.append(playListItem.getFile().getName());
				message.append("\n");
			}
		}
		return message.toString();
	}
	
	

}
