package com.zsy.frame.sample.control.android.a01ui.a25customview.base.views;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.zsy.frame.sample.R;

/**
 * @description：
 先总结下自定义View的步骤：
1、自定义View的属性
2、在View的构造方法中获得我们自定义的属性
[ 3、重写onMesure ]
4、重写onDraw
 * @author samy
 * @date 2015-3-2 下午4:43:49
 */
public class CustomTitleView extends View {
	/** 文本 */
	private String mTitleText;
	/** 文本的颜色 */
	private int mTitleTextColor;
	/**文本的大小*/
	private int mTitleTextSize;
	/** 绘制时控制文本绘制的范围*/
	private Rect mBound;
	private Paint mPaint;

	/**
	 * 我们重写了3个构造方法，默认的布局文件调用的是两个参数的构造方法，所以记得让所有的构造调用我们的三个参数的构造，我们在三个参数的构造中获得自定义属性。
	 * @param context
	 * @param attrs
	 */
	public CustomTitleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomTitleView(Context context) {
		this(context, null);
	}

	/**
	 * 获得我自定义的样式属性
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomTitleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		/**
		 * 自定义View的属性，首先在res/values/  下建立一个attrs.xml ， 在里面定义我们的属性和声明我们的整个样式。
		 * 获得我们所定义的自定义样式属性
		 */
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
				case R.styleable.CustomTitleView_titleText:
					mTitleText = a.getString(attr);
					break;
				case R.styleable.CustomTitleView_titleTextColor:
					// 默认颜色设置为黑色
					mTitleTextColor = a.getColor(attr, Color.BLACK);
					break;
				case R.styleable.CustomTitleView_titleTextSize:
					// 默认设置为16sp，TypeValue也可以把sp转化为px
					mTitleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
					break;
			}
		}
		// 一定要调用，否则这次的设定会对下次的使用造成影响
		a.recycle();

		/**
		 * 获得绘制文本的宽和高
		 */
		mPaint = new Paint();
		mPaint.setTextSize(mTitleTextSize);
		// mPaint.setColor(mTitleTextColor);
		mBound = new Rect();
		// 计算了描绘字体需要的范围
		mPaint.getTextBounds(mTitleText, 0, mTitleText.length(), mBound);
		// 控件在带点击事件
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mTitleText = randomText();
				postInvalidate();
			}
		});

	}

	/**
	 * @description：生成不重复的四位数字
	 * @author samy
	 * @date 2015-3-21 下午5:22:46
	 */
	private String randomText() {
		Random random = new Random();
		Set<Integer> set = new HashSet<Integer>();
		while (set.size() < 4) {
			int randomInt = random.nextInt(10);
			set.add(randomInt);
		}
		StringBuffer sb = new StringBuffer();
		for (Integer i : set) {
			sb.append("" + i);
		}
		return sb.toString();
	}

	/**
	 * 系统帮我们测量的高度和宽度都是MATCH_PARNET，当我们设置明确的宽度和高度时，系统帮我们测量的结果就是我们设置的结果，当我们设置为WRAP_CONTENT,或者MATCH_PARENT系统帮我们测量的结果就是MATCH_PARENT的长度。
	所以，当设置了WRAP_CONTENT时，我们需要自己进行测量，即重写onMesure方法”：
	重写之前先了解MeasureSpec的specMode,一共三种类型：
	EXACTLY：一般是设置了明确的值或者是MATCH_PARENT
	AT_MOST：表示子布局限制在一个最大值内，一般为WARP_CONTENT
	UNSPECIFIED：表示子布局想要多大就多大，很少使用
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = 0;
		int height = 0;

		/**
		 * 设置宽度
		 */
		int specMode = MeasureSpec.getMode(widthMeasureSpec);
		int specSize = MeasureSpec.getSize(widthMeasureSpec);
		switch (specMode) {
			case MeasureSpec.EXACTLY:// 明确指定了 match_parent , accurate
				width = getPaddingLeft() + getPaddingRight() + specSize;
				break;
			case MeasureSpec.AT_MOST:// 一般为WARP_CONTENT
				width = getPaddingLeft() + getPaddingRight() + mBound.width();
				break;
		}

		/**
		 * 设置高度
		 */
		specMode = MeasureSpec.getMode(heightMeasureSpec);
		specSize = MeasureSpec.getSize(heightMeasureSpec);
		switch (specMode) {
			case MeasureSpec.EXACTLY:// 明确指定了match_parent , accurate
				height = getPaddingTop() + getPaddingBottom() + specSize;
				break;
			case MeasureSpec.AT_MOST:// 一般为WARP_CONTENT
				height = getPaddingTop() + getPaddingBottom() + mBound.height();
				break;
		}

		setMeasuredDimension(width, height);
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		/**
		 * 黄色背景
		 */
		mPaint.setColor(Color.YELLOW);
		// // 消除锯齿
		// mPaint.setAntiAlias(true);
		// // 设置空心
		// mPaint.setStyle(Paint.Style.STROKE);
		// 这个属性是默认的配置
		// mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		// 在画布上显示矩形
		// void android.graphics.Canvas.drawRect(float left, float top, float right, float bottom, Paint paint)
		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
		/**
		 * 黄色背景下的字
		 */
		mPaint.setColor(mTitleTextColor);
		// 在画布上显示字体、
		// void android.graphics.Canvas.drawText(String text, float x, float y, Paint paint)
		canvas.drawText(mTitleText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 + mBound.height() / 2, mPaint);
	}
}
