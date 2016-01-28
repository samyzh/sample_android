package com.zsy.frame.sample.control.android.a20thirdparty.qrcode;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.zsy.frame.lib.utils.NetUtil;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.camera.CameraManager;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.decoding.CaptureActivityHandler;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.decoding.InactivityTimer;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.util.URIUtil;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.views.ViewfinderView;

/**
 * @description：扫一扫和扫一扫买单界面
 * @author samy
 * @date 2015-2-27 下午3:12:38
 */
public class BarCodeCaptureAct extends BaseBarCodeAct implements Callback {
	// public class BarCodeCaptureAct extends Activity implements Callback {
	public static final String SHOW_TYPE = "SHOW_TYPE";
	public static final int TYPE_URL = 0;
	public static final int TYPE_PAY = 1;

	private int showType;
	// private CaptureActivityHandler handler;
	// private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate; // 是否震动
	public static boolean isConnectNet = false;
	protected TextView mTvRedAmount;
	protected LayoutInflater inflater;
	private Boolean isOpenFlag = false;
	private TextView tb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_a20thirdparty_qrcode_barcode_capture);
		inflater = LayoutInflater.from(BarCodeCaptureAct.this);
		CameraManager.init(getApplication()); //
		// showType = getIntent().getIntExtra(SHOW_TYPE, TYPE_PAY);
		showType = getIntent().getIntExtra(SHOW_TYPE, TYPE_URL);
		// 判断是否是从惠信街过来的
		TextView tv_title = (TextView) findViewById(R.id.scan_tv_title);
		tv_title.setText(showType == TYPE_URL ? "扫一扫" : "扫一扫买单");
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		mTvRedAmount = (TextView) findViewById(R.id.advertise_scan_time_score_tv);
		// ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
		tb = (TextView) findViewById(R.id.to_btn_flash);
		tb.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Drawable topDrawable = null;
				if (!isOpenFlag) {
					topDrawable = getResources().getDrawable(R.drawable.a20thirdparty_qrcode_barcode_icon_flash_close);
					topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
					tb.setCompoundDrawables(null, topDrawable, null, null);
					tb.setText("关闭");
					turnLightOn(CameraManager.get().camera);
				}
				else {
					topDrawable = getResources().getDrawable(R.drawable.a20thirdparty_qrcode_barcode_icon_flash_open);
					topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
					tb.setCompoundDrawables(null, topDrawable, null, null);
					tb.setText("打开");
					turnLightOff(CameraManager.get().camera);
				}
				isOpenFlag = !isOpenFlag;
			}
		});
		findViewById(R.id.scan_imgbtn_left).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				BarCodeCaptureAct.this.finish();
			}
		});
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

		IntentFilter mFilter = new IntentFilter();
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(connectionReceiver, mFilter);
	}

	BroadcastReceiver connectionReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			onNetwork();
		}
	};

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder); // 实例化相机
		}
		else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		onNetwork();
	}

	private void onNetwork() {
		if (NetUtil.isNetworkAvailable(this)) {
			isConnectNet = true;
			decodeFormats = null;
			characterSet = null;
			playBeep = true;
			AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
			if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
				playBeep = false;
			}
			initBeepSound();
			vibrate = true;
		}
		else {
			isConnectNet = false;
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		if (isConnectNet) {
			try {
				URI url = new URI(resultString);
				String schemal = url.getScheme();
				if (showType == TYPE_PAY) {
					if ("hx".equals(schemal)) {
						String host = url.getHost();
						Map<String, String> params = URIUtil.getParams(url);
						if ("pay".equals(host)) {// hx://pay?showAccount=hkamall 扫一扫买单
							Bundle bundle = new Bundle();
							bundle.putInt("tag", 2);
							if (params != null) {
								bundle.putString("merchantAccount", params.get("showAccount"));
							}
							Intent payIntent = new Intent("com.huika.huixin.pay");
							payIntent.putExtras(bundle);
							startActivity(payIntent);
							BarCodeCaptureAct.this.finish();
						}
						else {
							showToast("请扫描商户收银二维码");
							Intent payIntent = new Intent(this, BarCodeCaptureAct.class);
							payIntent.putExtra(SHOW_TYPE, TYPE_PAY);
							startActivity(payIntent);
							BarCodeCaptureAct.this.finish();
						}
					}
					else {
						showToast("请扫描商户收银二维码");
						Intent payIntent = new Intent(this, BarCodeCaptureAct.class);
						payIntent.putExtra(SHOW_TYPE, TYPE_PAY);
						startActivity(payIntent);
						BarCodeCaptureAct.this.finish();
					}
				}
				else {
					if ("http".equals(schemal) || "https".equals(schemal)) {
						Uri uri = Uri.parse(resultString);
						Intent resultIntent = new Intent(Intent.ACTION_VIEW, uri);
						// Intent resultIntent = new Intent(this, WebSrcViewActivity.class);
						// resultIntent.putExtra(WebSrcViewActivity.PAGE_URL,resultString);
						// resultIntent.putExtra(WebSrcViewActivity.PAGE_TITLE,"应用下载");
						// resultIntent.putExtra(WebSrcViewActivity.IS_SHOW_BOTTOM,true);
						startActivity(resultIntent);
						BarCodeCaptureAct.this.finish();
					}
					else if ("hx".equals(schemal)) {// 惠信自定义
						String host = url.getHost();
						Map<String, String> params = URIUtil.getParams(url);
						if ("pay".equals(host)) {// hx://pay?showAccount=hkamall 扫一扫买单
							Bundle bundle = new Bundle();
							bundle.putInt("tag", 2);
							if (params != null) {
								bundle.putString("merchantAccount", params.get("showAccount"));
							}
							Intent payIntent = new Intent("com.huika.huixin.pay");
							payIntent.putExtras(bundle);
							startActivity(payIntent);
							BarCodeCaptureAct.this.finish();
						}
						// // 惠信扫一扫支付
						// public static final String SCAN_HOST_PAY = "hx://pay?showAccount=";
						/*
						 * <activity
						 * android:name="com.huika.huixin.control.pay.activity.PaymentMoneyActivity"
						 * android:screenOrientation="portrait"
						 * android:windowSoftInputMode="adjustResize" >
						 * <intent-filter>
						 * <action android:name="com.huika.huixin.pay" />
						 * <category android:name="android.intent.category.DEFAULT" />
						 * </intent-filter>
						 * </activity>
						 */
					}
					else {
						throw new Exception();
					}
				}
			}
			catch (Exception e) {
				if (TextUtils.isEmpty(resultString)) {
					showToast("Scan failed!");
				}
				else {
					Log.v("barCode", resultString);
					showToast("无效二维码");
				}
				handler.sendEmptyMessage(R.id.decode_failed);
			}
		}
	}

	// fan 2014-9-12 15 修改扫描结果跳转及其toast显示
	private Toast singleToast;

	private void showToast(String msg) {
		if (singleToast == null) {
			singleToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		}
		else {
			singleToast.setText(msg);
		}

		singleToast.show();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		}
		catch (IOException ioe) {
			return;
		}
		catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			}
			catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	/**
	 * setting Camera Turn on flash
	 * 
	 * @param mCamera
	 */
	public static void turnLightOn(Camera mCamera) {
		if (mCamera == null) { return; }
		Parameters parameters = null;
		try {
			parameters = mCamera.getParameters();
		}
		catch (Exception e) {
			return;
		}
		if (parameters == null) { return; }
		// getSupportflashmode
		List<String> flashModes = parameters.getSupportedFlashModes();
		// Check if camera flash exists
		if (flashModes == null) {
			// Use the screen as a flashlight (next best thing)
			return;
		}
		String flashMode = parameters.getFlashMode();
		// Log.i("HUIXIN", "Flash modes: " + flashModes);
		if (!Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
			// Turn on the flash
			if (flashModes.contains(Parameters.FLASH_MODE_TORCH)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
				mCamera.setParameters(parameters);
			}
			else {
			}
		}
	}

	/**
	 * setting Camera closed falsh
	 * 
	 * @param mCamera
	 */
	public static void turnLightOff(Camera mCamera) {
		if (mCamera == null) { return; }
		Parameters parameters = null;
		try {
			parameters = mCamera.getParameters();
		}
		catch (Exception e) {
			return;
		}

		if (parameters == null) { return; }
		List<String> flashModes = parameters.getSupportedFlashModes();
		String flashMode = parameters.getFlashMode();
		// Check if camera flash exists
		if (flashModes == null) { return; }
		// Log.i("HUIXIN", "Flash modes: " + flashModes);
		if (!Parameters.FLASH_MODE_OFF.equals(flashMode)) {
			// Turn off the flash
			if (flashModes.contains(Parameters.FLASH_MODE_OFF)) {
				parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
				mCamera.setParameters(parameters);
			}
			else {
				// Log.e("HUIXIN", "FLASH_MODE_OFF not supported");
			}
		}
	}
}
