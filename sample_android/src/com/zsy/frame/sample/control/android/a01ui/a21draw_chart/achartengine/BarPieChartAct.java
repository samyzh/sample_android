package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.achartengine;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

/**
 * @description：柱状和饼图显示；目前用的是achartengine-1.0.0;跟其他版本（高版本）不兼容；
 * @author samy
 * @date 2015-1-28 下午5:05:17
 */
public class BarPieChartAct extends BaseAct {
	@BindView(id = R.id.barpie_bar_btn, click = true)
	private Button barpie_bar_btn;
	@BindView(id = R.id.barpie_pie_btn, click = true)
	private Button barpie_pie_btn;

	public BarPieChartAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a21draw_chart_barpie);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.barpie_bar_btn:
				Intent lineIntent = new BarChart().getIntent(this);
				startActivity(lineIntent);
				break;
			case R.id.barpie_pie_btn:
				Intent pieIntent = new PieChart().getIntent(this);
				startActivity(pieIntent);
				break;
		}
	}
}
