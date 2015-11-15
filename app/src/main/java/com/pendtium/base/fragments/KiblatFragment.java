/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pendtium.base.fragments;

import islam.adhanalarm.Schedule;
import islam.adhanalarm.VARIABLE;
import islam.adhanalarm.dialog.CalculationSettingsDialog;
import islam.adhanalarm.receiver.StartNotificationReceiver;
import islam.adhanalarm.utility.ThemeManager;
import android.content.Context;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.pendtium.R;
import com.pendtium.qibla.QiblaCompassView;

public class KiblatFragment extends BaseFragment {
	
	public static String name = "Kiblat";
	
	private static SensorListener orientationListener;
	private static boolean isTrackingOrientation = false;
	private QiblaCompassView qibla;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
//		VARIABLE.context = getActivity();
		if (VARIABLE.settings == null)
			VARIABLE.settings = getActivity().getSharedPreferences("settingsFile",
					Context.MODE_PRIVATE);
		
		super.onCreate(savedInstanceState);

//		localeManager = new LocaleManager();
//		setTitle(Schedule.today().hijriDateToString(getActivity()));
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		setTitle("Kiblat");
		setColor(getResources().getColor(R.color.light_orange));
		
		margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.setMargins(margin, margin, margin, margin);
	}
	

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			new CalculationSettingsDialog(getActivity()).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FrameLayout fl = (FrameLayout) super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.shollu_tab_qibla, container, false);
		v.setBackgroundResource(R.drawable.background_card);
		ThemeManager themeManager = new ThemeManager(getActivity());
		qibla = (QiblaCompassView) v.findViewById(R.id.qibla_compass);
		qibla.setConstants(
				((TextView) v.findViewById(R.id.bearing_north)),
				getText(R.string.bearing_north),
				((TextView) v.findViewById(R.id.bearing_qibla)),
				getText(R.string.bearing_qibla), themeManager);
		
		orientationListener = new SensorListener() {
			public void onSensorChanged(int s, float v[]) {
				float northDirection = v[android.hardware.SensorManager.DATA_X];
				qibla.setDirections(northDirection, 0);

			}

			public void onAccuracyChanged(int s, int a) {
			}
		};
		v.setLayoutParams(params);
		v.setPadding(margin, margin, margin, margin);
		fl.addView(v);
		return fl;
	}

	@Override
	public void onStart() {
//		VARIABLE.mainActivityIsRunning = true;
		startTrackingOrientation();
		super.onStart();
	}

	@Override
	public void onStop() {
//		VARIABLE.mainActivityIsRunning = false;
		stopTrackingOrientation();
		super.onStop();
	}
	
	private void startTrackingOrientation() {
		if (!isTrackingOrientation)
			isTrackingOrientation = ((SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE))
					.registerListener(orientationListener,
							android.hardware.SensorManager.SENSOR_ORIENTATION);
	}

	private void stopTrackingOrientation() {
		if (isTrackingOrientation)
			((SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE))
					.unregisterListener(orientationListener);
		isTrackingOrientation = false;
	}

}