package com.ruicheng.farmingmanageclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

	public static final int VERSION = 1;

	public static final String SQL_NAME = "loginfo.db";

	public String loginfo = "create table loginfo (_id integer primary key autoincrement,dcId text,dcName text)";


	public MySQLiteOpenHelper(Context context) {
		super(context, SQL_NAME, null, VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(loginfo);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
}
