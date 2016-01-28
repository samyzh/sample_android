package com.zsy.frame.sample.control.android.a19imagemechanism.volley.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.zsy.frame.sample.R;

public class VolleyAboutAct extends Activity {

	private TextView tvAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.actitvity_a19imagemechanism_volley_app_about);
		setTitle(R.string.action_about);
		tvAbout = (TextView) findViewById(R.id.tv_about);
		// 自带链接跳转功能；这样设置可以不用了点击事件；方便快捷
		tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
		tvAbout.setText(Html.fromHtml((getResources().getString((R.string.about_text)))));
	}
}
