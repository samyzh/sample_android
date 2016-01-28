package com.zsy.frame.sample.control.android.a20thirdparty.qrcode;

import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.views.EcodeingProductView;

import android.app.Activity;
import android.os.Bundle;

public class EcodeingProductAct extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EcodeingProductView ev = new EcodeingProductView(this);
		setContentView(ev);
	}
}
