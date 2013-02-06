package com.example.androidjsontest.activities;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

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
		
		listComplex = (ListView) findViewById(R.id.listMain);
		channelContentsResponseParcel = getIntent().getExtras().getParcelable("movies");
		movies = Movie.getMoviesList(channelContentsResponseParcel);
		MovieAdapter adapter = new MovieAdapter(movies, this);
		listComplex.setAdapter(adapter);
		listComplex.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				arg1.setSelected(true);
				Toast.makeText(getBaseContext(), "Movie = " + movies.get(arg2), Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
}
