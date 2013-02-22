package com.betatest.canalkidsbeta.alarm;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SchedulerSetupReceiver extends BroadcastReceiver {

	private static final String APP_TAG = "com.betatest.canalkidsbeta";

	//TODO ADJUST TIME FOR ALARM
	//TODO CALL ALARM WHEN APP STARTS
	private static final int EXEC_INTERVAL = 60 * 1000;

	@Override
	public void onReceive(final Context context, final Intent intent) {

		Log.d(APP_TAG, "FIRST BROADCAST RECEIVED");

		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);

		Intent intentReceiver = new Intent(context,
				SchedulerEventReceiver.class);
		PendingIntent intentExecuted = PendingIntent.getBroadcast(context, 0,
				intentReceiver, PendingIntent.FLAG_NO_CREATE);

		if (intentExecuted == null) {
			Log.d(APP_TAG, "SETTING ALARM");
			intentExecuted = PendingIntent.getBroadcast(context, 0,
					intentReceiver, PendingIntent.FLAG_CANCEL_CURRENT);

			Calendar now = Calendar.getInstance();
			now.add(Calendar.SECOND, 60);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					now.getTimeInMillis(), EXEC_INTERVAL, intentExecuted);
		} else {
			Log.d(APP_TAG, "ALARM IS ALREADY SET");
		}
	}

}
