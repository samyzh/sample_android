package com.zsy.frame.sample.control.android.a18multimedia.camera;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a18multimedia.camera.helps.ImageTools;
import com.zsy.frame.sample.control.android.a18multimedia.camera.helps.Utils;

/**
 * 用到一个SurfaceHolder类去实现；使其可以自己开线程处理事务;
 *  SurfaceView和View最本质的区别在于: 
 *  surfaceView是在一个新起的单独线程中可以重新绘制画面而View必须在UI的主线程中更新画面。
 * @date 2014年6月14日 下午3:59:14
 */
public class TakePhotoBySurfaceviewAct extends BaseAct implements SurfaceHolder.Callback {
	public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
	// public static final String imageDir = "/Clover/";
	// public static String imgPath = SDCARD_PATH + "/" + imageDir;
	public static String imgPath = SDCARD_PATH;

	private SurfaceView surfaceView; // 相机画布
	private SurfaceHolder surfaceHolder;
	private ImageView takePicView;
	private Camera mCamera; // 照相机
	private int displayOrientation;

	private File uploadFile;
	
	@Override
	protected void initData() {
		checkSoftStage(); // 首先检测SD卡是否存在，有就创建文件夹
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		/**
		 * 获取Button并且设置事件监听
		 */
		takePicView = (ImageView) this.findViewById(R.id.takepic);
		takePicView.setOnClickListener(TakePicListener);

		surfaceView = (SurfaceView) this.findViewById(R.id.surface_camera);
		surfaceHolder = surfaceView.getHolder();
		// SURFACE_TYPE_PUSH_BUFFERS：表明该Surface不包含原生数据，Surface用到的数据由其他对象提供，
		// 在Camera图像预览中就使用该类型的Surface，有Camera负责提供给预览Surface数据，这样图像预览会比较流畅。
		// 如果设置这种类型则就不能调用lockCanvas来获取Canvas对象了。需要注意的是，在高版本的Android SDK中，setType这个方法已经被depreciated了。
		// 设置该Surface不需要自己维护缓冲区
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		surfaceHolder.addCallback(this);
	}

