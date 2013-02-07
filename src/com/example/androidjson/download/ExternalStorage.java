package com.example.androidjson.download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * @author marcosloiola
 *
 */
public class ExternalStorage {

	private boolean mExternalStorageAvailable = false;
	private boolean mExternalStorageWriteable = false;
	private final String CLASS_TAG = ExternalStorage.class.getSimpleName();
	
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
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
	}

	public void write(Context context, String fileName, String value) {

		if( mExternalStorageWriteable ){	
			try {

				Log.i(CLASS_TAG, "Writing External Storage");
				
				File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), fileName);
				FileOutputStream fos = context.openFileOutput(fileName,Context.MODE_PRIVATE);
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
			Log.w(CLASS_TAG,"mExternalStorageWriteable = " + mExternalStorageWriteable) ;
		}
	}

	public String read(Context context, String fileName) {

		if(mExternalStorageAvailable) {
		
			Log.i(CLASS_TAG, "Reading External Storage");
			
			StringBuilder sb = new StringBuilder();
			
			try {
				
				File file = new File(context.getExternalFilesDir(Environment.DIRECTORY_MOVIES), fileName);
				FileInputStream in = context.openFileInput(file.getPath());
			    InputStreamReader inputStreamReader = new InputStreamReader(in);
			    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			    
			    String line;
			    while ((line = bufferedReader.readLine()) != null) {
			        sb.append(line);
			    }
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Log.i(CLASS_TAG, "Read: " + sb.toString());
			
			return sb.toString();
			
		} else {
			Log.w(CLASS_TAG,"mExternalStorageAvailable = " + mExternalStorageAvailable) ;
			return null;
		}
	}
	
}