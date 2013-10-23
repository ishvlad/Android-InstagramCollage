package com.example.photocollageapp.loaders;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.example.photocollageapp.R;


public class ImageLoader {
	private final static String TAG = "ImadeLoader";
	
	public static void fetchImage(final String mUrl, final ImageView mView) {
	    if ( mUrl == null || mView == null ) {
	    	return;
	    }
	 
	    final Handler handler = new Handler() {
	        @Override
	        public void handleMessage(Message message) {
	            final Bitmap image = (Bitmap) message.obj;
	            mView.setImageBitmap(image);
	        }
	    };
	 
	    final Thread thread = new Thread() {
	        @Override
	        public void run() {
	            final Bitmap image = downloadImage(mUrl);
	            if ( image != null ) {
		            final Message message = handler.obtainMessage(1, image);
		            handler.sendMessage(message);
	            } else {
	            	Log.d(TAG, "Did not get image by URL: " + mUrl);
	            }
	        }
	    };
	    
	    mView.setImageResource(R.drawable.ic_launcher);
	    thread.setPriority(3);
	    thread.start();
	}
	
	public static Bitmap downloadImage(String mUrl) {
	    Bitmap bitmap = null;
	    HttpURLConnection conn = null;
	    BufferedInputStream buf_stream = null;
	    try {
		    conn = (HttpURLConnection) new URL(mUrl).openConnection();
		    conn.setDoInput(true);
		    conn.setRequestProperty("Connection", "Keep-Alive");
		    conn.connect();
		    
		    buf_stream = new BufferedInputStream(conn.getInputStream(), 8192);
		    bitmap = BitmapFactory.decodeStream(buf_stream);
		    buf_stream.close();
		    conn.disconnect();
		    buf_stream = null;
		    conn = null;
	    } catch (MalformedURLException ex) {
	    	Log.e(TAG, "Url parsing was failed: " + mUrl);
	    } catch (IOException ex) {
	    	Log.d(TAG, mUrl + " does not exists");
	    } catch (OutOfMemoryError e) {
		    Log.w(TAG, "Out of memory!!!");
		    return null;
	    } finally {
		    if ( buf_stream != null )
		        try { buf_stream.close(); } catch (IOException ex) {}
		    if ( conn != null )
		        conn.disconnect();
	    }
	    return bitmap;
	}
	
}