package com.zsy.frame.sample.control.android.a06fourcomponents.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

public class StartMethodTestCAct extends BaseAct {
	@BindView(id = R.id.textView1)
	private TextView textView1;
	@BindView(id = R.id.button1, click = true)
	private Button button1;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a06fourcomponents_activity_startmethod_testc);
	}
	
	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		textView1.setText(this.toString()+"\n" +"current task id: " + this.getTaskId());
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.button1:
				showActivity(aty, this.getClass());
				textView1.setText(this.toString()+"\n" +"current task id: " + this.getTaskId());
				break;
		}
	}

}
