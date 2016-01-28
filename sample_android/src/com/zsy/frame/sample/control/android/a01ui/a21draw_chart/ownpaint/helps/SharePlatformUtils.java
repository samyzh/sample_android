package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.helps;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.tencent.weibo.TencentWeibo;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans.Information;

/**
* Created by Jenny.Hu
*/
public class SharePlatformUtils {
	public static void selectPlatform(Context context, String text, PlatformActionListener shareListener) {
		Information info = new Information();
		info.setInfo_content(text);
		selectPlatform(context, info, shareListener);
	}

	public static void selectPlatform(final Context context, final Information info, final PlatformActionListener shareListener) {
		final String[] platformValue = new String[] { "QQ", "Wechat", "WechatMoments", "TencentWeibo", "QZone", "SinaWeibo" };
		View view = View.inflate(context, R.layout.view_a01ui_a21draw_chart_ownpaint_bodydata_ui_share_channel, null);
		final AlertDialog dialog = new AlertDialog.Builder(context, R.style.ShareDialog).setView(view).setCancelable(true).create();
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		for (int i = 0; i < 6; i++) {
			View btnView = view.findViewWithTag(String.valueOf(i));
			btnView.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					int which = Integer.parseInt(v.getTag().toString());
					switch (which) {
						case 0:
							qqShare(context, platformValue[which], info, shareListener);
							break;
						case 1:
							wechatShare(context, platformValue[which], info, shareListener);
							break;
						case 2:
							wechatMomentShare(context, platformValue[which], info, shareListener);
							break;
						case 3:
							tencentWeiBoShare(context, platformValue[which], info, shareListener);
							break;
						case 4:
							qZoneShare(context, platformValue[which], info, shareListener);
							break;
						case 5:
							sinaWeiBoShare(context, platformValue[which], info, shareListener);
							break;
						default:
							break;
					}
					dialog.dismiss();
				}
			});
		}
	}

	private static void wechatShare(Context context, String platform, Information info, final PlatformActionListener shareListener) {
		Wechat.ShareParams sp = new Wechat.ShareParams();
		sp.setTitle(info.getInfo_title());

		sp.setText(info.getInfo_content());
		// sp.setShareType(Platform.SHARE_IMAGE);
		Bitmap imageData = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		sp.setImageData(imageData);
		Platform plat = ShareSDK.getPlatform(platform);
		plat.share(sp);
	}

	private static void wechatMomentShare(Context context, String platform, Information info, final PlatformActionListener shareListener) {
		WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
		sp.setTitle(info.getInfo_title());
		sp.setText(info.getInfo_content());
		// sp.setShareType(Platform.SHARE_IMAGE);
		Bitmap imageData = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		sp.setImageData(imageData);
		Platform plat = ShareSDK.getPlatform(platform);
		plat.share(sp);
	}

	private static void qqShare(Context context, String platform, Information info, final PlatformActionListener shareListener) {
		QQ.ShareParams sp = new QQ.ShareParams();
		sp.setTitle(info.getInfo_title());
		sp.setText(info.getInfo_content());
		// sp.setShareType(Platform.SHARE_IMAGE);
		Bitmap imageData = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		sp.setImageData(imageData);
		Platform plat = ShareSDK.getPlatform(platform);
		plat.share(sp);
	}

	private static void tencentWeiBoShare(Context context, String platform, Information info, final PlatformActionListener shareListener) {
		TencentWeibo.ShareParams sp = new TencentWeibo.ShareParams();
		sp.setTitle(info.getInfo_title());
		sp.setText(info.getInfo_content());
		// sp.setShareType(Platform.SHARE_IMAGE);
		// Bitmap imageData = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		// sp.setImageData(imageData);
		Platform plat = ShareSDK.getPlatform(platform);
		plat.share(sp);
	}

	private static void qZoneShare(Context context, String platform, Information info, final PlatformActionListener shareListener) {
		QZone.ShareParams sp = new QZone.ShareParams();
		sp.setTitle(info.getInfo_title());
		sp.setText(info.getInfo_content());
		// sp.setShareType(Platform.SHARE_IMAGE);
		sp.setSiteUrl("http://sharesdk.cn");
		// Bitmap imageData = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		// sp.setImageData(imageData);
		Platform plat = ShareSDK.getPlatform(platform);
		plat.share(sp);
	}

	private static void sinaWeiBoShare(Context context, String platform, Information info, final PlatformActionListener shareListener) {

		SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
		sp.setTitle(info.getInfo_title());
		sp.setText(info.getInfo_content());
		// sp.setShareType(Platform.SHARE_IMAGE);
		// Bitmap imageData = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
		// sp.setImageData(imageData);
		Platform plat = ShareSDK.getPlatform(platform);
		plat.setPlatformActionListener(shareListener);
		plat.share(sp);
	}
}
