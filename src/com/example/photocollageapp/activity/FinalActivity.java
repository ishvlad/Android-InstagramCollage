package com.example.photocollageapp.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.example.photocollageapp.R;
import com.example.photocollageapp.layer.Collage;
import com.example.photocollageapp.layer.InstagramImage;
import com.example.photocollageapp.layer.LayoutCollage;

public class FinalActivity extends Activity implements OnClickListener {
	public static final String TAG = "FINAL";
	
    public static final String EXTRA_LAYOUT = TAG + "_layout";

    private LayoutCollage mLayout;
    private ArrayList<InstagramImage> mList;
    private Bitmap mCollage;
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.final_collage);
		
		if (savedInstanceState == null) {
			mLayout = (LayoutCollage) getIntent().getExtras().getSerializable(EXTRA_LAYOUT);
		} else {
			mLayout = (LayoutCollage) savedInstanceState.getSerializable(EXTRA_LAYOUT);
		}
        mList = GalleryActivity.mResult;
		
		LinearLayout collageLayout = (LinearLayout) findViewById(R.id.collage_layout);
		Collage frame = new Collage(getApplicationContext(), this);
		mCollage = frame.setCollage(mList, mLayout);
		collageLayout.addView(frame);
		
		findViewById(R.id.button_share).setOnClickListener(this);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putSerializable(EXTRA_LAYOUT, mLayout);
		super.onSaveInstanceState(outState);
	}

	private String saveCollage() {
		final String path = Environment.getExternalStorageDirectory().toString();
		File file = new File(	path, 
								getString(	R.string.pattern_save_collage_name, 
											Long.toString(Calendar.getInstance().getTimeInMillis())
										 )
							);
		try {			 
			FileOutputStream fOS = new FileOutputStream(file);
			mCollage.compress(CompressFormat.JPEG, 85, fOS);
		
			fOS.flush();
			fOS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			MediaStore.Images.Media.insertImage(getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return file.getAbsolutePath();
	}
	
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.button_share:
			final Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("message/partial");
			
			emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {});
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Collage");
			emailIntent.putExtra(Intent.EXTRA_TEXT,"");
			
			emailIntent.setType("image/jpeg");
			emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(saveCollage()));
			
			startActivity(Intent.createChooser(emailIntent, getString(R.string.title_send_email))); 
	
			break;
		}
	}
}
