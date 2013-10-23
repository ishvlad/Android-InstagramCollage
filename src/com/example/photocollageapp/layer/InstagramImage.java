package com.example.photocollageapp.layer;

import android.os.Parcel;
import android.os.Parcelable;


public class InstagramImage implements Parcelable {
	//rate = likes + comments
	public int rate;
	public String url;
	public boolean isSelected; 
	
	public InstagramImage(int rating, String URL) {
		rate = rating;
		url = URL;
		isSelected = false;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}

}
