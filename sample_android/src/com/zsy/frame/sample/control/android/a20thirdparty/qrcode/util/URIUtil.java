package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.util;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class URIUtil {

	// 更具url解析出请求参数
	public static Map<String, String> getParams(URI url) {
		try {
			Map<String, String> params = new HashMap<String, String>();
			String query = url.getQuery();
			String[] entitys = query.split("&");
			for (String entity : entitys) {
				String[] kv = entity.split("=");
				params.put(kv[0], kv[1]);
			}
			return params;
		}
		catch (Exception e) {
			return null;
		}
	}

}
