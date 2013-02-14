package com.example.androidjson.video;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends Activity{
	
	public void videoPlayers(String path, String fileName, boolean autoplay){
	    //get current window information, and set format, set it up differently, if you need some special effects
	    getWindow().setFormat(PixelFormat.TRANSLUCENT);
	    //the VideoView will hold the video
	    VideoView videoHolder = new VideoView(this);
	    //MediaController is the ui control howering above the video (just like in the default youtube player).
	    videoHolder.setMediaController(new MediaController(this));
	    //assing a video file to the video holder
	    videoHolder.setVideoURI(Uri.parse(path+"/"+fileName));
	    //get focus, before playing the video.
	    videoHolder.requestFocus();
	    if(autoplay){
	        videoHolder.start();
	    }
	}
}
