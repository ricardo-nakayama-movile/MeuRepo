package com.example.androidjsontest.util;

import android.content.Context;
import android.util.Log;

import com.example.androidjson.download.ExternalStorage;

public class VideoController {

	public Boolean videoDownload(Context context, String fileName,
			String downloadUrl) {

		ExternalStorage externalStorage = new ExternalStorage();
		String response = "";


		if (externalStorage.writeDirectFromUrl(downloadUrl, context, fileName, response)) {
			Log.i("INFO", "OK");
			return true;
		} else {
			Log.w("WARN", "ERRO");
			return false;
		}
	}

	public Void playVideoOffline() {
		return null;
	}

	public Void playVideoStream(String url) {
		return null;
	}
}
