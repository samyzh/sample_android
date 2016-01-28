package com.zsy.frame.sample.control.android.a19imagemechanism.volley.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
	public static void showToast(Context context, CharSequence text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

}
