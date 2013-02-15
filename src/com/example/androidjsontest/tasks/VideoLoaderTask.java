package com.example.androidjsontest.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.androidjson.model.Movie;
import com.example.androidjsontest.interfaces.AsyncTaskInterface;
import com.example.androidjsontest.util.VideoController;

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

		String testeDownload = params[0].urlDownload;
		String testeName = params[0].tag;
		
		VideoController videoController = new VideoController();
		return videoController.videoDownload(context, testeName, testeDownload);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		
	}
}
