package com.betatest.canalkidsbeta.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.betatest.canalkidsbeta.R;
import com.betatest.canalkidsbeta.model.Movie;
import com.loopj.android.image.SmartImageView;

public class MovieAdapter extends BaseAdapter {

	private List<Movie> movies;
	private Context context;

	public MovieAdapter(List<Movie> movies, Context context) {

		this.movies = movies;
		this.context = context;
	}

	@Override
	public int getCount() {

		return movies.size();
	}

	@Override
	public Object getItem(int position) {

		return movies.get(position);
	}

	@Override
	public long getItemId(int position) {

		return movies.get(position).title.hashCode();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {

		final Movie movie = movies.get(position);

		if (view == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			view = inflater.inflate(R.layout.adapter_main_item_channel, null);
		}

		// TODO USE THE viewHolder PATTERN!!

		TextView textViewTitle = (TextView) view
				.findViewById(R.id.adapterMovieTitle);
		SmartImageView profileImage = (SmartImageView) view
				.findViewById(R.id.adapterMoviePicture);
		textViewTitle.setText(movie.title);
		profileImage.setImageUrl(movies.get(position).idImage);
		
		// LinearLayout layoutSelect = (LinearLayout) view
		// .findViewById(R.id.adapterLayoutSelect);

		// layoutSelect.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Log.i("INFO", "abriu");
		// }
		// });

		return view;
	}
}
