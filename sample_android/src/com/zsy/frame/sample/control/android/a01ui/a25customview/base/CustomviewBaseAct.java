package com.zsy.frame.sample.control.android.a01ui.a25customview.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

/**
 * @description：任何一个视图都不可能凭空突然出现在屏幕上，它们都是要经过非常科学的绘制流程后才能显示出来的。
 * 每一个视图的绘制过程都必须经历三个最主要的阶段，即onMeasure()、onLayout()和onDraw()，下面我们逐个对这三个阶段展开进行探讨。
 * 
一. onMeasure()
	measure是测量的意思，那么onMeasure()方法顾名思义就是用于测量视图的大小的。View系统的绘制流程会从ViewRoot的performTraversals()方法中开始，
	在其内部调用View的measure()方法。measure()方法接收两个参数，widthMeasureSpec和heightMeasureSpec，这两个值分别用于确定视图的宽度和高度的规格和大小。
	MeasureSpec的值由specSize和specMode共同组成的，其中specSize记录的是大小，specMode记录的是规格。specMode一共有三种类型，如下所示：
	1. EXACTLY
	表示父视图希望子视图的大小应该是由specSize的值来决定的，系统默认会按照这个规则来设置子视图的大小，开发人员当然也可以按照自己的意愿设置成任意的大小。
	2. AT_MOST
	表示子视图最多只能是specSize中指定的大小，开发人员应该尽可能小得去设置这个视图，并且保证不会超过specSize。
	系统默认会按照这个规则来设置子视图的大小，开发人员当然也可以按照自己的意愿设置成任意的大小。
	3. UNSPECIFIED
	表示开发人员可以将视图按照自己的意愿设置成任意的大小，没有任何限制。这种情况比较少见，不太会用到。
	那么你可能会有疑问了，widthMeasureSpec和heightMeasureSpec这两个值又是从哪里得到的呢？通常情况下，
	这两个值都是由父视图经过计算后传递给子视图的，说明父视图会在一定程度上决定子视图的大小。
	但是最外层的根视图，它的widthMeasureSpec和heightMeasureSpec又是从哪里得到的呢？这就需要去分析ViewRoot中的源码了，观察performTraversals()方法可以发现如下代码：
	
	 如：重写ListView;达到使ListView适应ScrollView的效果;
	重写该方法，达到使ListView适应ScrollView的效果                            
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

二. onLayout()
    measure过程结束后，视图的大小就已经测量好了，接下来就是layout的过程了。正如其名字所描述的一样，这个方法是用于给视图进行布局的，也就是确定视图的位置。
    ViewRoot的performTraversals()方法会在measure结束后继续执行，并调用View的layout()方法来执行此过程，如下所示：
    host.layout(0, 0, host.mMeasuredWidth, host.mMeasuredHeight);  
    layout()方法接收四个参数，分别代表着左、上、右、下的坐标，当然这个坐标是相对于当前视图的父视图而言的。
         可以看到，这里还把刚才测量出的宽度和高度传到了layout()方法中。那么我们来看下layout()方法中的代码是什么样的吧，如下所示：
         
三. onDraw()
	measure和layout的过程都结束后，接下来就进入到draw的过程了。同样，根据名字你就能够判断出，在这里才真正地开始对视图进行绘制。
	ViewRoot中的代码会继续执行并创建出一个Canvas对象，然后调用View的draw()方法来执行具体的绘制工作。
	draw()方法内部的绘制过程总共可以分为六步，其中第二步和第五步在一般情况下很少用到，因此这里我们只分析简化后的绘制过程。
	
	
如果说要按类型来划分的话，自定义View的实现方式大概可以分为三种:
1:自绘控件;
2:组合控件;(其实这里可以组合成一个公共的TitleBar自定义控件类)
3:以及继承控件;
那么下面我们就来依次学习一下，每种方式分别是如何自定义View的。
	
 * @author samy
 * @date 2015-3-29 下午7:09:55
 */
public class CustomviewBaseAct extends BaseAct {
	@BindView(id = R.id.button1, click = true)
	private Button button1;
	@BindView(id = R.id.button2, click = true)
	private Button button2;
	@BindView(id = R.id.button3, click = true)
	private Button button3;
	@BindView(id = R.id.button4, click = true)
	private Button button4;
	@BindView(id = R.id.button5, click = true)
	private Button button5;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a25customview_base);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);

	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.button1:
				showActivity(aty, CustomTitleViewAct.class);
				break;
			case R.id.button2:
				showActivity(aty, CustomImageViewAct.class);
				break;
			case R.id.button3:
				showActivity(aty, CustomImageTextViewAct.class);
				break;
			case R.id.button4:
				showActivity(aty, CustomProgressBarAct.class);
				break;
			case R.id.button5:
				showActivity(aty, CustomVolumControlBarAct.class);
				break;
		}
	}

}
