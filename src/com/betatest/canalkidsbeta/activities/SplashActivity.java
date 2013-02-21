package com.betatest.canalkidsbeta.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.betatest.canalkidsbeta.R;
import com.betatest.canalkidsbeta.bean.ChannelContentsResponseParcel;
import com.betatest.canalkidsbeta.interfaces.AsyncTaskInterface;
import com.betatest.canalkidsbeta.tasks.PojoLoaderTask;

public class SplashActivity extends Activity implements AsyncTaskInterface{
	
	private Context context;
	private String channelContentsUrl = "https://s3.amazonaws.com/nativeapps/channel_kids/videos/channelkids_ios.json";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		
		PojoLoaderTask pojoLoaderTask = new PojoLoaderTask(context);
		
		setContentView(R.layout.activity_splash);
		
		pojoLoaderTask.delegate=this;
		pojoLoaderTask.execute(channelContentsUrl);

	}
	
	@Override
	public void processFinish(final ChannelContentsResponseParcel output) {

		Intent intent = new Intent(context, ListActivity.class);
		intent.putExtra("movies", output);
		startActivity(intent);
		finish();
	}
}
