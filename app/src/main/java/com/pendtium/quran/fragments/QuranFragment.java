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

package com.pendtium.quran.fragments;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

import org.jqurantree.orthography.Chapter;
import org.jqurantree.orthography.Document;

import quran.labs.data.QuranInfo;
import quran.labs.util.QuranUtils;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.text.TextUtilsCompat;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pendtium.R;
import com.pendtium.base.activities.DetailActivity;
import com.pendtium.base.adapters.BaseListAdapters;
import com.pendtium.base.fragments.BaseFragment;
import com.pendtium.base.helpers.ViewHolder;

public class QuranFragment extends BaseFragment {

	private List<Chapter> listAyat;
//	private BaseListAdapters<Chapter> adapter;
	
	public static String name = "Al-Qur'an";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Alquran");
		setColor(getResources().getColor(R.color.light_purple));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FrameLayout fl = (FrameLayout) super.onCreateView(inflater, container, savedInstanceState);
		ListView listView = new ListView(getActivity());
		listView.setLayoutParams(params);
		listView.setBackgroundResource(R.drawable.background_card);
//		setListSurat();
//		adapter = getAdapter();
//		adapter.updateList(listAyat);
//		listView.setAdapter(adapter);
		listView.setOnItemClickListener(getOnItemClickListener());
		fl.addView(listView);
//		if (listAyat.isEmpty()) {
//			updateContent();
//		}
		return fl;
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
	}
	

//	private void updateContent() {
//		(new AsyncTask<String, Void, String>() {
//
//			@Override
//			protected String doInBackground(String... params) {
//				Document.init(getActivity());
//				return "";
//			}
//
//			@Override
//			protected void onPostExecute(String result) {
//				setListSurat();
//				adapter.updateList(listAyat);
//			}
//
//		}).execute("");
//	}

//	private BaseListAdapters<Chapter> getAdapter() {
//		final Typeface tf = QuranUtils.getFontFromSD(getActivity(), "SCHE.TTF");
//		return new BaseListAdapters<Chapter>(getActivity()){
//			@Override
//		    public View getView(int position, View convertView, ViewGroup viewGroup) {
//				if (convertView == null){
//		            convertView = LayoutInflater.from(context).inflate(R.layout.surah_item_list, viewGroup, false);
//		        }
//		        TextView textArabic = ViewHolder.get(convertView, R.id.surah_txt_arabic);
//		        TextView textLatin = ViewHolder.get(convertView, R.id.surah_txt_latin);
//		        Chapter chapter = list.get(position);
//		        int index = position + 1;
//		        String title = chapter.getName().toUnicode();
//		        textArabic.setText(title);
//		        textArabic.setTypeface(tf);
//		        textLatin.setText(index+". "+QuranInfo.SURA_NAMES[position]);
//		        return convertView;
//			}
//		};
//	}

	private OnItemClickListener getOnItemClickListener() {
		// TODO Auto-generated method stub
		return new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), DetailActivity.class);
				int surah = position + 1;
				intent.putExtra("surah", surah);
				intent.putExtra("color", getColor());
				intent.putExtra("fragment", QuranDetailFragment.class.getName());
				getActivity().startActivity(intent);
			}
		};
	}

//	private void setListSurat() {
//		listAyat = new ArrayList<Chapter>();
//		int index;
//		for (int i = 0; i < 114; i++) {
//			index = i + 1;
//			listAyat.add(Document.getChapter(index));
//		}
//	}

}