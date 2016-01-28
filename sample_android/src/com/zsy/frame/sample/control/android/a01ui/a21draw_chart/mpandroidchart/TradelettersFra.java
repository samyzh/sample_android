package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.mpandroidchart;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.zsy.frame.sample.R;

public class TradelettersFra extends DialogFragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);  
		return inflater.inflate(R.layout.activity_a01ui_a21draw_chart_mpandroidchart_trade_letters, container);
	}
}
