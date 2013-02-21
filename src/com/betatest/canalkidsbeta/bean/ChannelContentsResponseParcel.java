package com.betatest.canalkidsbeta.bean;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class ChannelContentsResponseParcel implements Parcelable {

	private String categoryDescription;
	private String categoryID;
	private String categoryName;
	private List<ChannelContentsParcel> contents;

	public ChannelContentsResponseParcel() {
	}

	/**
	 * 
	 * Constructor to use when re-constructing object from a parcel
	 * 
	 * @param in
	 *            a parcel from which to read this object
	 *            
	 */
	public ChannelContentsResponseParcel(Parcel in) {
		readFromParcel(in);
	}

	public List<ChannelContentsParcel> getContents() {
		return contents;
	}

	public void setContents(List<ChannelContentsParcel> contents) {
		this.contents = contents;
	}
	
	public int contentsSize(){
		return contents.size();
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(categoryDescription);
		dest.writeString(categoryID);
		dest.writeString(categoryName);
		dest.writeTypedList(contents);
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
		categoryDescription = in.readString();
		categoryID = in.readString();
		categoryName = in.readString();
		if (contents == null) {
			contents = new ArrayList<ChannelContentsParcel>();
		}
		in.readTypedList(contents, ChannelContentsParcel.CREATOR);
	}

	/**
	 * 
	 * This field is needed for Android to be able to create new objects,
	 * individually or as arrays.
	 * 
	 */
	public static final Parcelable.Creator<ChannelContentsResponseParcel> CREATOR = new Parcelable.Creator<ChannelContentsResponseParcel>() {
		public ChannelContentsResponseParcel createFromParcel(Parcel in) {
			return new ChannelContentsResponseParcel(in);
		}

		public ChannelContentsResponseParcel[] newArray(int size) {
			return new ChannelContentsResponseParcel[size];
		}
	};

}
