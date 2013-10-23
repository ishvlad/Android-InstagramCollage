package com.example.photocollageapp.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.photocollageapp.R;

public class NameTask extends AsyncTask<String, Void, String> {
	final public String TAG = "NameTask"; 

	private ProgressDialog mProgressDialog;
	private Context mContext;
	
	public NameTask(Context context) {
		super();
		mContext = context;
		
		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setMessage(context.getText(R.string.title_download));
		mProgressDialog.setIndeterminate(true);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		
		mProgressDialog.show();
	}
	
	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	
		mProgressDialog.dismiss();
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		return downloadName(arg0[0]);
	}
	
	public String downloadName(String mUrl) {
	    String res = null;
	    HttpURLConnection conn = null;
	    try {
		    Log.v(TAG, "Starting loading image by URL: " + mUrl);
		    conn = (HttpURLConnection) new URL(mUrl).openConnection();
		    conn.setDoInput(true);
		    conn.setRequestProperty("Connection", "Keep-Alive");
		    conn.connect();
		    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			conn.disconnect();
		    conn = null;
		    res = sb.toString();
	    } catch (MalformedURLException ex) {
	    	Log.e(TAG, "Url parsing was failed: " + mUrl);
	    } catch (IOException ex) {
	    	Log.d(TAG, mUrl + " does not exists");
	    } catch (OutOfMemoryError e) {
		    Log.w(TAG, "Out of memory!!!");
		    return null;
	    } finally {
		    if ( conn != null )
		        conn.disconnect();
	    }
	    return res;
	}
}
