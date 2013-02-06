package com.example.androidjsontest.bean.parcelable;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class ChannelContentsParcel implements Parcelable {

	private Long _id;
	private String name;
	private String description;
	private String tag;
	private String accountType;
	private String episodeImg;
	private String episodeIdiOS;
	private String downloadUrl;
	private String inclusionTime;
	private String publishTime;

	public ChannelContentsParcel(String name, String description, String tag,
			String accountType, String episodeImg, String episodeIdiOS,
			String downloadUrl, String inclusionTime, String publishTime) {

		this.name = name;
		this.description = description;
		this.tag = tag;
		this.accountType = accountType;
		this.episodeImg = episodeImg;
		this.episodeIdiOS = episodeIdiOS;
		this.downloadUrl = downloadUrl;
		this.inclusionTime = inclusionTime;
		this.publishTime = publishTime;
	}

	public ChannelContentsParcel(Long id, String name, String description,
			String tag, String accountType, String episodeImg,
			String episodeIdiOS, String downloadUrl, String inclusionTime,
			String publishTime) {

		this._id = id;
		this.name = name;
		this.description = description;
		this.tag = tag;
		this.accountType = accountType;
		this.episodeImg = episodeImg;
		this.episodeIdiOS = episodeIdiOS;
		this.downloadUrl = downloadUrl;
		this.inclusionTime = inclusionTime;
		this.publishTime = publishTime;
	}

	/**
	 * 
	 * Standard basic constructor for non-parcel object creation
	 * 
	 */
	public ChannelContentsParcel() {
	}

	/**
	 * 
	 * Constructor to use when re-constructing object from a parcel
	 * 
	 * @param in
	 *            a parcel from which to read this object
	 * 
	 */
	public ChannelContentsParcel(Parcel in) {
		readFromParcel(in);
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return _id
	 * 
	 */
	public Long getId() {
		return _id;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param id
	 * 
	 */
	public void setId(Long id) {
		this._id = id;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return name
	 * 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return description
	 * 
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param description
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return tag
	 * 
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param tag
	 * 
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return accountType
	 * 
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param accountType
	 * 
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return episodeImg
	 * 
	 */
	public String getEpisodeImg() {
		return episodeImg;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param episodeImg
	 * 
	 */
	public void setEpisodeImg(String episodeImg) {
		this.episodeImg = episodeImg;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return episodeIdiOS
	 * 
	 */
	public String getEpisodeIdiOS() {
		return episodeIdiOS;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param episodeIdiOS
	 * 
	 */
	public void setEpisodeIdiOS(String episodeIdiOS) {
		this.episodeIdiOS = episodeIdiOS;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return downloadUrl
	 * 
	 */
	public String getDownloadUrl() {
		return downloadUrl;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param downloadUrl
	 * 
	 */
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return inclusionTime
	 * 
	 */
	public String getInclusionTime() {
		return inclusionTime;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param inclusionTime
	 * 
	 */
	public void setInclusionTime(String inclusionTime) {
		this.inclusionTime = inclusionTime;
	}

	/**
	 * 
	 * Getter
	 * 
	 * @return publishTime
	 * 
	 */
	public String getPublishTime() {
		return publishTime;
	}

	/**
	 * 
	 * Setter
	 * 
	 * @param publishTime
	 * 
	 */
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		/*
		 * 
		 * We just need to write each field into the parcel. When we read from
		 * parcel, they will come back in the same order
		 */
		Log.i("INFO", "writeToParcel..." + flags);
		dest.writeString(name);
		dest.writeString(description);
		dest.writeString(tag);
		dest.writeString(accountType);
		dest.writeString(episodeImg);
		dest.writeString(episodeIdiOS);
		dest.writeString(downloadUrl);
		dest.writeString(inclusionTime);
		dest.writeString(publishTime);
	}

	/**
	 * 
	 * Called from the constructor to create this object from a parcel.
	 * 
	 * @param in
	 *            parcel from which to re-create object
	 * 
	 */
	private void readFromParcel(Parcel in) {
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		name = in.readString();
		description = in.readString();
		tag = in.readString();
		accountType = in.readString();
		episodeImg = in.readString();
		episodeIdiOS = in.readString();
		downloadUrl = in.readString();
		inclusionTime = in.readString();
		publishTime = in.readString();
	}

	/**
	 * 
	 * This field is needed for Android to be able to create new objects,
	 * individually or as arrays.
	 * 
	 */
	public static final Parcelable.Creator<ChannelContentsParcel> CREATOR = new Parcelable.Creator<ChannelContentsParcel>() {
		public ChannelContentsParcel createFromParcel(Parcel in) {
			return new ChannelContentsParcel(in);
		}

		public ChannelContentsParcel[] newArray(int size) {
			return new ChannelContentsParcel[size];
		}
	};

}
