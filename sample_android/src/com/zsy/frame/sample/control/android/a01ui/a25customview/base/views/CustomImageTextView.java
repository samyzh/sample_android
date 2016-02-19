package com.zsy.frame.sample.control.android.a01ui.a25customview.base.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.zsy.frame.sample.R;

/**
 * @description：二、组合控件
 * 组合控件的意思就是，我们并不需要自己去绘制视图上显示的内容，而只是用系统原生的控件就好了，
 * 但我们可以将几个系统原生的控件组合到一起，这样创建出的控件就被称为组合控件。
 * @author samy
 * @date 2015-3-22 上午9:51:37
 */
public class CustomImageTextView extends View {
	/** 控件的宽	 */
	private int mWidth;
	/** 控件的高*/
	private int mHeight;
	/** 控件中的图片*/
	private Bitmap mImage;
	/** 图片的缩放模式*/
	private int mImageScale;
	private static final int IMAGE_SCALE_FITXY = 0;
	private static final int IMAGE_SCALE_CENTER = 1;
	/** 图片的介绍*/
	private String mTitle;
	/**字体的颜色	 */
	private int mTextColor;
	/**字体的大小	 */
	private int mTextSize;

	private Paint mPaint;
	
	/**对文本的约束	 */
	private Rect mTextBound;
	/**控制整体布局	 */
	private Rect rect;

	public CustomImageTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomImageTextView(Context context) {
		this(context, null);
	}

	/**
	 * 初始化所特有自定义类型
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomImageTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomImageTextView, defStyle, 0);

		int n = a.getIndexCount();

		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.CustomImageTextView_titleText:
					mTitle = a.getString(attr);
					break;
				case R.styleable.CustomImageTextView_titleTextColor:
					mTextColor = a.getColor(attr, Color.BLACK);
					break;
				case R.styleable.CustomImageTextView_titleTextSize:
					mTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
					break;
				case R.styleable.CustomImageTextView_image:
					mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(attr, 0));
					break;
				case R.styleable.CustomImageTextView_imageScaleType:
					mImageScale = a.getInt(attr, 0);
					break;
			}
		}
		// 一定要调用，否则这次的设定会对下次的使用造成影响
		a.recycle();

		// 在构造中new出对象，初始化数据；
		// 一定要的画笔
		mPaint = new Paint();
		mPaint.setTextSize(mTextSize);

		// 字体显示用到
		mTextBound = new Rect();
		// 计算了描绘字体需要的范围
		mPaint.getTextBounds(mTitle, 0, mTitle.length(), mTextBound);

		// 图片显示用到；
		rect = new Rect();
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/**
		 * 设置宽度
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);

		if (specMode == MeasureSpec.EXACTLY)// match_parent , accurate
		{
			Log.e("xxx", "EXACTLY");
			mWidth = specSize;
		}
		else {
			// 总结对象控件的宽度 = getPaddingLeft() + getPaddingRight() + 控件.getWidth();
			// 由图片决定的宽
			int desireByImg = getPaddingLeft() + getPaddingRight() + mImage.getWidth();
			// 由字体决定的宽
			int desireByTitle = getPaddingLeft() + getPaddingRight() + mTextBound.width();

			if (specMode == MeasureSpec.AT_MOST)// wrap_content
			{
				// 获取图片跟文字的最大值，再跟系统获取的大小比较去最小值，最后才得到宽度的大小；
				int desire = Math.max(desireByImg, desireByTitle);
				mWidth = Math.min(desire, specSize);
				Log.e("xxx", "AT_MOST");
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
			// height = getPaddingTop() + getPaddingBottom() + specSize;
			mHeight = specSize;
		}
		else {
			int desire = getPaddingTop() + getPaddingBottom() + mImage.getHeight() + mTextBound.height();
			if (specMode == MeasureSpec.AT_MOST)// wrap_content
			{
				mHeight = Math.min(desire, specSize);
			}else{
				mHeight = desire;
			}
		}
		setMeasuredDimension(mWidth, mHeight);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		// super.onDraw(canvas);
		/**
		 * 控件外围边框
		 */
		mPaint.setStrokeWidth(10);
		// 消除锯齿
		mPaint.setAntiAlias(true);
		// 设置空心
		mPaint.setStyle(Paint.Style.STROKE);
		// 后面设置的样式，全部填充样式。会导致不能体现出有边框
		// mPaint.setStyle(Paint.Style.FILL);
		// mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mPaint.setColor(Color.BLUE);
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

		/**
		 * 一般涉及到图片的，会用到这个属性
		 */
		rect.left = getPaddingLeft();
		rect.right = mWidth - getPaddingRight();
		rect.top = getPaddingTop();
		rect.bottom = mHeight - getPaddingBottom();

		mPaint.setColor(mTextColor);
		mPaint.setStyle(Style.FILL);

		/**
		 * 当前设置的宽度小于字体需要的宽度，将字体改为xxx...
		 * 代码控制文字超过边框，显示省略号
		 */
		if (mTextBound.width() > mWidth) {
			TextPaint paint = new TextPaint(mPaint);
			String msg = TextUtils.ellipsize(mTitle, paint, (float) mWidth - getPaddingLeft() - getPaddingRight(), TextUtils.TruncateAt.END).toString();
			canvas.drawText(msg, getPaddingLeft(), mHeight - getPaddingBottom(), mPaint);

		}
		else {
			// 正常情况，将字体居中
			canvas.drawText(mTitle, mWidth / 2 - mTextBound.width() * 1.0f / 2, mHeight - getPaddingBottom(), mPaint);
		}

		// 取消使用掉的快
		rect.bottom -= mTextBound.height();

		/**
		 * 图片的两种显示类型
		 *    <attr name="imageScaleType">
		<enum name="fillXY" value="0" />
		<enum name="center" value="1" />
		</attr>
		 */
		if (mImageScale == IMAGE_SCALE_FITXY) {
			canvas.drawBitmap(mImage, null, rect, mPaint);
		}
		else {
			// 计算居中的矩形范围
			rect.left = mWidth / 2 - mImage.getWidth() / 2;
			rect.right = mWidth / 2 + mImage.getWidth() / 2;
			rect.top = (mHeight - mTextBound.height()) / 2 - mImage.getHeight() / 2;
			rect.bottom = (mHeight - mTextBound.height()) / 2 + mImage.getHeight() / 2;

			canvas.drawBitmap(mImage, null, rect, mPaint);
		}
	}

}
