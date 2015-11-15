package com.pendtium.base.activities;

import com.pendtium.base.fragments.BaseFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;

public class DetailActivity extends BaseActivity {
	
	private final int LAYOUT_ID = 9999;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout fl = new FrameLayout(this);
		fl.setId(LAYOUT_ID);
		LayoutParams param = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		fl.setLayoutParams(param);
		Bundle extras = getIntent().getExtras();
		Fragment frag = getFragmentData(extras);
		replaceFragment(LAYOUT_ID, frag);
		setContentView(fl);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	private Fragment getFragmentData(Bundle extras) {
		String className = extras.getString("fragment");
		Fragment fragment = null;
		try {
			fragment = (Fragment) Class.forName(className).newInstance();
			fragment.setArguments(extras);
		} catch (InstantiationException e) {
			fragment = new BaseFragment();
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			fragment = new BaseFragment();
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			fragment = new BaseFragment();
			e.printStackTrace();
		}
		return fragment;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		if (item.getItemId() == android.R.id.home) {
			this.onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
}
