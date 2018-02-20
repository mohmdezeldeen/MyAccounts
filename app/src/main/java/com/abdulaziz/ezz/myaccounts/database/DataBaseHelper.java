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
				"CREATE TABLE " + TBL_ACCOUNT + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CLN_ACCOUNT_CODE + " TEXT NOT NULL, " + CLN_ACCOUNT_NAME
						+ " TEXT NOT NULL, " + CLN_USER_NAME + " TEXT NOT NULL, " + CLN_PASSWORD + " TEXT NOT NULL, " + CLN_EMAIL + " TEXT NOT NULL, "
						+ CLN_FIRST_NAME + " TEXT, " + CLN_LAST_NAME + " TEXT, " + CLN_SECURITY_QUESTION1 + " TEXT, " + CLN_SECURITY_ANSWER1
						+ " TEXT, " + CLN_SECURITY_QUESTION2 + " TEXT, " + CLN_SECURITY_ANSWER2 + " TEXT, " + CLN_RECOVERY_EMAIL + " TEXT, "
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
