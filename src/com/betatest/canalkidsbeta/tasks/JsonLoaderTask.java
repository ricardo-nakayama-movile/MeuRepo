package com.betatest.canalkidsbeta.tasks;

import java.io.IOException;

import android.os.AsyncTask;

import com.betatest.canalkidsbeta.bean.ChannelContentsResponseParcel;
import com.betatest.canalkidsbeta.interfaces.AsyncTaskInterface;
import com.betatest.canalkidsbeta.util.JSONParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class JsonLoaderTask extends
		AsyncTask<String, Void, ChannelContentsResponseParcel> {

	public AsyncTaskInterface delegate = null;

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected ChannelContentsResponseParcel doInBackground(String... params) {

		ChannelContentsResponseParcel channelContentsResponseParcel = null;

		String response = JSONParser.getUrlResponse(params[0]);

		try {
			channelContentsResponseParcel = JSONParser
					.getChannelContentsObjFromJson(response);
		} catch (JsonParseException e) {
			e.printStackTrace();
			return null;

		} catch (JsonMappingException e) {
			e.printStackTrace();
			return null;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return channelContentsResponseParcel;
	}

	protected void onProgressUpdate(Integer... progress) {
	}

	protected void onPostExecute(ChannelContentsResponseParcel result) {
		delegate.processFinish(result);
	}

}