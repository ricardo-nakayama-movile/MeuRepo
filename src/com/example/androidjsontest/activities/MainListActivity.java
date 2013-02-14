package com.example.androidjsontest.activities;

import java.util.List;

import android.app.Activity;
import android.app.DownloadManager;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.androidjson.model.Movie;
import com.example.androidjsontest.R;
import com.example.androidjsontest.adapter.MovieAdapter;
import com.example.androidjsontest.bean.parcelable.ChannelContentsResponseParcel;

public class MainListActivity extends Activity {

	private ListView listComplex;
	private List<Movie> movies;
	private ChannelContentsResponseParcel channelContentsResponseParcel;
	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_list);

		listComplex = (ListView) findViewById(R.id.list_main);
		channelContentsResponseParcel = getIntent().getExtras().getParcelable(
				"movies");
		movies = Movie.getMoviesList(channelContentsResponseParcel);
		MovieAdapter adapter = new MovieAdapter(movies, this);
		listComplex.setAdapter(adapter);

		listComplex.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				// TODO DOWNLOAD ESTA SENDO FEITO AQUI, MUDAR!

				String testeDownload = movies.get(arg2).urlDownload;
				String testeStream = movies.get(arg2).urlMovie;
				
				DownloadManager downloadManager;
				long downloadReference;

				downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
				Uri Download_Uri = Uri.parse(testeDownload);
				DownloadManager.Request request = new DownloadManager.Request(
						Download_Uri);

				// Restrict the types of networks over which this download may
				// proceed.
				request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
						| DownloadManager.Request.NETWORK_MOBILE);
				// Set whether this download may proceed over a roaming
				// connection.
				request.setAllowedOverRoaming(false);
				// Set the title of this download, to be displayed in
				// notifications (if enabled).
				request.setTitle(movies.get(arg2).title);
				// Set a description of this download, to be displayed in
				// notifications (if enabled)
				request.setDescription(movies.get(arg2).title);
				// Set the local destination for the downloaded file to a path
				// within the application's external files directory
				request.setDestinationInExternalFilesDir(MainListActivity.this,
						Environment.getExternalStorageDirectory()
								.getAbsolutePath(), movies.get(arg2).tag);

				// Enqueue a new download and same the referenceId
				//TODO VERIFICAR SE EXISTE UM VIDEO ANTES DE PUXAR NOVAMENTE
				//downloadReference = downloadManager.enqueue(request);

				// new DownloadTask().execute(arg2);

				// videoPlayer(Environment.getExternalStorageDirectory()
				// .getAbsolutePath(), "teste", true);

				setContentView(R.layout.videolayout);
				videoView = (VideoView) findViewById(R.id.playvideo);

				play(testeStream);
			}
		});

	}

	private void play(String url) {
		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(this.videoView);

		Uri video = Uri.parse(url);
		Log.e("Uri video", video.toString());
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(video);
		videoView.start();
		videoView.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	// public void videoPlayer(String path, String fileName, boolean autoplay) {
	// // get current window information, and set format, set it up
	// // differently, if you need some special effects
	// getWindow().setFormat(PixelFormat.TRANSLUCENT);
	// // the VideoView will hold the video
	// VideoView videoHolder = new VideoView(this);
	// // MediaController is the ui control howering above the video (just like
	// // in the default youtube player).
	// videoHolder.setMediaController(new MediaController(this));
	// // assing a video file to the video holder
	// videoHolder.setVideoURI(Uri.parse(path
	// + "/Android/data/com.example.androidjsontest/files/mnt/sdcard/"
	// + fileName));
	// // get focus, before playing the video.
	// videoHolder.requestFocus();
	// if (autoplay) {
	// videoHolder.start();
	// }
	// }

	// // To use the AsyncTask, it must be subclassed
	// private class DownloadTask extends
	// AsyncTask<Integer, Void, Void> {
	//
	// // The code to be executed in a background thread.
	// @Override
	// protected Void doInBackground(Integer... params) {
	//
	// String conteudo = "";
	//
	// try {
	// conteudo = HttpUtil.doHttpGet(movies.get(params[0]).urlMovie);
	// } catch (Exception e) {
	// Log.e("INFO", "erro ao conectar com servidor");
	// e.printStackTrace();
	// }
	//
	// ExternalStorage externalStorage = new ExternalStorage();
	// externalStorage.write(MainListActivity.this, movies.get(params[0]).tag,
	// conteudo);
	// Toast.makeText(getBaseContext(),
	// "Starting download of " + movies.get(params[0]).title,
	// Toast.LENGTH_SHORT).show();
	//
	// return null;
	// }
	//
	// // After executing the code in the thread
	// @Override
	// protected void onPostExecute(Void result) {
	//
	// }
	// }
}
