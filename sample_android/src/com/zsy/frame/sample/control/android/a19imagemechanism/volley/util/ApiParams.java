package com.zsy.frame.sample.control.android.a19imagemechanism.volley.util;

import java.util.HashMap;

/**
 * @description：POST请求网络；MAP携带提交参数；可联系with连加；
 * @author samy
 * @date 2014年8月15日 下午10:18:23
 */
public class ApiParams extends HashMap<String, String> {
	private static final long serialVersionUID = 8112047472727256876L;

	public ApiParams with(String key, String value) {
		put(key, value);
		return this;
	}
}
