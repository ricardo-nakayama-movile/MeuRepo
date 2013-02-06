package com.example.androidjsontest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "movies.db";

	public static final String TABLE_NAME = "movies";

	public static final String ID = "_id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String TAG = "tag";
	public static final String ACCOUNTTYPE = "accountType";
	public static final String EPISODEIMG = "episodeImg";
	public static final String EPISODEIDIOS = "episodeIdiOS";
	public static final String DOWNLOADURL = "downloadUrl";
	public static final String INCLUSIONTIME = "inclusionTime";
	public static final String PUBLISHTIME = "publishTime";

	private static final String DATABASE_CREATE = "create table " + TABLE_NAME
			+ "( " + ID + " integer primary key autoincrement, " + NAME
			+ " text not null, " + DESCRIPTION + " text not null, " + TAG
			+ " text not null, " + ACCOUNTTYPE + " text not null, "
			+ EPISODEIMG + " text not null, " + EPISODEIDIOS
			+ " text not null, " + DOWNLOADURL + " text not null, "
			+ INCLUSIONTIME + " text not null, " + PUBLISHTIME
			+ " text not null " + ");";

	public DbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(DbHelper.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");

		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
	}

}
