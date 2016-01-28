package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @description：身体数据界面
 * @author samy
 * @date 2015-2-25 下午1:48:00
 */
public class BodyDataContentAct extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, new BodyDataContentFra()).commit();
	}
}
