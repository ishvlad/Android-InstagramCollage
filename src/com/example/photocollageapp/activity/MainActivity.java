package com.example.photocollageapp.activity;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photocollageapp.R;
import com.example.photocollageapp.loaders.NameTask;

public class MainActivity extends Activity implements OnClickListener {
	public static final String TAG = "MAIN";
	
	private Intent stepTwoIntent;
	private long mUserId = 0;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		TextView stepView = (TextView) findViewById(R.id.title_step1);
		stepView.setText(getString(R.string.pattern_title_step, "1"));
		
		TextView goView = (TextView) findViewById(R.id.button_get_collage);
		goView.setOnClickListener(this);

		
	}



	@SuppressLint("DefaultLocale")
	private void getCollageClick() throws JSONException{
		String authorNick = ((EditText) findViewById(R.id.instagram_ID)).getText().toString().toLowerCase();
		
		if(authorNick.equals("")) {
			Toast.makeText(this, getText(R.string.toast_empty_instagram_ID), Toast.LENGTH_SHORT).show();
			return;
		}
		
		final String fullUrl =  getString(R.string.pattern_instagram_user_id, authorNick);
		
		NameTask nameTask = new NameTask(this);
		nameTask.execute(fullUrl);
		String data = null;;
		try {data = nameTask.get();} catch (InterruptedException e) {} catch (ExecutionException e) {}
		
		JSONArray jArray = (new JSONObject(data)).getJSONArray("data");
		if (jArray.length() == 0) {
			Toast.makeText(this, getText(R.string.toast_not_found_user), Toast.LENGTH_SHORT).show();
			return;
		}
		
		int i = 0;
		for(; i < jArray.length(); i++){
			JSONObject jObject = jArray.getJSONObject(i);
			if(jObject.getString("username").compareTo(authorNick) == 0) break;
		}
		if (i == jArray.length()) {
			Toast.makeText(this, getText(R.string.toast_not_found_user), Toast.LENGTH_SHORT).show();
			return;
		}
		mUserId = Long.parseLong(jArray.getJSONObject(i).getString("id"));
		
		stepTwoIntent = new Intent(this, LayoutActivity.class);
		Bundle bundle = new Bundle();
		bundle.putLong(LayoutActivity.EXTRA_AUTHOR_ID, mUserId);
		stepTwoIntent.putExtras(bundle);
		
		startActivity(stepTwoIntent);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.button_get_collage:
			try {getCollageClick();} catch (JSONException e) {}
				break;
		}
	}
}
