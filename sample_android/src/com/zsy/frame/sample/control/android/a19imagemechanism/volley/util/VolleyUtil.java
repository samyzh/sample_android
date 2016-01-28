package com.zsy.frame.sample.control.android.a19imagemechanism.volley.util;

import android.content.Context;

import com.zsy.frame.lib.net.http.volley.RequestQueue;
import com.zsy.frame.lib.net.http.volley.toolbox.Volley;

public class VolleyUtil {

	private volatile static RequestQueue requestQueue;

	/** 返回RequestQueue单例 **/
	public static RequestQueue getQueue(Context context) {
		if (requestQueue == null) {
			synchronized (VolleyUtil.class) {
				if (requestQueue == null) {
					requestQueue = Volley.newRequestQueue(context.getApplicationContext());
				}
			}
		}
		return requestQueue;
	}
}
