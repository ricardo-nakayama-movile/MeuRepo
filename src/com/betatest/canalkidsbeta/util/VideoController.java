package com.betatest.canalkidsbeta.util;

import java.io.File;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

import com.betatest.canalkidsbeta.download.ExternalStorage;
import com.betatest.canalkidsbeta.model.Movie;
import com.betatest.canalkidsbeta.tasks.VideoLoaderTask;

public class VideoController {

	private final String VIDEO_FORMAT_MP4 = ".mp4";

	// TODO I BELIEVE THIS SHOULD NOT BE HERE
	public Boolean videoDownload(Context context, String fileName,
			String downloadUrl) {

		ExternalStorage externalStorage = new ExternalStorage();

		if (externalStorage.writeMp4ToExternalStorageFromUrl(downloadUrl,
				context, fileName)) {
			Log.i("INFO", "DOWNLOAD VIDEO OK");
			return true;
		} else {
			Log.w("WARN", "DOWNLOAD VIDEO ERROR");
			return false;
		}
	}
	
	/**
	 * Remove a video from external storage
	 * 
	 * @param context
	 * @param fileName
	 * 
	 * @return Boolean
	 */
	public Boolean removeDownload(Context context, String fileName){
		ExternalStorage externalStorage = new ExternalStorage();
		
		if(externalStorage.deleteMovieExternalStorage(context, fileName))
			return true;
		return false;
	}

	/**
	 * Verify if video was already downloaded
	 * Parameter movieName must not have format appended
	 * 
	 * @param context
	 * @param movieName
	 * 
	 * @return Boolean
	 */
	public Boolean verifyExistsVideoMp4(Context context, String movieName) {
		movieName = movieName.concat(VIDEO_FORMAT_MP4);
		File file = new File(context.getExternalFilesDir(null), movieName);

		if (file.exists()) {
			Log.i("INFO", "VIDEO FOUND");
			return true;
		}
		Log.w("WARN", "VIDEO NOT FOUND");
		return false;
	}

	/**
	 * Choose whether a video should be played from stream or offline
	 * 
	 * @param context
	 * @param movie
	 * @param videoView
	 * 
	 * @return Void
	 */
	public Void loadVideo(Context context, Movie movie, VideoView videoView) {
		
		if (verifyExistsVideoMp4(context, movie.tag)) {
			String filePath = context.getExternalFilesDir(null).toString();
			String fileName = movie.tag.concat(VIDEO_FORMAT_MP4);
			filePath = filePath.concat("/" + fileName);
			
			playVideoOffline(context, filePath,	videoView);
		} else {
			// TODO STILL MISSING CHECK IF THERES CONNECTION OR NOT
			VideoLoaderTask videoLoaderTask = new VideoLoaderTask(context);
			videoLoaderTask.execute(movie);
			
			playVideoStream(context, movie.urlMovie, videoView);
		}
		return null;
	}

	/**
	 * Play an already downloaded video
	 * 
	 * @param context
	 * @param filePath
	 * @param videoView
	 * 
	 * @return Void
	 */
	public Void playVideoOffline(Context context, String filePath,
			VideoView videoView) {

		Log.i("INFO", "File path to play video offline: " + filePath);

		videoView.setVideoPath(filePath);

		MediaController mediaController = new MediaController(context);
		mediaController.setMediaPlayer(videoView);

		videoView.setMediaController(mediaController);
		videoView.requestFocus();
		videoView.start();

		Log.i("INFO", "PLAYING VIDEO OFFLINE");

		videoView.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer arg0) {
				// TODO RETURN TO LAST SCREEN
			}
		});

		return null;
	}

	/**
	 * Play a video streaming
	 * 
	 * @param context
	 * @param url
	 * @param videoView
	 * 
	 * @return Void
	 */
	public Void playVideoStream(Context context, String url, VideoView videoView) {

		MediaController mediaController = new MediaController(context);
		mediaController.setAnchorView(videoView);

		Uri video = Uri.parse(url);
		Log.e("Uri video", video.toString());
		videoView.setMediaController(mediaController);
		videoView.setVideoURI(video);
		videoView.start();
		Log.i("INFO", "PLAYING VIDEO STREAM");
		videoView.setOnCompletionListener(new OnCompletionListener() {

			public void onCompletion(MediaPlayer arg0) {
				// TODO RETURN TO LAST SCREEN

			}
		});

		return null;
	}
}
