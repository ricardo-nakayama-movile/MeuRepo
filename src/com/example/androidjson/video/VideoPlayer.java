package com.example.androidjson.video;

import java.io.File;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.androidjsontest.R;

public class VideoPlayer extends Activity {
	private VideoView video;
	private MediaController ctlr;

	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);
		setContentView(R.layout.activity_main_list);

		File clip = new File(Environment.getExternalStorageDirectory(),
				"test.mp4");

		if (clip.exists()) {
			video = (VideoView) findViewById(R.id.video);
			video.setVideoPath(clip.getAbsolutePath());

			ctlr = new MediaController(this);
			ctlr.setMediaPlayer(video);
			video.setMediaController(ctlr);
			video.requestFocus();
			video.start();
		}
	}
}
