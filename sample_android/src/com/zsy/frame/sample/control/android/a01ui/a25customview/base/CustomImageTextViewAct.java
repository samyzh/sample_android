package com.zsy.frame.sample.control.android.a01ui.a25customview.base;

import android.app.Activity;
import android.os.Bundle;

import com.zsy.frame.sample.R;

/**
 * @description：
 * 我特意让显示出现3中情况：
1、字体的宽度大于图片，且View宽度设置为wrap_content
2、View宽度设置为精确值，字体的长度大于此宽度
3、图片的宽度大于字体，且View宽度设置为wrap_content
 * @author samy
 * @date 2015-3-21 下午11:19:50
 */
public class CustomImageTextViewAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a25customview_base_customimagetextview);
	}

}
