package com.betatest.canalkidsbeta.alarm;

import java.util.Date;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.betatest.canalkidsbeta.bean.ChannelContentsResponseParcel;
import com.betatest.canalkidsbeta.interfaces.AsyncTaskInterface;
import com.betatest.canalkidsbeta.tasks.PojoLoaderTask;

public class SchedulerEventService extends IntentService implements
		AsyncTaskInterface {

	private static final String APP_TAG = "com.betatest.canalkidsbeta";
	private String channelContentsUrl = "https://s3.amazonaws.com/nativeapps/channel_kids/videos/channelkids_ios.json";

	/**
	 * A constructor is required, and must call the super IntentService(String)
	 * constructor with a name for the worker thread.
	 */
	public SchedulerEventService() {
		super("CanalKidsBetaVideoListUpdate");
	}

	/**
	 * The IntentService calls this method from the default worker thread with
	 * the intent that started the service. When this method returns,
	 * IntentService stops the service, as appropriate.
	 */
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d(APP_TAG, "SERVICE CALLED AT: " + new Date().toString());

		// Normally we would do some work here, like download a file.
		// For our sample, we just sleep for 5 seconds.

		PojoLoaderTask pojoLoaderTask = new PojoLoaderTask(this);

		pojoLoaderTask.delegate = this;
		pojoLoaderTask.execute(channelContentsUrl);
	}

	@Override
	public void processFinish(final ChannelContentsResponseParcel output) {
		Log.d(APP_TAG, "LOADED VIDEOS FROM JSON");
	}
}