	/**
	 * 检测手机是否存在SD卡,网络连接是否打开
	 */
	private void checkSoftStage() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) { // 判断是否存在SD卡
			// String rootPath =
			// Environment.getExternalStorageDirectory().getPath(); //获取SD卡的根目录
			File file = new File(imgPath);
			if (!file.exists()) {
				file.mkdir();
			}
		}
		else {
			new AlertDialog.Builder(this).setMessage("检测到手机没有存储卡！请插入手机存储卡再开启本应用。").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			}).show();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_CAMERA) { // 按下相机实体按键，启动本程序照相功能
			mCamera.autoFocus(new AutoFoucus()); // 自动对焦
			return true;
		}
		else {
			finish();
			return false;
		}
	}

	/**
	 * 点击拍照按钮,启动拍照
	 */
	private final OnClickListener TakePicListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			mCamera.autoFocus(new AutoFoucus()); // 自动对焦
		}
	};

	/**
	 * 自动对焦后拍照
	 */
	private final class AutoFoucus implements AutoFocusCallback {
		public void onAutoFocus(boolean success, Camera camera) {
			if (success && mCamera != null) {
				mCamera.takePicture(mShutterCallback, null, mPictureCallback);
			}
		}
	}

	/**
	 * 重点对象、 此处实例化了一个本界面的PictureCallback 当用户拍完一张照片的时候触发，这时候对图片处理并保存操作。
	 */
	private final PictureCallback mPictureCallback = new PictureCallback() {
		
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bMap;
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = 0; // try to decrease decoded image
				options.inPurgeable = true; // purgeable to disk
				bMap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
				Bitmap bMapRotate = null;
				Configuration config = getResources().getConfiguration();
				if (config.orientation == 1) { // 竖拍
					Matrix matrix = new Matrix();
					matrix.reset();
					matrix.postRotate(90);
					bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), matrix, true);
					bMap = bMapRotate;
				}

				String fileName = System.currentTimeMillis() + ".jpg";
				File file = new File(imgPath, fileName);
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
				bMap.compress(Bitmap.CompressFormat.JPEG, 100, bos);// 将图片压缩到流中
				bos.flush();// 输出
				bos.close();// 关闭
				if (bMap != null) {
					bMap.recycle();
					bMap = null;
				}
				if (bMapRotate != null) {
					bMapRotate.recycle();
					bMapRotate = null;
				}

				String cameraImgPath = file.getAbsolutePath();
				uploadFile = ImageTools.saveImgForUpload(cameraImgPath);
				if (!file.getAbsoluteFile().equals(uploadFile.getAbsoluteFile())) {
					// 有压缩时，把原来的大图像素图删除；
					Utils.delSDFile(cameraImgPath);
				}
				// intent.putExtra("data", bitmap)
				Intent intent = new Intent(TakePhotoBySurfaceviewAct.this, SurfaceViewPictureViewAct.class);
				intent.putExtra("imagePath", uploadFile.getPath());
				startActivity(intent);
				finish();

			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}

	};
	/**
	 * 在相机快门关闭时候的回调接口，通过这个接口来通知用户快门关闭的事件， 普通相机在快门关闭的时候都会发出响声，根据需要可以在该回调接口中定义各种动作， 例如：使设备震动
	 */
	private final ShutterCallback mShutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d("ShutterCallback", "...onShutter...");
		}
	};

	private int findBackCamera() {
		int cameraCount = 0;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras(); // get cameras number

		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				// 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
				return camIdx;
			}
		}
		return -1;
	}

	private void setRightCameraOrientation() {
		CameraInfo info = new android.hardware.Camera.CameraInfo();
		Camera.getCameraInfo(findBackCamera(), info);
		int rotation = this.getWindowManager().getDefaultDisplay().getRotation();
		int degrees = 0;
		switch (rotation) {
			case Surface.ROTATION_0:
				degrees = 0;
				break;
			case Surface.ROTATION_90:
				degrees = 90;
				break;
			case Surface.ROTATION_180:
				degrees = 180;
				break;
			case Surface.ROTATION_270:
				degrees = 270;
				break;
		}

		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
			displayOrientation = (info.orientation + degrees) % 360;
			displayOrientation = (360 - displayOrientation) % 360; // compensate the mirror
		}
		else { // back-facing
			displayOrientation = (info.orientation - degrees + 360) % 360;
		}
		mCamera.setDisplayOrientation(displayOrientation);
	}

	/**
	 * 打开相机,设置预览
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// 此处默认打开后置摄像头。
			// 通过传入参数可以打开前置摄像头
			mCamera = Camera.open();
			setRightCameraOrientation();
			
			mCamera.setPreviewDisplay(holder);
		}
		catch (IOException e) {
			mCamera.release();
			mCamera = null;
		}
	}

	/**
	 * 初始化相机参数，比如相机的参数: 像素, 大小,格式
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Camera.Parameters parameters = mCamera.getParameters();
		List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
		int previewWidth = 640;// 1024
		int previewHeight = 480;// 768
		Boolean findSize = false;

		if (sizeList.size() > 1) {
			Iterator<Camera.Size> itor = sizeList.iterator();
			while (itor.hasNext()) {
				Camera.Size cur = itor.next();
				if (cur.width >= previewWidth && cur.height >= previewHeight) {
					findSize = true;
					previewWidth = cur.width;
					previewHeight = cur.height;
					break;
				}
			}
		}
		if (!findSize && sizeList.size() > 5) {
			previewWidth = sizeList.get(5).width;
			previewHeight = sizeList.get(5).height;
		}
		// SP.logD("test", "widthSize=" + previewWidth + "   heigthSize=" + previewHeight);

		// WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		// Display display = manager.getDefaultDisplay();
		// parameters.setPreviewSize(display.getWidth(),display.getHeight());
		// parameters.setPreviewFormat(PixelFormat.YCbCr_422_SP);
		// parameters.setPictureSize(213,350);
		// parameters.set("jpeg-quality",85);
		// parameters.setRotation(90);
		// mCamera.setDisplayOrientation(90);//和下面设置的一样
		// parameters.set("orientation", "portrait");
		// parameters.setFlashMode("off"); // 无闪光灯
		// parameters.setPictureFormat(PixelFormat.JPEG); // Sets the image format for picture 设定相片格式为JPEG，默认为NV21

		parameters.setPreviewSize(previewWidth, previewHeight);
		parameters.setPictureFormat(PixelFormat.JPEG); // 设置图片格式
		parameters.setPreviewSize(previewWidth, previewHeight); // 设置预览大小
		parameters.setPreviewFrameRate(5); // 设置每秒显示4帧
		parameters.setPictureSize(previewWidth, previewHeight); // 设置保存的图片尺寸
		parameters.setJpegQuality(100); // 设置照片质量
		try {
			mCamera.setParameters(parameters);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			// mCamera.release();
			// mCamera = null;
		}
		mCamera.startPreview();// 开始预览
	}

	/**
	 * 预览界面被关闭时，或者停止相机拍摄;释放相机资源
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null); // 这个必须在前，不然退出出错
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a18multimedia_camera_surfaceview_pictureview_takephoto);
	}

}
