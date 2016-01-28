package com.zsy.frame.sample.control.android.a01ui.a12gallery.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a12gallery.views.Gallery3D;

public class Gallery3DAct extends BaseAct implements OnItemClickListener, OnItemSelectedListener {
	protected DisplayMetrics display;

	private Integer[] images = { R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher, R.drawable.ic_launcher };;
	private int select = 0;

	private GalleryAdapter adapter;
	private Gallery3D gallery3D;
	private TextView devicesName;

	@Override
	protected void initData() {
		super.initData();
		display = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(display);

	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a12gallery_gallery3d);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		gallery3D = (Gallery3D) this.findViewById(R.id.devices_management_gallery);
		gallery3D.setFadingEdgeLength(0);
		gallery3D.setSpacing(-display.widthPixels / 5);
		gallery3D.setOnItemClickListener(this);
		gallery3D.setOnItemSelectedListener(this);

		devicesName = (TextView) super.findViewById(R.id.devices_management_name);
		devicesName.setMaxWidth(display.widthPixels / 2);

		adapter = new GalleryAdapter(this);
		adapter.createReflectedImages();
		gallery3D.setAdapter(adapter);
		gallery3D.setSelection(select);

	}

	class GalleryAdapter extends BaseAdapter {

		private Context mContext;

		private ImageView[] mImages;

		public GalleryAdapter(Context c) {
			mContext = c;
			mImages = new ImageView[images.length];
		}

		public boolean createReflectedImages() {
			final int reflectionGap = 4;
			int index = 0;

			for (int imageId : images) {
				Bitmap originalImage = BitmapFactory.decodeResource(mContext.getResources(), imageId);
				int width = originalImage.getWidth();
				int height = originalImage.getHeight();

				Matrix matrix = new Matrix();
				matrix.preScale(1, -1);

				Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
				Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);

				Canvas canvas = new Canvas(bitmapWithReflection);
				canvas.drawBitmap(originalImage, 0, 0, null);

				Paint deafaultPaint = new Paint();
				deafaultPaint.setAntiAlias(false);
				canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

				Paint paint = new Paint();
				paint.setAntiAlias(false);

				LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.MIRROR);
				paint.setShader(shader);
				paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));
				canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

				ImageView imageView = new ImageView(mContext);
				// StaticImage imageView = new StaticImage(mContext);
				// imageView.setBackground(bitmapWithReflection);
				imageView.setLayoutParams(new Gallery3D.LayoutParams(display.widthPixels * 2 / 3, display.widthPixels * 2 / 3));
				mImages[index++] = imageView;
				originalImage.recycle();
				reflectionImage.recycle();
			}
			return true;
		}

		public int getCount() {
			return mImages.length;
		}

		public Object getItem(int position) {
			return mImages[position];
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			return mImages[position];
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// devicesName.setText(c.getString(DeviceTable.DB.displayName.ordinal()));
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {

	}

}
