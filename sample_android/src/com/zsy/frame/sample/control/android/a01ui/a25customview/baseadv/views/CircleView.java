//package com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv.views;
//
//
//import android.content.Context;
//import android.content.res.TypedArray;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.util.AttributeSet;
//import android.view.View;
//
//import org.zsy.frame.android.frame.demo.R;
//
//public class CircleView extends View {
//
//    private int mColor = Color.RED;
//    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//
//    public CircleView(Context context) {
//        super(context);
//        init();
//    }
//
//    public CircleView(Context context, AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        //自定义属性；
//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
//        mColor = a.getColor(R.styleable.CircleView_circle_color, Color.RED);
//        a.recycle();
//        init();
//    }
//
//    private void init() {
//        mPaint.setColor(mColor);
//    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
////        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        /**
//         * 解决wrap_content属性，设置默认的宽和高；这里处理的简单，实际中不可这样使用；
//         */
//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
//           setMeasuredDimension(200, 200);
//        } else if (widthSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(200, heightSpecSize);
//        } else if (heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension(widthSpecSize, 200);
//        }
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        /**
//         * 解决padding属性问题；
//         * 中心思想:
//         * 就是在绘制的时候考虑到View的四周的空白即可；
//         * 其中圆心和半径都会考虑到View四周的padding,从而做相应的调整；
//         */
//        final int paddingLeft = getPaddingLeft();
//        final int paddingRight = getPaddingRight();
//        final int paddingTop = getPaddingTop();
//        final int paddingBottom = getPaddingBottom();
//        int width = getWidth() - paddingLeft - paddingRight;
//        int height = getHeight() - paddingTop - paddingBottom;
//        int radius = Math.min(width, height) / 2;
//        canvas.drawCircle(paddingLeft + width / 2, paddingTop + height / 2, radius, mPaint);
//    }
//}
