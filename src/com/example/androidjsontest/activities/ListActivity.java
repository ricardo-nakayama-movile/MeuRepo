package com.example.androidjsontest.activities;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.VideoView;

import com.example.androidjson.model.Movie;
import com.example.androidjsontest.R;
import com.example.androidjsontest.adapter.MovieAdapter;
import com.example.androidjsontest.bean.parcelable.ChannelContentsResponseParcel;
import com.example.androidjsontest.util.VideoController;

public class ListActivity extends Activity {

	private ListView listComplex;
	private ChannelContentsResponseParcel channelContentsResponseParcel;
	private List<Movie> movies;
	private VideoView videoView;

	Context context;

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
		context = this;

		listComplex.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Movie movie = movies.get(arg2);
				VideoController videoController = new VideoController();
				
				setContentView(R.layout.videolayout);
				videoView = (VideoView) findViewById(R.id.playvideo);
				videoController.loadVideo(context, movie, videoView);

			}
		});
	}
}
