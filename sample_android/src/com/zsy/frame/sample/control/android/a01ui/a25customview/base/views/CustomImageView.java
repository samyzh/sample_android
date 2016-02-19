package com.zsy.frame.sample.control.android.a01ui.a25customview.base.views;

import com.zsy.frame.sample.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义View，实现圆角，圆形等效果
 */
public class CustomImageView extends View {

	/**
	 * TYPE_CIRCLE / TYPE_ROUND
	 */
	private int type;
	private static final int TYPE_CIRCLE = 0;
	private static final int TYPE_ROUND = 1;

	/** 图片 */
	private Bitmap mSrc;
	/** 圆角的大小 */
	private int mRadius;
	/** 控件的宽度 */
	private int mWidth;
	/** 控件的高度 */
	private int mHeight;

	public CustomImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomImageView(Context context) {
		this(context, null);
	}

	/**
	 * 初始化一些自定义的参数
	 */
	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageView, defStyle, 0);

		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.CustomImageView_borderRadius:
				mRadius = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
						10f, getResources().getDisplayMetrics()));// 默认为10DP
				break;
			case R.styleable.CustomImageView_src:
				mSrc = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
				break;
			case R.styleable.CustomImageView_type:
				type = a.getInt(attr, 0);// 默认为Circle
				break;
			}
		}
		Log.i("Log","mBorderRadius:"+mRadius+"mSrc:"+mSrc+"type:"+type);
		a.recycle();
	}

	/**
	 * 计算控件的高度和宽度
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		/**
		 * 设置宽度
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		if (specMode == MeasureSpec.EXACTLY) {// match_parent , accurate
			mWidth = specSize;
		} else {
			// 由图片决定的宽
			int desireByImg = getPaddingLeft() + getPaddingRight() + mSrc.getWidth();
			if (specMode == MeasureSpec.AT_MOST) {// wrap_content
				mWidth = Math.min(desireByImg, specSize);
			}else{
				mWidth = desireByImg;
			}
		}

		/***
		 * 设置高度
		 */
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		if (specMode == MeasureSpec.EXACTLY) {// match_parent , accurate
			mHeight = specSize;
		} else {
			int desire = getPaddingTop() + getPaddingBottom() + mSrc.getHeight();
			if (specMode == MeasureSpec.AT_MOST) {// wrap_content
				mHeight = Math.min(desire, specSize);
			}else{
				mHeight = desire;
			}
		}
		setMeasuredDimension(mWidth, mHeight);
	}

	/**
	 * 绘制
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		switch (type) {
		// 如果是TYPE_CIRCLE绘制圆形
		case TYPE_CIRCLE:
			int min = Math.min(mWidth, mHeight);
			// 长度如果不一致，按小的值进行压缩
			mSrc = Bitmap.createScaledBitmap(mSrc, min, min, false);

			canvas.drawBitmap(createCircleImage(mSrc, min), 0, 0, null);
			break;
		case TYPE_ROUND:
			canvas.drawBitmap(createRoundConerImage(mSrc), 0, 0, null);
			break;
		}
	}

	/**
	 * 根据原图和变长绘制圆形图片
	 * 
	 * 其实主要靠：paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
	 * 这行代码，为什么呢，我给大家解释下，SRC_IN这种模式，两个绘制的效果叠加后取交集展现后图，怎么说呢，
	 * 咱们第一个绘制的是个圆形，第二个绘制的是个Bitmap，于是交集为圆形，展现的是BItmap，
	 * 就实现了圆形图片效果。圆角，其实就是先绘制圆角矩形，是不是很简单，以后别人再说实现圆角，你就把这一行代码给他就行了。
	 * 
	 * 首先看一下效果图(来自ApiDemos/Graphics/XferModes) 1.PorterDuff.Mode.CLEAR
	 * 
	 * 所绘制不会提交到画布上。 2.PorterDuff.Mode.SRC
	 * 
	 * 显示上层绘制图片 3.PorterDuff.Mode.DST
	 * 
	 * 显示下层绘制图片 4.PorterDuff.Mode.SRC_OVER
	 * 
	 * 正常绘制显示，上下层绘制叠盖。 5.PorterDuff.Mode.DST_OVER
	 * 
	 * 上下层都显示。下层居上显示。 6.PorterDuff.Mode.SRC_IN
	 * 
	 * 取两层绘制交集。显示上层。 7.PorterDuff.Mode.DST_IN
	 * 
	 * 取两层绘制交集。显示下层。 8.PorterDuff.Mode.SRC_OUT
	 * 
	 * 取上层绘制非交集部分。 9.PorterDuff.Mode.DST_OUT
	 * 
	 * 取下层绘制非交集部分。 10.PorterDuff.Mode.SRC_ATOP
	 * 
	 * 取下层非交集部分与上层交集部分 11.PorterDuff.Mode.DST_ATOP
	 * 
	 * 取上层非交集部分与下层交集部分 12.PorterDuff.Mode.XOR
	 * 
	 * 异或：去除两图层交集部分 13.PorterDuff.Mode.DARKEN
	 * 
	 * 取两图层全部区域，交集部分颜色加深 14.PorterDuff.Mode.LIGHTEN
	 * 
	 * 取两图层全部，点亮交集部分颜色 15.PorterDuff.Mode.MULTIPLY
	 * 
	 * 取两图层交集部分叠加后颜色 16.PorterDuff.Mode.SCREEN
	 * 
	 * 取两图层全部区域，交集部分变为透明色
	 * 
	 */
	private Bitmap createCircleImage(Bitmap source, int min) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		//
		Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
		// 产生一个同样大小的画布
		Canvas canvas = new Canvas(target);
		// 首先绘制圆形
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		// 使用SRC_IN，参考上面的说明
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 绘制图片
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}

	/**
	 * 根据原图添加圆角
	 */
	private Bitmap createRoundConerImage(Bitmap source) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		// 这里的Bitmap可以做进一步的优化
		// Bitmap bitmap = mWeakBitmap == null?null:mWeakBitmap.get();
		// mWeakBitmap = new WeakReference<Bitmap>(bitmap);
		Bitmap target = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
		// 产生一个同样大小的画布
		Canvas canvas = new Canvas(target);
		// 首先绘制圆角
		RectF rect = new RectF(0, 0, source.getWidth(), source.getHeight());
		canvas.drawRoundRect(rect, 50f, 50f, paint);

		// 使用SRC_IN，参考上面的说明
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(source, 0, 0, paint);
		// 绘制图片
		return target;
	}

	/**
	 * 如果用户想动态修改图片怎么办，我们在内存里面保存了一个旧的图片，该怎么更新呢。 其实好办，我们只需要做下面的操作就行
	 */
//	public void invalidate() {
//		mWeakBitmap = null;
//		if (mMashBitmap != null) {
//			mMashBitmap.recycle();
//			mMashBitmap = null;
//		}
//		super.invalidate();
//	}
}
