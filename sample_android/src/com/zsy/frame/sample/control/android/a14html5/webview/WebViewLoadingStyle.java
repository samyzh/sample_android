package com.zsy.frame.sample.control.android.a14html5.webview;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.ZoomDensity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zsy.frame.sample.R;

public class WebViewLoadingStyle extends Activity {
	private WebView webView1;
	// private String html = "<style>p {margin:0px;} img
	// {width:100%;}</style><p><img
	// src=\"http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWAP9T5AAPIXNlm4A8138.jpg\"
	// style=\"\" title=\"商详_01.jpg\"/></p><p><img
	// src=\"http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWATjbaAAXg5rBzWqY771.jpg"
	// style="" title="商详_02.jpg"/></p><p><img
	// src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWAHBYlAAUsfw3c2RE186.jpg"
	// style="" title="商详_03.jpg"/></p><p><img
	// src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BWAOheuAAZEblYifDo241.jpg"
	// style="" title="商详_04.jpg"/></p><p><img
	// src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAMqgiAAQUqrq1r6E570.jpg"
	// style="" title="商详_05.jpg"/></p><p><img
	// src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAQfsIAARxG5iu96U233.jpg"
	// style="" title="商详_06.jpg"/></p><p><img
	// src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAKLzhAAQtRGKmWpc270.jpg"
	// style="" title="商详_07.jpg"/></p><p><img
	// src="http://192.168.16.218/pm1/M00/01/03/wKgQ2lV37BaAOyAKAAMwC5DC44w225.jpg"
	// style="" title="商详_08.jpg"/></p><p><br/></p>";
	// private String html = "<style>p {margin:0px;} img
	// {width:100%;}</style>[]";
	// private String html = "<style>p {margin:0px;} img
	// {width:100%;}</style><p>用坏了就不要来找我，谢谢合作！</p>";
	 private String html = "<table><tbody><tr><th colspan=\"2\">主体参数</th></tr><tr><td class=\"td1\">产品品牌</td><td class=\"td2\">乐视TV(Letv)</td></tr></tbody></table>";

	// private String htmlContent = "<p style=\ "text-indent:28px\"><span
	// style=\ "font-size: 40px;\"><span style=\"font-size: 40px; font-family:
	// 宋体;\">在</span>4<span style=\ "font-size: 40px; font-family:
	// 宋体;\">月所有店员的努力下，成都店护肤做到了</span>7<span style=\ "font-size: 40px;
	// font-family: 宋体;\">万元以上，彩妆在</span>3
	// <span style=\ "font-size: 40px; font-family:
	// 宋体;\">万元以上。团队里每个人都比较喜欢护肤多一些，包括自己都会使用店里的产品，所以一直以来护肤卖的比较好，这样可以让店员的潜意识里信任千千氏的产品，只有信任千千氏的产品才会有自信的跟顾客沟通，打开顾客的心防，无形中顾客就会在自己使用后做宣传。口碑的力量是无限的。</span>
	// </span>
	// </p>";
	// private String html = "<style>p {margin:0px;} img
	// {width:100%;}</style><p><img
	// src=\"http://qqs.oss-cn-shenzhen.aliyuncs.com/crm/datas/image/20170612/crm_e424ffda37dc483c869a4ff7da91a44d..jpg?x-oss-process=image/resize,w_1100\"
	// style=\"\" title=\"商详_01.jpg\"/></p>";
	// private String html = "<p style=\ "text-indent:28px\"><span style=\
	// "font-size: 40px;\"><span style=\"font-size: 40px; font-family:
	// 宋体;\">在</span>4<span style=\ "font-size: 40px; font-family:
	// 宋体;\">月所有店员的努力下，成都店护肤做到了</span>7<span style=\ "font-size: 40px;
	// font-family: 宋体;\">万元以上，彩妆在</span>3
	// <span style=\ "font-size: 40px; font-family:
	// 宋体;\">万元以上。团队里每个人都比较喜欢护肤多一些，包括自己都会使用店里的产品，所以一直以来护肤卖的比较好，这样可以让店员的潜意识里信任千千氏的产品，只有信任千千氏的产品才会有自信的跟顾客沟通，打开顾客的心防，无形中顾客就会在自己使用后做宣传。口碑的力量是无限的。</span>
	// </span>
	// </p>";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a14html5_webview_load_style);

		webView1 = (WebView) findViewById(R.id.webview1);
		WebSettings webSettings = webView1.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		//这几个配置最重要；
		webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		webSettings.setUseWideViewPort(true);
		webSettings.setLoadWithOverviewMode(true);

		// User settings

//		webSettings.setJavaScriptEnabled(true);
//		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//		webSettings.setUseWideViewPort(true);// 关键点
//
//		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//		webSettings.setDisplayZoomControls(false);
//		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//		webSettings.setAllowFileAccess(true); // 允许访问文件
//		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
//		webSettings.setSupportZoom(true); // 支持缩放
//
//		webSettings.setLoadWithOverviewMode(true);
//
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		int mDensity = metrics.densityDpi;
//		Log.d("samy", "densityDpi = " + mDensity);
//		if (mDensity == 240) {
//			webSettings.setDefaultZoom(ZoomDensity.FAR);
//		} else if (mDensity == 160) {
//			webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
//		} else if (mDensity == 120) {
//			webSettings.setDefaultZoom(ZoomDensity.CLOSE);
//		} else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
//			webSettings.setDefaultZoom(ZoomDensity.FAR);
//		} else if (mDensity == DisplayMetrics.DENSITY_TV) {
//			webSettings.setDefaultZoom(ZoomDensity.FAR);
//		} else {
//			webSettings.setDefaultZoom(ZoomDensity.MEDIUM);
//		}
//
//		/**
//		 * 用WebView显示图片，可使用这个参数 设置网页布局类型： 1、LayoutAlgorithm.NARROW_COLUMNS ：
//		 * 适应内容大小 2、LayoutAlgorithm.SINGLE_COLUMN:适应屏幕，内容将自动缩放
//		 */
//		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);

		 webView1.loadDataWithBaseURL("about:blank", html, "text/html","utf-8", null);
//		webView1.loadUrl("http://192.168.9.251:8888/src/ui/ztest/InformationTest.html");
	}
}
