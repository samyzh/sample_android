package com.zsy.frame.sample.control.android.a01ui.a25customview.advance.views;

import java.text.DecimalFormat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * @description：计步器加载圈圈
 * 
 * @author samy
 * @date 2015-3-2 下午4:11:12
 */
public class CircleBar extends View {
	private RectF mColorWheelRectangle = new RectF();
	/**	灰色背景	（圈子的最下面的颜色*/
	private Paint mDefaultWheelPaint;
	/**白色背景(圆圈没有加载时背景)*/
	private Paint mColorWheelPaintCentre;
	/**粉红色圈边（圈子的加载时完成颜色）*/
	private Paint mColorWheelPaint;
	/**步数百分比*/
	private Paint mTextP;
	/**步数数量*/
	private Paint mTextnum;
	/**步数文字*/
	private Paint mTextch;
	private float circleStrokeWidth;
	private float mSweepAnglePer;
	private float mPercent;
	private int stepnumber, stepnumbernow;
	private float pressExtraStrokeWidth;
	private BarAnimation anim;
	private int stepnumbermax = 6000;// 默认最大步数
	private float mPercent_y, stepnumber_y, Text_y;
	private DecimalFormat fnum = new DecimalFormat("#.0");// 格式为保留小数点后一位

	public CircleBar(Context context) {
		super(context);
		init(null, 0);
	}

