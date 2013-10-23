package com.example.photocollageapp.layer;

public class IntPair {
	private int mX;
	private int mY;
	
	public int getX(){
		return mX;
	}
	public int getY(){
		return mY;
	}
	
	public IntPair(int position,int mCountOfPhotos,int width, boolean hor) {
		int fx = 0, fy = 0;
		
		switch(mCountOfPhotos) {
		case 2:
			if (position == 1)  if (hor) fy = width/2; else fx = width/2;
			break;
		case 3:
			if (position == 1)	if (hor) fx = width/2; else fy = width/2;
			if (position == 2)	if (hor) fy = width/2; else fx = width/2;
			break;
		case 4:
			if (position == 1)	fx = width / 2;
			if (position == 2)  fy = width / 2;
			if (position == 3) { fx = width / 2; fy = width / 2; }
			break;
		}
		
		mX = fx;
		mY = fy;
	}
	
}
