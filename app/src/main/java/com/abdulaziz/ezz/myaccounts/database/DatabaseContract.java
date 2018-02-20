package com.abdulaziz.ezz.myaccounts.database;


import android.provider.BaseColumns;

/**
 * Created by EZZ on 2018-02-11.
 */

public class DatabaseContract
{

	public static class DataBaseEntry implements BaseColumns
	{
		public static final String TBL_ACCOUNT = "my_accounts";
		public static final String CLN_CODE = "code";
		public static final String CLN_NAME1 = "name1";
		public static final String CLN_NAME2 = "name2";
		public static final String CLN_USER = "user";
		public static final String CLN_PASSWORD = "password";
		public static final String CLN_EMAIL = "email";
		public static final String CLN_REMARKS = "remarks";
		public static final String CLN_TIMESTAMP = "timestamp";
	}
}
