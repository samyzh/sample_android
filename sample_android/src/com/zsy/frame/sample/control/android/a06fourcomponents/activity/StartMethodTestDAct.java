package com.zsy.frame.sample.control.android.a06fourcomponents.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

public class StartMethodTestDAct extends BaseAct {
	@BindView(id = R.id.textView1)
	private TextView textView1;
	@BindView(id = R.id.button1, click = true)
	private Button button1;
	@BindView(id = R.id.button2, click = true)
	private Button button2;
	@BindView(id = R.id.button3, click = true)
	private Button button3;
	@BindView(id = R.id.button4, click = true)
	private Button button4;

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a06fourcomponents_activity_startmethod_testd);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		textView1.setText(this.toString() + "\n" + "current task id: " + this.getTaskId());
	}

	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.button1:
				// 默认的Activity的启动模式: 无论栈中是否已经创建过，它都会创建一个新的并置于栈顶并显示，调用oncreate方法。
				showActivity(aty, StartMethodTestAAct.class);
				break;
			case R.id.button2:
				// 只有需要打开的A在栈顶，那么不会创建一个新的A，并调用onNewIntent方法，
				// 如果需要打开的A不在栈顶，那么不论A在栈中有还是没有，都会创建一个新的A放入栈顶，并执行onCreate方法。
				showActivity(aty, StartMethodTestBAct.class);
				break;
			case R.id.button3:
				// 只要A存在栈内，那么就将A之上的全部销毁（不包含A），同时显示并复用A，执行onNewIntent方法。
				// 否则，创建一个新A置于栈顶。
				// 当前的app栈顶为A，这时候，突然来了个消息通知，这个消息通知需要通过Intent去打开A，那么我们点击这个消息通知打开A的时候，
				// 此场景就复现了，它将不会创建一个新的A，而是复用栈顶的A，并执行onNewIntent方法。
				showActivity(aty, StartMethodTestCAct.class);
				break;
			case R.id.button4:
				// singleInstance模式下，会创建一个新的Task栈;只是保持全栈有且只有一个activity实例对象
				// 通常会在打开另一个App才会使用。比如：打电话，使用平率高，耗资源的应用。在应用中打开微信、新浪微博等客户端
				// showActivity(aty, StartMethodAct.class);
				showActivity(aty, this.getClass());
				textView1.setText(this.toString() + "\n" + "current task id: " + this.getTaskId());
				break;
		}
	}

}
