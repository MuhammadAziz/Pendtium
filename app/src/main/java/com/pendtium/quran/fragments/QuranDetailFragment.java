package com.pendtium.quran.fragments;

import java.util.ArrayList;
import java.util.List;

import org.jqurantree.orthography.Chapter;
import org.jqurantree.orthography.Document;
import org.jqurantree.orthography.Verse;

import quran.labs.util.QuranUtils;
import quran.labs.widget.LongLinkMovementMethod;
import quran.labs.widget.LongURLSpan;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pendtium.R;
import com.pendtium.base.adapters.BaseListAdapters;
import com.pendtium.base.fragments.BaseFragment;
import com.pendtium.base.helpers.ViewHolder;

public class QuranDetailFragment extends BaseFragment {
	int surah;

	private int currentColor;
	private List<Verse> list;
	private BaseListAdapters<Verse> adapter;

	private Chapter chapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getArguments();
		surah = extras.getInt("surah");
		currentColor = extras.getInt("color");
		setColor(currentColor);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity());
//		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.MATCH_PARENT);
		textView.setLayoutParams(params);
		setListVerse();;
		if (list.isEmpty()) {
			updateContent();
		}
		setContent(textView);
		textView.setBackgroundResource(R.drawable.background_card);
		return textView;
	}
	
	@Override
	public void onStart(){
		super.onStart();
		changeActionBarColor(currentColor);
	}

	private void setContent(TextView textView) {
		BitmapDrawable header = (BitmapDrawable) getActivity().getResources().getDrawable(R.drawable.header);
		Typeface pageTypeFace = QuranUtils.getFontFromSD(getActivity(), "ME_QURAN.TTF");
		Typeface titleTypeFace = QuranUtils.getMainFontFromSD(getActivity());
//		StringBuilder ayats = new StringBuilder();
		String title = chapter.getName().toString();

		SpannableStringBuilder builder = new SpannableStringBuilder();
		int first = 0;
		int last = 0;
//		builder.setSpan(new HeaderURLSpan("/"+chapter.getChapterNumber()+"/title", pageTypeFace, header, 0), 0, builder.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		for (int i = 0; i<list.size(); i++) {
			Verse verse = list.get(i);
			int idx = i + 1;
			String ayat = verse.toUnicode();
			builder.append(ayat);
			builder.append(" ("+idx+") ");
			last = builder.toString().lastIndexOf("(");
			builder.setSpan(new LongURLSpan("/"+chapter.getChapterNumber()+"/"+ idx, pageTypeFace), first, last, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			first = builder.toString().lastIndexOf(")") + 1;
		}
//		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		textView.setText(builder);
		textView.setTypeface(pageTypeFace);
		textView.setTextSize(24f);
		textView.setLineSpacing(3, 1.3f);
		textView.setMovementMethod(LongLinkMovementMethod.getInstance());
	}

	private BaseListAdapters<Verse> getAdapter() {
		return new BaseListAdapters<Verse>(getActivity()){
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
		};
	}

	private void setListVerse() {
		list = new ArrayList<Verse>();
		chapter = Document.getChapter(surah);
		setTitle(chapter.getName().toString());
		int countVerse = chapter.getVerseCount();
		for (int i = 1; i <= countVerse; i++) {
			list.add(chapter.getVerse(i));
		}
	}
	
	private void updateContent() {
		(new AsyncTask<String, Void, String>() {

			@Override
			protected String doInBackground(String... params) {
				Document.init(getActivity());
				return "";
			}
			
			@Override
			protected void onPostExecute(String result) {
				setListVerse();
				adapter.updateList(list);
			}
			
		}).execute("");
	}
}
