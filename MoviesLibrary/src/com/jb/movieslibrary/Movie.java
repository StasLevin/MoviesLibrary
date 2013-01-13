package com.jb.movieslibrary;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable{
	private int _id;
	private String subject;
	private String body;
	private Uri uri;
	
	
	
	// default (empty) CTOR
	public Movie() {
		super();
	}
	
	// CTOR w/o _id
	public Movie(String subject, String body, Uri uri) {
		super();
		this.subject = subject;
		this.body = body;
		this.uri = uri;
	}

	// CTOR
	public Movie(int _id, String subject, String body, Uri uri) {
		super();
		this._id = _id;
		this.subject = subject;
		this.body = body;
		this.uri = uri;
	}

	//CCTOR from Parcel object
	public Movie(Parcel source) {
		readFromParcel(source);
	}

	// getters & setters
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getBody() {
		return body;
	}
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public Uri getUri() {
		return uri;
	}
	
	public void setUri(Uri uri) {
		this.uri = uri;
	}
	
	public int get_id() {
		return _id;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public String toString() {
		return subject;
	}

	public void readFromParcel(Parcel source) {
		_id = source.readInt();
		subject = source.readString();
		body = source.readString();
		uri = Uri.parse(source.readString());
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(get_id());
		dest.writeString(getSubject());
		dest.writeString(getBody());
		dest.writeString(getUri().toString());
	}
	
	public static Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

		@Override
		public Movie createFromParcel(Parcel source) {
			return new Movie(source);
		}

		@Override
		public Movie[] newArray(int size) {
			return new Movie[size];
		}
		
	};
	
}
