package com.jb.movieslibrary;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MoviesDBHandler {
	private MoviesDBHelper dbHelper;
	
	public MoviesDBHandler(Context context) {
		dbHelper = MoviesDBHelper.getInstance(context);
	}
	
	/**
	 * 
	 * @return names of all movies from DB
	 */
	public ArrayList<String> getAllMoviesNames() {
		ArrayList<String> names = new ArrayList<String>();
		final String query = "Select * from " + MoviesConstants.MOVIES_TABLE; 
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Log.i(MoviesConstants.LOG_TAG, "Get names of all movies from DB");
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			cursor.moveToFirst();
			do {
				names.add(cursor.getString(cursor.getColumnIndex(MoviesConstants.MOVIE_SUBJECT)));
			} while (cursor.moveToNext());
			
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, "SQL exception: " + e.getMessage() + " " + e.getStackTrace());
		} finally {
			if (db.isOpen())
				db.close();
		}
		return names;
	}
		
	/**
	 * 
	 * @return all movies from DB
	 */
	public ArrayList<Movie> getAllMovies() {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		final String query = "Select * from " + MoviesConstants.MOVIES_TABLE; 
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		
		Log.i(MoviesConstants.LOG_TAG, "Get names of all movies from DB");
		Cursor cursor = db.rawQuery(query, null);
		
		try {
			cursor.moveToFirst();
			do {
				movies.add(new Movie (cursor.getInt(cursor.getColumnIndex(MoviesConstants.MOVIE_ID)),
						cursor.getString(cursor.getColumnIndex(MoviesConstants.MOVIE_SUBJECT)),
						cursor.getString(cursor.getColumnIndex(MoviesConstants.MOVIE_BODY)),
						Uri.parse(cursor.getString(cursor.getColumnIndex(MoviesConstants.MOVIE_URI)))));
			} while (cursor.moveToNext());
			
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, "SQL exception: " + e.getMessage() + " " + e.getStackTrace());
		} finally {
			if (db.isOpen())
				db.close();
		}
		return movies;
	}
	
	public boolean addMovie (Movie movie) {
		boolean result = false;
		Log.i(MoviesConstants.LOG_TAG, "Insert " + movie.toString() + " to DB");
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("subject", movie.getSubject());
			values.put("body", movie.getBody());
			values.put("uri", movie.getUri().toString());
			result = db.insert(MoviesConstants.MOVIES_TABLE, null, values) > 0;	
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, "SQL exception: " + e.getMessage() + e.getStackTrace());
		} finally {
			if (db.isOpen())
				db.close();
		}
		return result;
	}
	
	public boolean updateMovie (Movie movie) {
		boolean result = false;
		Log.i(MoviesConstants.LOG_TAG, "Update existing move: " + movie.toString());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			ContentValues values = new ContentValues();
			values.put("subject", movie.getSubject());
			values.put("body", movie.getBody());
			values.put("uri", movie.getUri().toString());
			result = db.update(MoviesConstants.MOVIES_TABLE, values, "_id = " + movie.get_id(), null) > 0;	
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, "SQL exception: " + e.getMessage() + e.getStackTrace());
		} finally {
			if (db.isOpen())
				db.close();
		}
		return result;
	}
	
	public boolean deleteMovie (Movie movie) {
		boolean result = false;
		Log.i(MoviesConstants.LOG_TAG, "Update existing move: " + movie.toString());
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			result = db.delete(MoviesConstants.MOVIES_TABLE, "_id = " + movie.get_id(), null) > 0;	
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, "SQL exception: " + e.getMessage() + e.getStackTrace());
		} finally {
			if (db.isOpen())
				db.close();
		}
		return result;
	}
	
	public boolean removeAllMovies() {
		boolean result = false;
		Log.i(MoviesConstants.LOG_TAG, "Removing all objects from " + MoviesConstants.MOVIES_TABLE);
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		
		try {
			result = db.delete(MoviesConstants.MOVIES_TABLE, null, null) > 0;
//			db.execSQL("Delete From " + MoviesConstants.MOVIES_TABLE);
//			result = true;
		} catch (Exception e) {
			Log.e(MoviesConstants.LOG_TAG, e.getMessage() + " " + e.getStackTrace());
		} finally {
			if (db.isOpen())
				db.close();
		}
		return result;
	}
	

}
