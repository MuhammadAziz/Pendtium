package com.pendtium.base.adapters;

import org.jqurantree.orthography.Verse;

import com.pendtium.R;
import com.pendtium.base.helpers.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class VerseAdapter extends BaseListAdapters<Verse> {

	public VerseAdapter(Context context) {
		super(context);
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
		if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.ayat_item_list, viewGroup, false);
        }
        TextView text = ViewHolder.get(convertView, R.id.ayat_txt);
        Verse verse = list.get(position);
        text.setText(verse.toUnicode());
        return convertView;
	}
}
