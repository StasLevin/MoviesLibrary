package com.jb.movieslibrary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MoviesDBHelper extends SQLiteOpenHelper {

	private static MoviesDBHelper instance = null;
	
	private MoviesDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	public static MoviesDBHelper getInstance(Context context) {
		if (instance == null) {
			instance = new MoviesDBHelper(context, MoviesConstants.MOVIES_DB, null, MoviesConstants.MOVIES_DB_VERSION);
		}
		
		return instance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(MoviesConstants.LOG_TAG, "Creating DB Tables");
		
		try {
			db.execSQL("Create table if not exists " + MoviesConstants.MOVIES_TABLE + "(" +
					MoviesConstants.MOVIE_ID + " Integer primary key autoincrement, " + MoviesConstants.MOVIE_SUBJECT + 
					" Text, " + MoviesConstants.MOVIE_BODY + " text, " + MoviesConstants.MOVIE_URI + " text)");
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, e.getMessage() + " " + e.getStackTrace());
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.d(MoviesConstants.LOG_TAG, "Upgrading DB from version " + oldVersion +
				" to " + newVersion);
		try {
			db.execSQL("Drop table if exists " + MoviesConstants.MOVIES_TABLE);
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, e.getMessage() + " " + e.getStackTrace());
		}
	}

}
