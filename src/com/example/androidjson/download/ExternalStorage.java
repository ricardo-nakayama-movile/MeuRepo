package com.example.androidjson.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class ExternalStorage {

	private boolean mExternalStorageAvailable = false;
	private boolean mExternalStorageWriteable = false;
	private final String CLASS_TAG = ExternalStorage.class.getSimpleName();
	private static final int DEFAULT_TIMEOUT = 15;

	public ExternalStorage() {
		super();
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

	public Boolean write(Context context, String fileName, String value) {

		if (mExternalStorageWriteable) {
			
			try {
				Log.i(CLASS_TAG, "Writing External Storage");

				FileOutputStream fos = context.openFileOutput(fileName,
						Context.MODE_PRIVATE);
				fos.write(value.getBytes());
				fos.close();

				Log.i(CLASS_TAG, "OK");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			Log.w(CLASS_TAG, "mExternalStorageWriteable = "
					+ mExternalStorageWriteable);
			return false;
		}
		return true;
	}

	public Boolean writeDirectFromUrl(String downloadUrl, Context context,
			String fileName, String value) {

		if (mExternalStorageWriteable) {
			
			try {
				Log.i(CLASS_TAG, "Writing External Storage");

				FileOutputStream fos = context.openFileOutput(fileName,
						Context.MODE_PRIVATE);
				URL url;
				HttpURLConnection conn = null;
				BufferedReader br;

				url = new URL(downloadUrl);
				conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setDefaultUseCaches(false);
				conn.setInstanceFollowRedirects(true);
				conn.setConnectTimeout(DEFAULT_TIMEOUT * 1000);
				conn.setReadTimeout(DEFAULT_TIMEOUT * 1000);
				conn.setUseCaches(false);

				br = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), "iso-8859-1"));

				String line;

				while ((line = br.readLine()) != null) {
					fos.write(line.getBytes());
				}

				fos.close();
				br.close();

				Log.i(CLASS_TAG, "OK");
				
				return true;
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();	
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				if (e.getMessage().contains("403")) {
					System.out.println("[ERROR] 403 Forbidden");
				} else {
					Log.e("Erro ao conectar", e.getMessage());
					e.printStackTrace();
				}
			}
			
		} else {
			Log.w(CLASS_TAG, "mExternalStorageWriteable = "
					+ mExternalStorageWriteable);
		}
		return false;
	}

	public String read(Context context, String fileName) {

		if (mExternalStorageAvailable) {

			Log.i(CLASS_TAG, "Reading External Storage");

			StringBuilder sb = new StringBuilder();

			try {

				FileInputStream in = context.openFileInput(fileName);
				InputStreamReader inputStreamReader = new InputStreamReader(in);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					sb.append(line);
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			Log.i(CLASS_TAG, "Read: " + sb.toString());

			return sb.toString();

		} else {
			Log.w(CLASS_TAG, "mExternalStorageAvailable = "
					+ mExternalStorageAvailable);
			return null;
		}
	}

}