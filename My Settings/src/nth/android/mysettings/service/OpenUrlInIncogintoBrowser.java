package nth.android.mysettings.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

public class OpenUrlInIncogintoBrowser extends AsyncTask<Void, Void, String> {

	private final Context context;
	private final String uri;

	public OpenUrlInIncogintoBrowser(Context context, String uri) {
		this.context = context;
		this.uri = uri;
	}

	@Override
	protected void onPostExecute(String message) {

	}

	@Override
	protected String doInBackground(Void... v) {
		
		String packageName = "nu.tommie.inbrowser"; 
		String className = "nu.tommie.inbrowser.lib.Inbrowser"; 
		Intent intent = new Intent();
		ComponentName comp = new ComponentName(packageName,className);
		intent.setComponent(comp);
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.BROWSABLE");
		Uri uri = Uri.parse(this.uri);
		intent.setData(uri);
		context.startActivity(intent);
	
		return "Ok";
			}

}
