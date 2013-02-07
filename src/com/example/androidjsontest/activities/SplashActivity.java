package com.example.androidjsontest.activities;

import java.io.IOException;
import java.util.Iterator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.androidjsontest.R;
import com.example.androidjsontest.adapter.DBAdapter;
import com.example.androidjsontest.bean.parcelable.ChannelContentsParcel;
import com.example.androidjsontest.bean.parcelable.ChannelContentsResponseParcel;
import com.example.androidjsontest.util.JSONLoader;
import com.example.androidjsontest.util.JSONParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SplashActivity extends Activity {
	// creates a ViewSwitcher object, to switch between Views
	private ViewSwitcher viewSwitcher;

	private String channelContentsUrl = "https://s3.amazonaws.com/nativeapps/channel_kids/videos/channelkids_ios.json";
	JSONLoader jsonLoader = new JSONLoader();

	Context context;
	private DBAdapter datasource;

	private ChannelContentsParcel channelContentsParcel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		context = this;
		// Initialize a LoadViewTask object and call the execute() method
		new LoadViewTask().execute();
	}

	// To use the AsyncTask, it must be subclassed
	private class LoadViewTask extends
			AsyncTask<String, Integer, ChannelContentsResponseParcel> {
		// A TextView object and a ProgressBar object
		private TextView tv_progress;
		private ProgressBar pb_progressBar;

		// Before running code in the separate thread
		@Override
		protected void onPreExecute() {
			
			// Initialize the ViewSwitcher object
			viewSwitcher = new ViewSwitcher(SplashActivity.this);
			/*
			 * Initialize the loading screen with data from the
			 * 'loadingscreen.xml' layout xml file. Add the initialized View to
			 * the viewSwitcher.
			 */
			viewSwitcher.addView(ViewSwitcher.inflate(SplashActivity.this,
					R.layout.activity_splash, null));

			// Initialize the TextView and ProgressBar instances - IMPORTANT:
			// call findViewById() from viewSwitcher.
			tv_progress = (TextView) viewSwitcher
					.findViewById(R.id.tv_progress);
			pb_progressBar = (ProgressBar) viewSwitcher
					.findViewById(R.id.pb_progressbar);

			// Set ViewSwitcher instance as the current View.
			setContentView(viewSwitcher);
		}

		// The code to be executed in a background thread.
		@Override
		protected ChannelContentsResponseParcel doInBackground(String... params) {

			ChannelContentsResponseParcel channelContentsResponseParcel = null;
			int contentsSize = 0;
			int counter = 0;

			String response = JSONParser.getUrlResponse(channelContentsUrl);

			try {
				channelContentsResponseParcel = JSONParser
						.getChannelContentsObjFromJson(response);
			} catch (JsonParseException e) {
				e.printStackTrace();
				return null;

			} catch (JsonMappingException e) {
				e.printStackTrace();
				return null;

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			try {
				// Get the current thread's token
				synchronized (context) {
					datasource = new DBAdapter(context);
					datasource.open();
					Iterator<ChannelContentsParcel> it = channelContentsResponseParcel
							.getContents().iterator();

					contentsSize = channelContentsResponseParcel.contentsSize();
					// Sets the maximum value of the progress bar to number of
					// channelContents
					pb_progressBar.setMax(contentsSize);

					while (it.hasNext()) {
						channelContentsParcel = it.next();
						datasource.createChannelContents(
								channelContentsParcel.getName(),
								channelContentsParcel.getDescription(),
								channelContentsParcel.getTag(),
								channelContentsParcel.getAccountType(),
								channelContentsParcel.getEpisodeImg(),
								channelContentsParcel.getEpisodeIdiOS(),
								channelContentsParcel.getDownloadUrl(),
								channelContentsParcel.getInclusionTime(),
								channelContentsParcel.getPublishTime());

						counter++;
						publishProgress(counter);
					}

					datasource.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return channelContentsResponseParcel;
		}

		// Update the TextView and the progress at progress bar
		@Override
		protected void onProgressUpdate(Integer... values) {
			// Update the progress at the UI if progress value is smaller than
			// 100
			if (values[0] <= 100) {
				tv_progress.setText("Carregando: "
						+ Integer.toString(values[0]) + "%");
				pb_progressBar.setProgress(values[0]);
			}
		}

		// After executing the code in the thread
		@Override
		protected void onPostExecute(ChannelContentsResponseParcel result) {

			Intent intent = new Intent(context, MainListActivity.class);
			intent.putExtra("movies", result);
			startActivity(intent);
			finish();
		}
	}

	// Override the default back key behavior
	@Override
	public void onBackPressed() {
		// Emulate the progressDialog.setCancelable(false) behavior
		// If the first view is being shown
		if (viewSwitcher.getDisplayedChild() == 0) {
			// Do nothing
			return;
		} else {
			// Finishes the current Activity
			super.onBackPressed();
		}
	}
}