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
		public static final String CLN_ACCOUNT_CODE = "account_code";
		public static final String CLN_ACCOUNT_NAME = "account_name";
		public static final String CLN_FIRST_NAME = "first_name";
		public static final String CLN_LAST_NAME = "last_name";
		public static final String CLN_USER_NAME = "user_name";
		public static final String CLN_PASSWORD = "password";
		public static final String CLN_EMAIL = "email";
		public static final String CLN_RECOVERY_EMAIL = "recovery_email";
		public static final String CLN_SECURITY_QUESTION1 = "security_question1";
		public static final String CLN_SECURITY_QUESTION2 = "security_question2";
		public static final String CLN_SECURITY_ANSWER1 = "security_answer1";
		public static final String CLN_SECURITY_ANSWER2 = "security_answer2";
		public static final String CLN_REMARKS = "remarks";
		public static final String CLN_TIMESTAMP = "time_stamp";
	}
}
