package com.betatest.canalkidsbeta.tasks;

import java.io.IOException;
import java.util.Iterator;

import android.content.Context;
import android.database.SQLException;
import android.os.AsyncTask;

import com.betatest.canalkidsbeta.adapter.DBAdapter;
import com.betatest.canalkidsbeta.bean.ChannelContentsParcel;
import com.betatest.canalkidsbeta.bean.ChannelContentsResponseParcel;
import com.betatest.canalkidsbeta.interfaces.AsyncTaskInterface;
import com.betatest.canalkidsbeta.util.JSONParser;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class PojoLoaderTask extends
		AsyncTask<String, Void, ChannelContentsResponseParcel> {

	private DBAdapter datasource;
	private Context context;
	private ChannelContentsParcel channelContentsParcel;
	private ChannelContentsResponseParcel channelContentsResponseParcel;
	public AsyncTaskInterface delegate = null;

	public PojoLoaderTask(Context context) {
		this.context = context.getApplicationContext();
	}

	// TODO STILL GETTING FROM URL, WHAT HAPPENS IF THERE'S NO CONNECTION?
	@Override
	protected ChannelContentsResponseParcel doInBackground(String... params) {

		String channelContentsUrl = params[0];
		String response = JSONParser.getUrlResponse(channelContentsUrl);

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

		// Writing the channel contents response in the sqlite
		try {
			synchronized (context) {
				datasource = new DBAdapter(context);
				datasource.open();

				Iterator<ChannelContentsParcel> it = channelContentsResponseParcel
						.getContents().iterator();

				while (it.hasNext()) {
					channelContentsParcel = it.next();
					datasource.createOrUpdate(
							channelContentsParcel.getName(),
							channelContentsParcel.getDescription(),
							channelContentsParcel.getTag(),
							channelContentsParcel.getAccountType(),
							channelContentsParcel.getEpisodeImg(),
							channelContentsParcel.getEpisodeIdiOS(),
							channelContentsParcel.getDownloadUrl(),
							channelContentsParcel.getInclusionTime(),
							channelContentsParcel.getPublishTime());
				}
				datasource.close();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return channelContentsResponseParcel;
	}

	@Override
	protected void onPostExecute(ChannelContentsResponseParcel result) {
		delegate.processFinish(result);
	}
}
