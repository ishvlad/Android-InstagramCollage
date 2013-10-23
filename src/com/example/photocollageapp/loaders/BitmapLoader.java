package com.example.photocollageapp.loaders;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public class BitmapLoader extends AsyncTask<String, Void, Bitmap> {
	final public String TAG = "BitmapLoader"; 

	@Override
	protected Bitmap doInBackground(String... arg0) {
		return ImageLoader.downloadImage(arg0[0]);
	}
}
