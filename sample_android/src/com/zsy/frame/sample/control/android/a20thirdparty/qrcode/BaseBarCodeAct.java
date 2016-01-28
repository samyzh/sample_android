package com.zsy.frame.sample.control.android.a20thirdparty.qrcode;

import com.google.zxing.Result;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.decoding.CaptureActivityHandler;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.views.ViewfinderView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;

/**
 * @description：方便各个调用二维码类那个Handle(临时处理方法)
 * @author samy
 * @date 2015-2-27 下午5:37:55
 */
public abstract class BaseBarCodeAct extends Activity {
	protected CaptureActivityHandler handler;
	protected ViewfinderView viewfinderView;

	// public Handler getHandler() {
	// return handler;
	// }
	//
	// public ViewfinderView getViewfinderView() {
	// return viewfinderView;
	// }
	//
	// public void drawViewfinder() {
	// viewfinderView.drawViewfinder();
	// }

	public abstract Handler getHandler();

	public abstract ViewfinderView getViewfinderView();

	public abstract void drawViewfinder();

	public abstract void handleDecode(Result result, Bitmap barcode);
}
