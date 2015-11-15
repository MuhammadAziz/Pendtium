package com.pendtium.shollu.adapters;

import com.pendtium.R;
import com.pendtium.base.adapters.BaseListAdapters;
import com.pendtium.base.helpers.ViewHolder;
import com.pendtium.quran.controllers.PrayTime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PrayTimeAdapter extends BaseListAdapters<String> {

	public PrayTimeAdapter(Context context) {
		super(context);
	}
	
	@Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.surah_item_list, viewGroup, false);
        }
        TextView txtName = ViewHolder.get(view, R.id.surah_txt_latin);
        TextView txtTime = ViewHolder.get(view, R.id.surah_txt_arabic);
        txtName.setText(PrayTime.getTimeNames().get(position));
        txtTime.setText(list.get(position));
        return view;
	}

}
