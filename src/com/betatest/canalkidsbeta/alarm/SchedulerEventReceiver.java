package com.betatest.canalkidsbeta.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SchedulerEventReceiver extends BroadcastReceiver {
	
	private static final String APP_TAG = "com.betatest.canalkidsbeta";

	@Override
	public void onReceive(final Context context, final Intent intent) {
		Log.d(APP_TAG, "RECEIVED ALARM BROADCAST, CALLING VIDEO LIST UPDATE");
		Intent eventService = new Intent(context, SchedulerEventService.class);
		context.startService(eventService);
	}

}
