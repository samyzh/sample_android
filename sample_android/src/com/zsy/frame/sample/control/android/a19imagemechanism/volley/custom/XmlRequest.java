package com.zsy.frame.sample.control.android.a19imagemechanism.volley.custom;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.zsy.frame.lib.net.http.volley.AuthFailureError;
import com.zsy.frame.lib.net.http.volley.NetworkResponse;
import com.zsy.frame.lib.net.http.volley.ParseError;
import com.zsy.frame.lib.net.http.volley.Request;
import com.zsy.frame.lib.net.http.volley.Request.Method;
import com.zsy.frame.lib.net.http.volley.Response;
import com.zsy.frame.lib.net.http.volley.Response.ErrorListener;
import com.zsy.frame.lib.net.http.volley.Response.Listener;
import com.zsy.frame.lib.net.http.volley.toolbox.HttpHeaderParser;

/**
 * @description：自定义的XML请求协议
 * @author samy
 * @date 2015-3-27 下午4:06:22
 */
public class XmlRequest extends Request<XmlPullParser> {

	private final Listener<XmlPullParser> mListener;

	public XmlRequest(int method, String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		mListener = listener;
	}

	public XmlRequest(String url, Listener<XmlPullParser> listener, ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected Response<XmlPullParser> parseNetworkResponse(NetworkResponse response) {
		try {
			String xmlString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xmlPullParser = factory.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlString));
			return Response.success(xmlPullParser, HttpHeaderParser.parseCacheHeaders(response));
		}
		catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
		catch (XmlPullParserException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(XmlPullParser response) {
		mListener.onResponse(response);
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		// self-defined user agent
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-Agent", "android-open-project-analysis/1.0");
		return headerMap;
	}
}