	public CircleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public CircleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}

	private void init(AttributeSet attrs, int defStyle) {
		/**
		 * 这个自定义的控件，用了多个画笔，这个设计的不好，可以多个公用一个画笔
		 */

		// 灰色背景 （圈子的底边）
		mDefaultWheelPaint = new Paint();
		mDefaultWheelPaint.setColor(Color.BLACK);
		// mDefaultWheelPaint.setColor(Color.rgb(127, 127, 127));
		mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
		mDefaultWheelPaint.setStrokeCap(Paint.Cap.ROUND);
		mDefaultWheelPaint.setAntiAlias(true);

		// 白色背景
		mColorWheelPaintCentre = new Paint();
		mColorWheelPaintCentre.setColor(Color.GREEN);
		// mColorWheelPaintCentre.setColor(Color.rgb(250, 250, 250));
		mColorWheelPaintCentre.setStyle(Paint.Style.STROKE);
		mColorWheelPaintCentre.setStrokeCap(Paint.Cap.ROUND);
		mColorWheelPaintCentre.setAntiAlias(true);

		// 粉红色圈边（圈子的加载时和完成颜色）
		mColorWheelPaint = new Paint();
		mColorWheelPaint.setColor(Color.RED);
		// mColorWheelPaint.setColor(Color.rgb(249, 135, 49));
		mColorWheelPaint.setStyle(Paint.Style.STROKE);// 空心
		mColorWheelPaint.setStrokeCap(Paint.Cap.ROUND);// 圆角画笔
		mColorWheelPaint.setAntiAlias(true);// 去锯齿

		// 步数百分比,步数数量,步数文字
		mTextP = new Paint();
		mTextP.setAntiAlias(true);
		mTextP.setColor(Color.rgb(249, 135, 49));

		// 步数数量
		mTextnum = new Paint();
		mTextnum.setAntiAlias(true);
		mTextnum.setColor(Color.BLACK);

		// 步数文字
		mTextch = new Paint();
		mTextch.setAntiAlias(true);
		mTextch.setColor(Color.BLACK);

		anim = new BarAnimation();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
		int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);

		// 获取View最短边的长度
		int min = Math.min(width, height);
		// 强制改View为以最短边为长度的正方形
		setMeasuredDimension(min, min);
		// 圆弧的宽度
		circleStrokeWidth = Textscale(40, min);
		// 圆弧离矩形的距离
		pressExtraStrokeWidth = Textscale(2, min);
		// 设置矩形
		mColorWheelRectangle.set(circleStrokeWidth + pressExtraStrokeWidth, circleStrokeWidth + pressExtraStrokeWidth, min - circleStrokeWidth - pressExtraStrokeWidth, min - circleStrokeWidth - pressExtraStrokeWidth);

		mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth - Textscale(2, min));
		// 设置阴影
		mDefaultWheelPaint.setShadowLayer(Textscale(10, min), 0, 0, Color.BLACK);
		// mDefaultWheelPaint.setShadowLayer(Textscale(10, min), 0, 0, Color.rgb(127, 127, 127));
		mColorWheelPaintCentre.setStrokeWidth(circleStrokeWidth);
		mColorWheelPaint.setStrokeWidth(circleStrokeWidth);

		mTextP.setTextSize(Textscale(80, min));
		mTextnum.setTextSize(Textscale(160, min));
		mTextch.setTextSize(Textscale(50, min));

		mPercent_y = Textscale(190, min);
		stepnumber_y = Textscale(330, min);
		Text_y = Textscale(400, min);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawArc(mColorWheelRectangle, 0, 359, false, mDefaultWheelPaint);
		canvas.drawArc(mColorWheelRectangle, 0, 359, false, mColorWheelPaintCentre);
		// 中间进度条;从底部90开始
		canvas.drawArc(mColorWheelRectangle, 90, mSweepAnglePer, false, mColorWheelPaint);

		canvas.drawText(mPercent + "%", mColorWheelRectangle.centerX() - (mTextP.measureText(String.valueOf(mPercent) + "%") / 2), mPercent_y, mTextP);
		
		/**
		 * 可以参照Advance中的RoundProgressBar
		 */
		canvas.drawText(stepnumbernow + "", mColorWheelRectangle.centerX() - (mTextnum.measureText(String.valueOf(stepnumbernow)) / 2), stepnumber_y, mTextnum);
		canvas.drawText("步数", mColorWheelRectangle.centerX() - (mTextch.measureText(String.valueOf("步数")) / 2), Text_y, mTextch);
	}

	/**
	 * 进度条动画
	 */
	public class BarAnimation extends Animation {
		public BarAnimation() {

		}

		/**
		 * 每次系统调用这个方法时， 改变mSweepAnglePer，mPercent，stepnumbernow的值，
		 * 然后调用postInvalidate()不停的绘制view。
		 */
		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				mPercent = Float.parseFloat(fnum.format(interpolatedTime * stepnumber * 100f / stepnumbermax));// 将浮点值四舍五入保留一位小数
				mSweepAnglePer = interpolatedTime * stepnumber * 360 / stepnumbermax;
				stepnumbernow = (int) (interpolatedTime * stepnumber);
			}
			else {
				mPercent = Float.parseFloat(fnum.format(stepnumber * 100f / stepnumbermax));// 将浮点值四舍五入保留一位小数
				mSweepAnglePer = stepnumber * 360 / stepnumbermax;
				stepnumbernow = stepnumber;
			}
			postInvalidate();
		}
	}

	/**
	 * 根据控件的大小改变绝对位置的比例
	 */
	public float Textscale(float n, float m) {
		return n / 500 * m;
	}

	/**
	 * 更新步数和设置一圈动画时间
	 */
	public void update(int stepnumber, int time) {
		this.stepnumber = stepnumber;
		anim.setDuration(time);
		// setAnimationTime(time);
		this.startAnimation(anim);
	}

	/**
	 * 设置每天的最大步数
	 */
	public void setMaxstepnumber(int Maxstepnumber) {
		stepnumbermax = Maxstepnumber;
	}

	/**
	 * 设置进度条颜色
	 */
	public void setColor(int red, int green, int blue) {
		mColorWheelPaint.setColor(Color.rgb(red, green, blue));
	}

	/**
	 * 设置动画时间
	 */
	public void setAnimationTime(int time) {
		anim.setDuration(time * stepnumber / stepnumbermax);// 按照比例设置动画执行时间
	}

}
