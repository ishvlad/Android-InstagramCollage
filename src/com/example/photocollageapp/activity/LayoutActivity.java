package com.example.photocollageapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.example.photocollageapp.R;
import com.example.photocollageapp.adapter.LayoutAdapter;
import com.example.photocollageapp.layer.LayoutCollage;

public class LayoutActivity extends Activity {
	public static final String TAG = "LAYOUT";
	public static final String EXTRA_AUTHOR_ID = TAG + "_authorID";

	private Long mUserId;
	private Intent stepThreeIntent;
	private static final LayoutCollage[] mList = {
			new LayoutCollage(R.drawable.photo2v, 2, true),
			new LayoutCollage(R.drawable.photo2h, 2, false),
			new LayoutCollage(R.drawable.photo3l, 3, false),
			new LayoutCollage(R.drawable.photo3u, 3, true),
			new LayoutCollage(R.drawable.photo4, 4, true),
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		
		TextView stepView = (TextView) findViewById(R.id.title_step2);
		stepView.setText(getString(R.string.pattern_title_step, "2"));
		
		if (savedInstanceState == null) {
			mUserId = getIntent().getExtras().getLong(EXTRA_AUTHOR_ID);
		} else {
			mUserId = savedInstanceState.getLong(EXTRA_AUTHOR_ID);
		}
		
		GridView gridView = (GridView) findViewById(R.id.collage_list);
		gridView.setAdapter(new LayoutAdapter(this, mList));

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position,
					long id) {
				clickLayout(position);
			}
		});
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putLong(EXTRA_AUTHOR_ID, mUserId);
		super.onSaveInstanceState(outState);
	}
	
	public void clickLayout(int position) {
		stepThreeIntent = new Intent(this, GalleryActivity.class);
		
		Bundle bundle = new Bundle();
		bundle.putLong(GalleryActivity.EXTRA_AUTHOR_ID, mUserId);
		bundle.putSerializable(GalleryActivity.EXTRA_LAYOUT, mList[position]);
		stepThreeIntent.putExtras(bundle);
		
		startActivity(stepThreeIntent);
	}
	
	
}
