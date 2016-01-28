package com.zsy.frame.sample.control.android.a01ui.a25customview.advance;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a25customview.advance.views.CircleBar;

public class PedometerAct extends Activity {
	private CircleBar circleBar;
	private EditText setnum;
	private Button setnumbutton;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a25customview_advance_passometer);
		circleBar = (CircleBar) findViewById(R.id.circle);
		setnum = (EditText) findViewById(R.id.setnum);
		setnumbutton = (Button) findViewById(R.id.setnumbutton);
		circleBar.setMaxstepnumber(10000);
		setnumbutton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(setnum.getText().toString())) {
					circleBar.update(Integer.parseInt(setnum.getText().toString()), 700);
				}
			}
		});

	}

}
