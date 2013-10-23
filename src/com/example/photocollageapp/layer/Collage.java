package com.example.photocollageapp.layer;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.photocollageapp.R;
import com.example.photocollageapp.loaders.BitmapLoader;

public class Collage extends RelativeLayout {
	private Canvas mCollage;
	private Context mContext;
	
	public Canvas getCollage(){
		return mCollage;
	}
	
	public Collage(Context aContext, Context context) {
		super(aContext);
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.collage_layout, this);
		
	}
	
	public Bitmap setCollage(ArrayList<InstagramImage> list, LayoutCollage layout) {
		ProgressDialog mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setMessage(mContext.getText(R.string.title_generate));
		mProgressDialog.setIndeterminate(true);
		mProgressDialog.show();
		
		ImageView imgView = (ImageView) findViewById(R.id.item_photoview) ; 
		int width =  (int)(mContext.getResources().getDimension(R.dimen.img_size_big));
		imgView.setLayoutParams(new LayoutParams(width, width));
		
		ArrayList<BitmapLoader> mTasks = new ArrayList<BitmapLoader>(layout.getCount());
		for(int i = 0; i < layout.getCount(); i++) {
			BitmapLoader bmpl = new BitmapLoader();
			bmpl.execute(list.get(i).url);
			mTasks.add(bmpl);
		}
		
		Bitmap resultBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);;
		Canvas cvs = new Canvas(resultBitmap);
		try {
			for(int i = 0; i < layout.getCount(); i++) {
				cvs.drawBitmap(
						layout.getScaledBitmap(mTasks.get(i).get(), i, width), 
						layout.getCoords(i, width).getX(), 
						layout.getCoords(i, width).getY(), 
						null
				);
			}
		} catch (InterruptedException e) {} catch (ExecutionException e) {}
		
		mProgressDialog.dismiss();
		
		imgView.setImageBitmap(resultBitmap);
		return resultBitmap;
	}
	
}
