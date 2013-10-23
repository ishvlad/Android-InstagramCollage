package com.example.photocollageapp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.photocollageapp.R;
import com.example.photocollageapp.layer.InstagramImage;
import com.example.photocollageapp.loaders.ImageLoader;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<InstagramImage> mList;
	
	public Context getContext() {
		return mContext;
	}
	
	public ImageAdapter(Context ctx, ArrayList<InstagramImage> list) {
		mContext = ctx;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		ImageLoader.fetchImage(mList.get(position).url, imageView);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		
		final int imgSize = (int) mContext.getResources().getDimension(R.dimen.img_size_small);
		imageView.setLayoutParams(new GridView.LayoutParams(imgSize, imgSize));
		
		final int dimen = mList.get(position).isSelected ?  (int) mContext.getResources().getDimension(R.dimen.step_1_2) :
															(int) mContext.getResources().getDimension(R.dimen.step_0);
		imageView.setPadding(dimen, dimen, dimen, dimen);
		
		return imageView;
	}
	
}