package nth.android.mysettings.service;

import java.util.List;

import nth.android.mysettings.dom.playlist.PlayListItem;
import nth.android.mysettings.dom.playlist.PlayListService;
import nth.android.mysettings.ui.dialog.Dialog;
import android.content.Context;
import android.os.AsyncTask;

public class FindComparableFiles extends AsyncTask<Void, Void, String> {

	private final Context context;

	public FindComparableFiles(Context context) {
		this.context = context;
	}

	@Override
	protected void onPostExecute(String message) {
		new Dialog(context, "Find comparable files", message).show();
	}

	@Override
	protected String doInBackground(Void... params) {
		StringBuffer message = new StringBuffer();

		message.append("Same file names :\n");
		List<PlayListItem> items = PlayListService.getPlayListWithAllItemsOrderdByName();
		boolean wasSame = false;
		for (int i = 0; i < items.size() - 1; i++) {// skip last one because we have none to compare it with
			PlayListItem playListItem1 = items.get(i);
			PlayListItem playListItem2 = items.get(i + 1);
			if (playListItem1.getName().equalsIgnoreCase(playListItem2.getName())) {
				if (!wasSame) {
					message.append("Name: ");
					message.append(playListItem1.getName());
					message.append("\n");
				}
				message.append(playListItem1.getFile().getName());
				message.append("\n");
				wasSame = true;
			} else {
				if (wasSame) {
					message.append(playListItem1.getFile().getName());
					message.append("\n");
				}
				wasSame = false;
			}
		}

		message.append("\nSame file sizes :\n");
		items = PlayListService.getPlayListWithAllItemsOrderdBySize();
		wasSame = false;
		for (int i = 0; i < items.size() - 1; i++) {// skip last one because we have none to compare it with
			PlayListItem playListItem1 = items.get(i);
			PlayListItem playListItem2 = items.get(i + 1);
			if (playListItem1.getFile().length() == playListItem2.getFile().length()) {
				if (!wasSame) {
					message.append("Size: ");
					message.append(playListItem1.getFile().length());
					message.append("\n");
				}
				message.append(playListItem1.getFile().getName());
				message.append("\n");
				wasSame = true;
			} else {
				if (wasSame) {
					message.append(playListItem1.getFile().getName());
					message.append("\n");
				}
				wasSame = false;
			}
		}

		return message.toString();
	}

}
