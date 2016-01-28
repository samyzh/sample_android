package com.zsy.frame.sample.control.android.a14html5.webview;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

public class WebViewJsMutual extends BaseAct {
	/** 浏览器 */
	private WebView mAdvertiseUrlDetailWv;
	private ProgressBar mPbar;
	// private String tempurl = "file:///mnt/sdcard/webview_demo/jsurvey/test.html";
	// private String tempurl = "file:///android_asset/icon.png";
	private String tempurl = "file:///android_asset/hkb_detail_show.html";

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a14html5_webview_webviewjs_mutual);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);

		// if (!advertiseInfo.getAdvertisementLink().startsWith("http://")) {
		// tempurl = "http://" + advertiseInfo.getAdvertisementLink();
		// }
		mAdvertiseUrlDetailWv = (WebView) findViewById(R.id.advertise_url_detail_wv);
		mPbar = (ProgressBar) findViewById(R.id.advertise_pb);
		mAdvertiseUrlDetailWv.loadUrl(tempurl);
		// mAdvertiseUrlDetailWv.loadUrl(url);
		mAdvertiseUrlDetailWv.addJavascriptInterface(new MyJavaScriptInterface(), "huixinMember");
		mAdvertiseUrlDetailWv.setDownloadListener(new DownloadListener() {// 下载
					public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
						Uri uri = Uri.parse(url);
						Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						startActivity(intent);
					}
				});
		mAdvertiseUrlDetailWv.setWebViewClient(new MyWebViewClient());
		mAdvertiseUrlDetailWv.setWebChromeClient(new MyWebChromeClient());

		WebSettings wSet = mAdvertiseUrlDetailWv.getSettings();
		wSet.setJavaScriptEnabled(true);
		// wSet.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
		// wSet.setBuiltInZoomControls(true);
	}

	/**
	 * @description：判断是否有app
	 * @author shicm
	 * @date 2014年9月16日 上午9:48:28
	 */
	private boolean isAvilible(Context context, String packageName) {
		PackageManager packageManager = context.getPackageManager();// 获取packagemanager
		List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
		List<String> pName = new ArrayList<String>();// 用于存储所有已安装程序的包名
		if (pinfo != null) {
			for (int i = 0; i < pinfo.size(); i++) {
				String pn = pinfo.get(i).packageName;
				pName.add(pn);
			}
		}
		return pName.contains(packageName);// 判断pName中是否有目标程序的包名，有TRUE，没有FALSE
	}

	final class MyJavaScriptInterface {
		MyJavaScriptInterface() {
		}

		/**
		 * @description：立即体验跳转到惠卡宝里面显示
		 * @author samy
		 * @date 2014年8月19日 上午9:19:21
		 */
		@JavascriptInterface
		public void jumpToPage(String url) {
			if (url.equals("experienceHKB")) {
				showToastMsg("您点击了experienceHKB");
				// jumpToActivity(BCTHomeActivity.class, false);
			}
			// else if (advertiseInfo.getType() == 2) {
			// 1、发送网络请求告诉服务器我点击了下载
			// 2、超链接过去下载
			// 3、下载完了看是否安装
			// 4、安装了直接发出广播
			// 5、发出广播的同时请求网络邀请给钱奖励
			// if (isAvilible(WebViewJsMutual.this, advertiseInfo.getAndroidForPN())) {
			showToastMsg("您已经安装了此应用,下载将没有奖励");
			// MoneyUIHelp.openCLD(advertiseInfo.getAndroidForPN(), context);
			// } else {
			// requestRecordApk();
			// Uri uri = Uri.parse(advertiseInfo.getDownloadLinkAndroid());
			// Intent resultIntent = new Intent(Intent.ACTION_VIEW, uri);
			// startActivity(resultIntent);
			// }
			// }
			// else {
			//
			// }
		}

	}

	private class MyWebViewClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			String website = Uri.parse(url).getHost();
			mPbar.setVisibility(View.VISIBLE);
			// if (COMPANY_WEB.equals(website)) {
			// // This is my web site, so do not override; let my WebView load the page
			// return false;
			// } else {
			view.loadUrl(url);
			return true;
			// }
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			mPbar.setVisibility(View.GONE);
			super.onPageFinished(view, url);
		}
	}

	/**
	 * @description：用于辅助WebView，处理JavaScript的对话框、网站图标、网站标题、加载进度等
	 * @author samy
	 * @date 2014年8月6日 下午3:39:37
	 */
	public class MyWebChromeClient extends WebChromeClient {
		@Override
		public void onProgressChanged(WebView view, int newProgress) {
			if (newProgress == 100) {
				mPbar.setVisibility(View.GONE);
			}
			else {
				if (mPbar.getVisibility() == View.GONE) {
					mPbar.setVisibility(View.VISIBLE);
				}
				mPbar.setProgress(newProgress);
			}
			super.onProgressChanged(view, newProgress);
		}
	}

}
