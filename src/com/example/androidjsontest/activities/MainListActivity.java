package com.example.androidjsontest.activities;

import java.util.List;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.androidjson.model.Movie;
import com.example.androidjsontest.R;
import com.example.androidjsontest.adapter.MovieAdapter;
import com.example.androidjsontest.bean.parcelable.ChannelContentsResponseParcel;

public class MainListActivity extends Activity {

	private ListView listComplex;
	private List<Movie> movies;
	private ChannelContentsResponseParcel channelContentsResponseParcel;

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

				//TODO DOWNLOAD ESTA SENDO FEITO AQUI, MUDAR!
				
				String teste = movies.get(arg2).urlDownload;

				DownloadManager downloadManager;
				long downloadReference;

				downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
				Uri Download_Uri = Uri.parse(teste);
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
						Environment.DIRECTORY_MOVIES, movies.get(arg2).title);

				// Enqueue a new download and same the referenceId
				downloadReference = downloadManager.enqueue(request);

//				try {
//					conteudo = HttpUtil.doHttpGet(movies.get(arg2).urlMovie);
//				} catch (Exception e) {
//					Log.e("INFO", "erro ao conectar com servidor");
//					e.printStackTrace();
//				}
//
//				ExternalStorage externalStorage = new ExternalStorage();
//				externalStorage.write(MainListActivity.this,
//						movies.get(arg2).tag, conteudo);
//				Toast.makeText(getBaseContext(),
//						"Starting download of " + movies.get(arg2).title,
//						Toast.LENGTH_SHORT).show();
				
				

			}
		});

	}

}
