package com.pendtium.base.activities;

import com.pendtium.R;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
	
	private final Handler handler = new Handler();
	private Drawable oldBackground = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	protected Drawable.Callback drawableCallback = new Drawable.Callback() {
		@Override
		public void invalidateDrawable(Drawable who) {
			getSupportActionBar().setBackgroundDrawable(who);
		}

		@Override
		public void scheduleDrawable(Drawable who, Runnable what, long when) {
			handler.postAtTime(what, when);
		}

		@Override
		public void unscheduleDrawable(Drawable who, Runnable what) {
			handler.removeCallbacks(what);
		}
	};
	
	/**
	 * Replace target layout with source fragment
	 * @param target
	 * @param source
	 */
	protected void replaceFragment(int target, Fragment source) {
		this.getSupportFragmentManager().beginTransaction()
		.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
		.replace(target, source).commit();
	}

	public void toastIt(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
	
	protected void changeActionBarColor(int color){
		Drawable colorDrawable = new ColorDrawable(color);
		Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
		LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

		if (oldBackground == null) {

//			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//				ld.setCallback(drawableCallback);
//			} else {
				getSupportActionBar().setBackgroundDrawable(ld);
//			}

		} else {

			TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

			// workaround for broken ActionBarContainer drawable handling on
			// pre-API 17 builds
			// https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
				td.setCallback(drawableCallback);
			} else {
				getSupportActionBar().setBackgroundDrawable(td);
			}

			td.startTransition(200);

		}

		oldBackground = ld;
	}
}
