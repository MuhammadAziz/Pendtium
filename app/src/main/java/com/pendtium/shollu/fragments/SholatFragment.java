/*
 * Copyright (C) 2014 Aziz Muhammad <dazziest@gmail.com>
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

package com.pendtium.shollu.fragments;

import islam.adhanalarm.CONSTANT;
import islam.adhanalarm.Notifier;
import islam.adhanalarm.Schedule;
import islam.adhanalarm.VARIABLE;
import islam.adhanalarm.dialog.SettingsDialog;
import islam.adhanalarm.receiver.StartNotificationReceiver;
import islam.adhanalarm.services.FillDailyTimetableService;
import islam.adhanalarm.utility.GateKeeper;
import islam.adhanalarm.utility.LocaleManager;
import islam.adhanalarm.utility.ThemeManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.pendtium.R;
import com.pendtium.base.activities.HomeActivity;
import com.pendtium.base.activities.PurchaseActivity;
import com.pendtium.base.fragments.BaseFragment;

public class SholatFragment extends BaseFragment {
	
//	private static ThemeManager themeManager;
	private static LocaleManager localeManager;

	private ArrayList<HashMap<String, String>> timetable = new ArrayList<HashMap<String, String>>();
	private SimpleAdapter timetableView;

//	private static SensorListener orientationListener;
//	private static boolean isTrackingOrientation = false;
	
	public static String name = "Jadwal Sholat";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (VARIABLE.settings == null)
			VARIABLE.settings = getActivity().getSharedPreferences("settingsFile",
					Context.MODE_PRIVATE);

//		themeManager = new ThemeManager(getActivity());
		super.onCreate(savedInstanceState);

		localeManager = new LocaleManager(getActivity());
		setRetainInstance(true);
		setHasOptionsMenu(true);
		
		setTitle("Jadwal Sholat");
		setColor(getResources().getColor(R.color.light_red));
		
		margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
				.getDisplayMetrics());
		params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.setMargins(margin, margin, margin, margin);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FrameLayout fl = (FrameLayout) super.onCreateView(inflater, container, savedInstanceState);
		View v = inflater.inflate(R.layout.shollu_tab_today, container, false);
		TextView info = (TextView) v.findViewById(R.id.today_info);
		info.setText(Schedule.today().hijriDateToString(getActivity()));
		v.setBackgroundResource(R.drawable.background_card);
		if (timetable.isEmpty()) {
			for (int i = CONSTANT.FAJR; i <= CONSTANT.NEXT_FAJR; i++) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("time_name", getString(CONSTANT.TIME_NAMES[i]));
				timetable.add(i, map);
			}
		}
		timetableView = new SimpleAdapter(getActivity(), timetable,
				R.layout.shollu_timetable_row, new String[] { "mark", "time_name",
						"time", "time_am_pm" }, new int[] { R.id.mark,
						R.id.time_name, R.id.time, R.id.time_am_pm });
		((ListView) v.findViewById(R.id.timetable)).setAdapter(timetableView);
		configureCalculationDefaults(v); /* End of Tab 1 Items */
		v.setLayoutParams(params);
		v.setPadding(margin, margin, margin, margin);
		fl.addView(v);
		return fl;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.layout.shollu_menu, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		short time = Schedule.today().nextTimeIndex();
		switch (item.getItemId()) {
		case R.id.menu_previous:
			time--;
			if (time < CONSTANT.FAJR)
				time = CONSTANT.ISHAA;
			if (time == CONSTANT.SUNRISE && !VARIABLE.alertSunrise())
				time = CONSTANT.FAJR;
			Notifier.start(getActivity(), time,
					Schedule.today().getTimes()[time].getTimeInMillis());
			break;
		case R.id.menu_next:
			if (time == CONSTANT.SUNRISE && !VARIABLE.alertSunrise())
				time = CONSTANT.DHUHR;
			Notifier.start(getActivity(), time,
					Schedule.today().getTimes()[time].getTimeInMillis());
//			Intent intent = new Intent(getActivity(), HeadActivity.class);
//			getActivity().startActivity(intent);
			break;
		case R.id.menu_stop:
			Notifier.stop();
			break;
		case R.id.menu_settings:
			new SettingsDialog(getActivity(), localeManager, new ThemeManager(getActivity())).show();
			break;
		case R.id.menu_buy:
			Activity activity = getActivity();
			Intent intent = new Intent(activity, PurchaseActivity.class);
			activity.startActivityForResult(intent, HomeActivity.REQUEST_PASSPORT_PURCHASE);
			break;
		case R.id.menu_help:

//			SpannableString s = new SpannableString(getText(R.string.help_text));
//			Linkify.addLinks(s, Linkify.WEB_URLS);
//			LinearLayout help = (LinearLayout) getActivity().getLayoutInflater().inflate(
//					R.layout.shollu_help, null);
//			TextView message = (TextView) help.findViewById(R.id.help);
//			message.setText(s);
//			message.setMovementMethod(LinkMovementMethod.getInstance());
//			new AlertDialog.Builder(getActivity()).setTitle(R.string.help).setView(help)
//					.setPositiveButton(android.R.string.ok, null).create()
//					.show();
//			break;
		case R.id.menu_information:
//			s = new SpannableString(getText(R.string.information_text)
//					.toString().replace("#", GateKeeper.getVersionName(getActivity())));
//			Linkify.addLinks(s, Linkify.WEB_URLS);
//			LinearLayout information = (LinearLayout) getActivity().getLayoutInflater()
//					.inflate(R.layout.shollu_information, null);
//			message = (TextView) information.findViewById(R.id.information);
//			message.setText(s);
//			message.setMovementMethod(LinkMovementMethod.getInstance());
//			new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_launcher)
//					.setTitle(R.string.app_name).setView(information)
//					.setPositiveButton(android.R.string.ok, null).create()
//					.show();
//			break;
		}
		return super.onOptionsItemSelected(item);
	}

