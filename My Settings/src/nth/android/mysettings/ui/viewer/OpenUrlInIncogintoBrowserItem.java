package nth.android.mysettings.ui.viewer;

import android.content.Context;
import nth.android.mysettings.service.OpenUrlInIncogintoBrowser;
import nth.android.mysettings.ui.dialog.Item;

public class OpenUrlInIncogintoBrowserItem extends Item {

	public OpenUrlInIncogintoBrowserItem(String url, ViewerActivity context) {
		super(url, createRunnable(url, context));
	}

	private static Runnable createRunnable(final String url, final Context context) {
		return new Runnable() {

			@Override
			public void run() {
				new OpenUrlInIncogintoBrowser(context, url).execute();
			}
		};

	}

}
