package com.zsy.frame.sample.control.android.a14html5.webview;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.zsy.frame.sample.R;

public class WebViewLoadingStyle extends Activity {	
	private WebView webView1;
	// private String html = "<style>p {margin:0px;} img {width:100%;}</style><p><img src=\"http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWAP9T5AAPIXNlm4A8138.jpg\" style=\"\" title=\"商详_01.jpg\"/></p><p><img src=\"http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWATjbaAAXg5rBzWqY771.jpg" style="" title="商详_02.jpg"/></p><p><img src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWAHBYlAAUsfw3c2RE186.jpg" style="" title="商详_03.jpg"/></p><p><img src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWAOheuAAZEblYifDo241.jpg" style="" title="商详_04.jpg"/></p><p><img src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAMqgiAAQUqrq1r6E570.jpg" style="" title="商详_05.jpg"/></p><p><img src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAQfsIAARxG5iu96U233.jpg" style="" title="商详_06.jpg"/></p><p><img
	// src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAKLzhAAQtRGKmWpc270.jpg" style="" title="商详_07.jpg"/></p><p><img src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAOyAKAAMwC5DC44w225.jpg" style="" title="商详_08.jpg"/></p><p><br/></p>";
	// private String html = "<style>p {margin:0px;} img {width:100%;}</style>[]";
//	private String html = "<style>p {margin:0px;} img {width:100%;}</style><p>用坏了就不要来找我，谢谢合作！</p>";
	private String html = "<table><tbody><tr><th colspan=\"2\">主体参数</th></tr><tr><td class=\"td1\">产品品牌</td><td class=\"td2\">乐视TV(Letv)</td></tr></tbody></table>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a14html5_webview_load_style);

		webView1 = (WebView) findViewById(R.id.webview1);

		webView1.loadDataWithBaseURL("about:blank", html, "text/html", "utf-8", null);
	}
}