//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		super.onWindowFocusChanged(hasFocus);
//		if (hasFocus && (themeManager.isDirty() || localeManager.isDirty())) {
//			VARIABLE.updateWidgets(this);
//			restart();
//		} else if (hasFocus) {
//			if (VARIABLE.settings.contains("latitude")
//					&& VARIABLE.settings.contains("longitude")) {
//				((TextView) findViewById(R.id.notes)).setText("");
//			}
//			if (Schedule.settingsAreDirty()) {
//				updateTodaysTimetableAndNotification();
//				VARIABLE.updateWidgets(this);
//			}
//		}
//	}

	@Override
	public void onStart() {
		StartNotificationReceiver.setNext(getActivity());
//		VARIABLE.mainActivityIsRunning = true;
		updateTodaysTimetableAndNotification();
//		startTrackingOrientation();
		super.onStart();
	}

	@Override
	public void onStop() {
//		stopTrackingOrientation();
//		VARIABLE.mainActivityIsRunning = false;
		super.onStop();
	}

	

	private void restart() {
		long restartTime = Calendar.getInstance().getTimeInMillis()
				+ CONSTANT.RESTART_DELAY;
		AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, restartTime, PendingIntent.getActivity(
				getActivity(), 0, getActivity().getIntent(), PendingIntent.FLAG_ONE_SHOT
						| PendingIntent.FLAG_CANCEL_CURRENT));
		getActivity().finish();
	}

	private void configureCalculationDefaults(View v) {
		if (!VARIABLE.settings.contains("latitude")
				|| !VARIABLE.settings.contains("longitude")) {
			Location currentLocation = VARIABLE.getCurrentLocation(getActivity());
			try {
				SharedPreferences.Editor editor = VARIABLE.settings.edit();
				editor.putFloat("latitude", (float) currentLocation.getLatitude());
				editor.putFloat("longitude", (float) currentLocation.getLongitude());
				editor.commit();
				VARIABLE.updateWidgets(getActivity());
			} catch (Exception ex) {
				((TextView) v.findViewById(R.id.notes))
						.setText(getString(R.string.location_not_set));
			}
		}
		if (!VARIABLE.settings.contains("calculationMethodsIndex")) {
			try {
				String country = Locale.getDefault().getISO3Country()
						.toUpperCase();

				SharedPreferences.Editor editor = VARIABLE.settings.edit();
				for (int i = 0; i < CONSTANT.CALCULATION_METHOD_COUNTRY_CODES.length; i++) {
					if (Arrays.asList(
							CONSTANT.CALCULATION_METHOD_COUNTRY_CODES[i])
							.contains(country)) {
						editor.putInt("calculationMethodsIndex", i);
						editor.commit();
						VARIABLE.updateWidgets(getActivity());
						break;
					}
				}
			} catch (Exception ex) {
				// Wasn't set, oh well we'll uses DEFAULT_CALCULATION_METHOD
				// later
			}
		}
	}

	private void updateTodaysTimetableAndNotification() {
		StartNotificationReceiver.setNext(getActivity());
		FillDailyTimetableService.set(getActivity(), Schedule.today(), timetable,
				timetableView);
	}

}