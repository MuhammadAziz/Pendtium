package com.pendtium.base.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.pendtium.base.helpers.ThreadPreconditions;
import com.pendtium.base.helpers.ViewHolder;

/**
 * 
 * @author ziddan
 *
 */
public class BaseListAdapters<T> extends BaseAdapter {

    protected final Context context;
    protected List<T> list = new ArrayList<T>();

    public BaseListAdapters(Context context){
        this.context = context;
    }

    public void updateList(List<T> list){
        ThreadPreconditions.checkOnMainThread();
        this.list = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }
//        TextView text = ViewHolder.get(view, android.R.id.text1);
//        text.setText(list.get(position));
        return view;
    }
}

