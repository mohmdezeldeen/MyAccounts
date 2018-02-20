package com.abdulaziz.ezz.myaccounts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.abdulaziz.ezz.myaccounts.database.DatabaseContract.DataBaseEntry.*;

/**
 * Created by EZZ on 2018-02-11.
 */

public class DataBaseHelper extends SQLiteOpenHelper
{

	private static final String DATABASE_NAME = "myaccounts.db";
	private static final int DATABASE_VERSION = 1;

	public DataBaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		final String CREATE_ACCOUNT_TABLE =
				"CREATE TABLE " + TBL_ACCOUNT + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CLN_CODE + " TEXT NOT NULL, " + CLN_NAME1
						+ " TEXT NOT NULL, " + CLN_NAME2 + " TEXT, " + CLN_USER + " TEXT, " + CLN_PASSWORD + " TEXT, " + CLN_EMAIL + " TEXT, "
						+ CLN_REMARKS + " TEXT, " + CLN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" + "); ";
		db.execSQL(CREATE_ACCOUNT_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + TBL_ACCOUNT);
		onCreate(db);
	}
}
