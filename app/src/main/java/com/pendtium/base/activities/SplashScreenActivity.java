package com.pendtium.base.activities;

import org.jqurantree.orthography.Document;

import com.pendtium.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.view.Window;

public class SplashScreenActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		new InitializerAsyncTask().execute("");
	}
	
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	
	/**
	 * Background thread responsible for initializing the app.
	 */
	private class InitializerAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
//			Document.init(SplashScreenActivity.this);
			return "executed";
		}

		@Override
		protected void onPostExecute(String result) {
			
			// Delay 1 sec before start Main Activity
			Handler handler = new Handler(); 
		    handler.post(new Runnable() { 
		         public void run() {
		        	Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
		     		startActivity(intent);
		     		finish();
		         } 
		    }); 
			
		}
	}
}
