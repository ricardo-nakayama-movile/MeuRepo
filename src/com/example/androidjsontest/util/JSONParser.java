package com.example.androidjsontest.util;

import java.io.IOException;

import android.util.Log;

import com.example.androidjsontest.bean.parcelable.ChannelContentsResponseParcel;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONParser {

	/**
	 * 
	 * Gets a content from a url
	 * 
	 * @param url
	 * @return String
	 * 
	 */
	public static String getUrlResponse(String url) {

		String response = "";

		try {
			response = HttpUtil.doHttpGet(url);
		} catch (Exception e) {
			Log.e("INFO", "erro ao conectar com servidor");
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * 
	 * Get a ChannelContentsResponse from the json
	 * 
	 * @param json
	 * @return ChannelContentsResponseParcel
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * 
	 */
	public static ChannelContentsResponseParcel getChannelContentsObjFromJson(
			String json) throws JsonParseException, JsonMappingException,
			IOException {

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.readValue(json,
				new TypeReference<ChannelContentsResponseParcel>() {
				});
	}
}