package com.betatest.canalkidsbeta.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.betatest.canalkidsbeta.download.ExternalStorage;
import com.betatest.canalkidsbeta.interfaces.AsyncTaskInterface;
import com.betatest.canalkidsbeta.model.Movie;
import com.betatest.canalkidsbeta.util.VideoController;

public class VideoLoaderTask extends
		AsyncTask<Movie, Void, Boolean> {

	private Context context;
	public AsyncTaskInterface delegate = null;

	public VideoLoaderTask(Context context) {
		this.context = context.getApplicationContext();
	}

	// TODO STILL GETTING FROM URL, WHAT HAPPENS IF THERE'S NO CONNECTION?
	@Override
	protected Boolean doInBackground(Movie... params) {
	
		String fileDownloadUrl = params[0].urlDownload;
		String fileName = params[0].tag;
		
		VideoController videoController = new VideoController();
		return videoController.videoDownload(context, fileName, fileDownloadUrl);
	}
	
	@Override
	protected void onPostExecute(Boolean result) {
	}
}
