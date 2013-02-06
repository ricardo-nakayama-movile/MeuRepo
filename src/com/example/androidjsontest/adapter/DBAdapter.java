package com.example.androidjsontest.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.androidjsontest.bean.parcelable.ChannelContentsParcel;
import com.example.androidjsontest.db.DbHelper;

public class DBAdapter {
	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] allColumns = { DbHelper.ID, DbHelper.NAME,
			DbHelper.DESCRIPTION, DbHelper.TAG, DbHelper.ACCOUNTTYPE,
			DbHelper.EPISODEIMG, DbHelper.EPISODEIDIOS, DbHelper.DOWNLOADURL,
			DbHelper.INCLUSIONTIME, DbHelper.PUBLISHTIME };

	public DBAdapter(Context context) {
		dbHelper = new DbHelper(context);
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}

	/**
	 * 
	 * This method creates a new channelContent in the database
	 * 
	 * @param name
	 * @param description
	 * @param tag
	 * @param accountType
	 * @param episodeImg
	 * @param EpisodeIdiOS
	 * @param downloadUrl
	 * @param inclusionTime
	 * @param publishTime
	 * @return ChannelContentsParcel
	 * 
	 */
	public ChannelContentsParcel createChannelContents(String name,
			String description, String tag, String accountType,
			String episodeImg, String EpisodeIdiOS, String downloadUrl,
			String inclusionTime, String publishTime) {
		ContentValues values = new ContentValues();
		values.put(dbHelper.NAME, name);
		values.put(dbHelper.DESCRIPTION, description);
		values.put(dbHelper.TAG, tag);
		values.put(dbHelper.ACCOUNTTYPE, accountType);
		values.put(dbHelper.EPISODEIMG, episodeImg);
		values.put(dbHelper.EPISODEIDIOS, EpisodeIdiOS);
		values.put(dbHelper.DOWNLOADURL, downloadUrl);
		values.put(dbHelper.INCLUSIONTIME, inclusionTime);
		values.put(dbHelper.PUBLISHTIME, publishTime);

		long insertId = database.insert(dbHelper.TABLE_NAME, null, values);

		Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns,
				dbHelper.ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		return cursorToChannelContents(cursor);
	}

	/**
	 * 
	 * Removes a channelContent by it's ID
	 * 
	 * @param idChannelContent
	 * 
	 */
	public void removeChannelContent(int idChannelContent) {
		database.delete(DbHelper.TABLE_NAME, DbHelper.ID + " = "
				+ idChannelContent, null);
	}

	/**
	 * 
	 * Returns a channelContent using a cursor
	 * 
	 * @param cursor
	 * @return ChannelContentsParcel
	 * 
	 */
	private ChannelContentsParcel cursorToChannelContents(Cursor cursor) {
		ChannelContentsParcel channelContentsParcel = new ChannelContentsParcel(
				cursor.getLong(0), cursor.getString(1), cursor.getString(2),
				cursor.getString(3), cursor.getString(4), cursor.getString(5),
				cursor.getString(6), cursor.getString(7), cursor.getString(8),
				cursor.getString(9));
		return channelContentsParcel;
	}
	
	/**
	 * 
	 * Return all channelContents
	 * 
	 * @return cursor
	 * 
	 */

	public Cursor getChannelContents() {
		Cursor cursor = database
				.rawQuery(
						"select _id, name, description, tag, accountType, episodeImg, episodeIdiOS, downloadUrl, inclusionTime, publishTime from movies",
						null);
		return cursor;
	}

	/**
	 * 
	 * Returns a channelContent using the id of the channel
	 * 
	 * @param idChannelContent
	 * @return ChannelContentsParcel
	 * 
	 */
	public ChannelContentsParcel getSingleChannelContent(int idChannelContent) {
		Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns,
				dbHelper.ID + " = " + idChannelContent, null, null, null, null);
		cursor.moveToFirst();
		return cursorToChannelContents(cursor);
	}
}
