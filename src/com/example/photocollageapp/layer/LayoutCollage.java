package com.example.photocollageapp.layer;
import java.io.Serializable;

import android.graphics.Bitmap;


public class LayoutCollage implements Serializable {
	private static final long serialVersionUID = 1L;
	private int mDrawable;
	private int mCountOfPhotos;
	private boolean isHorisontal;
	
	public LayoutCollage(int drawable, int count, boolean isHor){
		mDrawable = drawable;
		mCountOfPhotos = count;
		isHorisontal = isHor;
	}
	
	public int getDrawable(){
		return mDrawable;
	}
	
	public int getCount(){
		return mCountOfPhotos;
	}
	
	public IntPair getCoords(int position, int width) {
		return new IntPair(position, mCountOfPhotos, width, isHorisontal);
	}
	
	public Bitmap getScaledBitmap(Bitmap bmp, int position, int width) {
		return mCountOfPhotos == 4 || (mCountOfPhotos == 3 && position != 2) ? Bitmap.createScaledBitmap(bmp, width/2, width/2, false) :
				mCountOfPhotos == 3  && position == 2 ? isHorisontal ?  Bitmap.createScaledBitmap(bmp, width, width/2, false) :
																		Bitmap.createScaledBitmap(bmp, width/2, width, false) :
					mCountOfPhotos != 1 ? isHorisontal ?    Bitmap.createScaledBitmap(bmp, width, width/2, false) :
															Bitmap.createScaledBitmap(bmp, width/2, width, false) :
						Bitmap.createScaledBitmap(bmp, width, width, false);
	}
	
}
