package com.pendtium.base.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.Menu;

import com.astuetz.PagerSlidingTabStrip;
import com.pendtium.R;
import com.pendtium.base.fragments.BaseFragment;
import com.pendtium.base.fragments.DoaFragment;
import com.pendtium.base.fragments.HijriahFragment;
import com.pendtium.base.fragments.KiblatFragment;
import com.pendtium.base.fragments.TadjwidFragment;
import com.pendtium.quran.fragments.QuranFragment;
import com.pendtium.shollu.fragments.SholatFragment;

public class HomeActivity extends BaseActivity implements OnPageChangeListener{

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor;

	private List<BaseFragment> listFragment;

	public static final int REQUEST_PASSPORT_PURCHASE = 2012;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		listFragment = getListFragment();
		adapter = new MyPagerAdapter(getSupportFragmentManager(), listFragment);
		
		pager.setAdapter(adapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
		tabs.setOnPageChangeListener(this);
		currentColor = getResources().getColor(R.color.light_red);
		changeColor(currentColor);
		setTitleBar("Sholat");
	}

	private List<BaseFragment> getListFragment() {
		List<BaseFragment> list = new ArrayList<BaseFragment>();
		list.add(new SholatFragment());
		list.add(new KiblatFragment());
		list.add(new QuranFragment());
		list.add(new TadjwidFragment());
		list.add(new DoaFragment());
		list.add(new HijriahFragment());
		return list;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	private void changeColor(int newColor) {

		tabs.setIndicatorColor(newColor);

			Drawable colorDrawable = new ColorDrawable(newColor);
			Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
			LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

			if (oldBackground == null) {

//				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
//					ld.setCallback(drawableCallback);
//				} else {
					getSupportActionBar().setBackgroundDrawable(ld);
//				}

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

			// http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
			getSupportActionBar().setDisplayShowTitleEnabled(false);
			getSupportActionBar().setDisplayShowTitleEnabled(true);

		currentColor = newColor;

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("currentColor", currentColor);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		currentColor = savedInstanceState.getInt("currentColor");
		changeColor(currentColor);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		private final String[] TITLES = { 
				SholatFragment.name, KiblatFragment.name, 
				QuranFragment.name, TadjwidFragment.name, 
				DoaFragment.name, HijriahFragment.name
			};
		private List<BaseFragment> list;

		public MyPagerAdapter(FragmentManager fm, List<BaseFragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int position) {
			return list.get(position);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		int color = listFragment.get(position).getColor();
		String title = listFragment.get(position).getTitle();
		if (color != 0) {
			changeColor(color);
		}
		if (title != null) {
			setTitleBar(title);
		}
	}

	private void setTitleBar(String title) {
		getSupportActionBar().setTitle(getString(R.string.app_name)+" - " + title);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (REQUEST_PASSPORT_PURCHASE == requestCode) {
			//TODO: add handler
			if (RESULT_OK == resultCode) {
				toastIt("Success");
			} else {
				toastIt("Failed");
			}
		}
	}

}
