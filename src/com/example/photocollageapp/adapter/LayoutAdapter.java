package com.example.photocollageapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.photocollageapp.R;
import com.example.photocollageapp.layer.LayoutCollage;

public class LayoutAdapter extends BaseAdapter {		
	private Context mContext;
	private LayoutCollage[] mList;

	public LayoutAdapter(Context ctx, LayoutCollage[] list) {
		mContext = ctx;
		mList = list;
	}

	@Override
	public int getCount() {
		return mList.length;
	}

	@Override
	public Object getItem(int position) {
		return mList[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(mList[position].getDrawable());
		
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

		final int imgSize = (int) mContext.getResources().getDimension(R.dimen.img_size_small);
		imageView.setLayoutParams(new GridView.LayoutParams(imgSize, imgSize));

		return imageView;
	}

}
