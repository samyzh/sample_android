package com.zsy.frame.sample.control.android.a18multimedia.camera;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a18multimedia.camera.helps.Utils;

public class SurfaceViewPictureViewAct extends BaseAct {
	private ImageView preview_image;
	private TextView btn_cancel;
	private TextView btn_use;
	private String imagePath;
	private Bitmap bm;

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		preview_image = (ImageView) findViewById(R.id.previewimage);
		btn_cancel = (TextView) findViewById(R.id.cancel_btn);
		btn_use = (TextView) findViewById(R.id.use_btn);
		btn_cancel.setOnClickListener(this);
		btn_use.setOnClickListener(this);
		
		Bundle extra = getIntent().getExtras();
		if (extra != null) {
			imagePath = extra.getString("imagePath");
			File file = new File(imagePath);
			if (file.exists()) {
				// 做压缩处理
				bm = Utils.getBitmapForPath(imagePath, 1);
				preview_image.setImageBitmap(bm);
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel_btn:
				pressBtnCancel();
				break;
			case R.id.use_btn:
				pressBtnUse();
				break;
		}
	}

	private void pressBtnUse() {
		// DefectPhoto photoItem = new DefectPhoto();
		// photoItem.path = imagePath;
		// DBUtils.getInstance(this).saveDefectPhoto(this, photoItem);
		//
		// Intent i = new Intent();
		// i.putExtras(getIntent().getExtras());
		// i.setClass(this, MainActivity.class);
		// startActivity(i);
		finish();
	}

	private void pressBtnCancel() {
		Utils.delSDFile(imagePath);
		finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (bm != null) {
			bm.recycle();
			bm = null;
		}
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a18multimedia_camera_surfaceview_preview_image);
	}

}
