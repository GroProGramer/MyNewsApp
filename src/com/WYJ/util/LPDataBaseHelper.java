package com.WYJ.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LPDataBaseHelper extends SQLiteOpenHelper{
	
	private final static String DATABASE_NAME = "newsList"; 
	private static LPDataBaseHelper lpDataBaseHelper;
	
	public static LPDataBaseHelper getInstance(Context context){
		if(lpDataBaseHelper==null){
			lpDataBaseHelper = new LPDataBaseHelper(context);
		}
		return lpDataBaseHelper;
	}
	
	private LPDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists newslist (id INTEGER PRIMARY KEY AUTOINCREMENT,title varchar(100),url varchar(100)" +
			",photoUrl varchar(100),source varchar(50),date varchar(50))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

}
