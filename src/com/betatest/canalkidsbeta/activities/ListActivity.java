package com.betatest.canalkidsbeta.activities;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.VideoView;

import com.betatest.canalkidsbeta.R;
import com.betatest.canalkidsbeta.adapter.MovieAdapter;
import com.betatest.canalkidsbeta.bean.ChannelContentsResponseParcel;
import com.betatest.canalkidsbeta.download.ExternalStorage;
import com.betatest.canalkidsbeta.model.Movie;
import com.betatest.canalkidsbeta.util.VideoController;

public class ListActivity extends Activity {

	private ListView listComplex;
	private ChannelContentsResponseParcel channelContentsResponseParcel;
	private List<Movie> movies;
	private VideoView videoView;

	private Button buttonTeste;
	
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
		
		//TODO STILL MISSING A BUTTON AT THE END OF THE LIST, THIS ONE IS JUST A TEST
		buttonTeste = (Button)findViewById(R.id.button_list_end);
		buttonTeste.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ExternalStorage externalStorage = new ExternalStorage();
				externalStorage.deleteMovieExternalStorage(context, "5275.mp4");
			}
		});
	}
}
