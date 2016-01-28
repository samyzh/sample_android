package com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment;

import com.zsy.frame.sample.control.android.a17navigation.actionbar.activity.SpinnerTabsAct;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AlbumFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity());
		textView.setText("Album Fragment \n to SpinnerTabsAct");
		textView.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout layout = new LinearLayout(getActivity());
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layout.addView(textView, params);

		textView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), SpinnerTabsAct.class));
			}
		});
		return layout;
	}

}