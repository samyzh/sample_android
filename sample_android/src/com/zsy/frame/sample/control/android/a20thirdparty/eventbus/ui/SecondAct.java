package com.zsy.frame.sample.control.android.a20thirdparty.eventbus.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event.FirstEvent;
import com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event.SecondEvent;
import com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event.ThirdEvent;

import de.greenrobot.event.EventBus;

public class SecondAct extends Activity {
	private Button btn_FirstEvent, btn_SecondEvent, btn_ThirdEvent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a20thirdparty_eventbus_second);
		btn_FirstEvent = (Button) findViewById(R.id.btn_first_event);
		btn_SecondEvent = (Button) findViewById(R.id.btn_second_event);
		btn_ThirdEvent = (Button) findViewById(R.id.btn_third_event);

		btn_FirstEvent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new FirstEvent("FirstEvent btn clicked"));
			}
		});

		btn_SecondEvent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new SecondEvent("SecondEvent btn clicked"));
			}
		});

		btn_ThirdEvent.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EventBus.getDefault().post(new ThirdEvent("ThirdEvent btn clicked"));
			}
		});

	}

}
