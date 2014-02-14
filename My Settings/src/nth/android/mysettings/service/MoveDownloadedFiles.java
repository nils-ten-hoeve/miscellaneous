package nth.android.mysettings.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import nth.android.mysettings.dom.playlist.FileService;
import nth.android.mysettings.dom.playlist.PlayListItem;
import nth.android.mysettings.ui.dialog.Dialog;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

@SuppressLint("DefaultLocale")
public class MoveDownloadedFiles extends AsyncTask<Void, Void, String> {

	private final Context context;

	public MoveDownloadedFiles(Context context) {
		this.context = context;
	}

	@Override
	protected void onPostExecute(String message) {
		new Dialog(context, "Move downloaded files", message).show();
	}

	@Override
	protected String doInBackground(Void... context) {

		int nrOfMovedFiles = 0;
		nrOfMovedFiles += moveFiles(FileService.getInternalDownloadFolder());
		nrOfMovedFiles += moveFiles(FileService.getExternalDownloadFolder());

		return "Moved " + nrOfMovedFiles + " files.";

	}

	private int moveFiles(File downloadFolder) {

		int nrOfMovedFiles = 0;
		File[] files = downloadFolder.listFiles();
		for (File sourceFile : files) {
			if (sourceFile.getName().toLowerCase().endsWith(".mp4")) {// only copy mp4

				StringBuffer newFilePath = new StringBuffer(FileService.getMySettingsFolder().getPath());
				newFilePath.append(FileService.FILE_SEPRATOR);
				newFilePath.append(PlayListItem.NEVER_RATED.toString().charAt(1));
				newFilePath.append(FileService.PARAMETER_SEPARATOR);
				newFilePath.append(PlayListItem.NEVER_VIEWED);
				newFilePath.append(FileService.PARAMETER_SEPARATOR);
				newFilePath.append(sourceFile.getName());
				File destinationFile = new File(newFilePath.toString());
				try {
					FileInputStream inStream = new FileInputStream(sourceFile);
					FileOutputStream outStream = new FileOutputStream(destinationFile);

					byte[] buffer = new byte[1024];

					int length;
					// copy the file content in bytes
					while ((length = inStream.read(buffer)) > 0) {

						outStream.write(buffer, 0, length);

					}

					inStream.close();
					outStream.close();

					// delete the original file
					sourceFile.delete();
					nrOfMovedFiles++;

				} catch (Exception e) {
					// failed so delete result
					destinationFile.delete();
				}
			}
		}
		return nrOfMovedFiles;
	}
}
