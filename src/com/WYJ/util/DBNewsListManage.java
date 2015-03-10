package com.WYJ.util;

import java.util.ArrayList;
import java.util.List;

import com.WYJ.domain.News;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBNewsListManage {
	
	/**
	 * 向数据库加入news集合
	 * @param newss
	 * @param context
	 */
	public void addNewsList(List<News> newss,Context context){
		if(newss.size()>0){
			clearNewsList(context);
		}
		for(News news:newss){
			addNews(news, context);
		}
	}
	
	/**
	 * 向数据库中加入一条记录
	 * @param news  一个news
	 * @param context 
	 */
	public void addNews(News news,Context context){
		SQLiteDatabase sqLiteDatabase = LPDataBaseHelper.getInstance(context).getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("title", news.getTitle());
		contentValues.put("url", news.getUrl());
		contentValues.put("photoUrl", news.getPhotoUrl());
		contentValues.put("source", news.getSource());
		contentValues.put("date", news.getDate());
		if(sqLiteDatabase!=null){
			sqLiteDatabase.insert("newslist", null, contentValues);
			sqLiteDatabase.close();
		}
	}
	
	/**
	 * 取出news集合数据
	 * @param context
	 * @return
	 */
	public List<News> getNewss(Context context){
		List<News> newss = new ArrayList<News>();
		SQLiteDatabase sqLiteDatabase = LPDataBaseHelper.getInstance(context).getReadableDatabase();
		if(sqLiteDatabase!=null){
			Cursor cursor = sqLiteDatabase.query("newslist", new String[] { "title",
					"url", "photoUrl", "source" ,"date"}, null, null, null, null, null);
			while(cursor.moveToNext()){
				News news = new News();
				news.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				news.setUrl(cursor.getString(cursor.getColumnIndex("url")));
				news.setPhotoUrl(cursor.getString(cursor.getColumnIndex("photoUrl")));
				news.setSource(cursor.getString(cursor.getColumnIndex("source")));
				news.setDate(cursor.getString(cursor.getColumnIndex("date")));
				newss.add(news);
			}
			sqLiteDatabase.close();
		}
		return newss;
	}

	/**
	 * 清空数据
	 * @param context
	 */
	public void clearNewsList(Context context){
		SQLiteDatabase sqLiteDatabase=LPDataBaseHelper.getInstance(context).getWritableDatabase();
		if (sqLiteDatabase != null)  {
			sqLiteDatabase.delete("newslist", null, null);
		} 
		sqLiteDatabase.close();
	}
}
