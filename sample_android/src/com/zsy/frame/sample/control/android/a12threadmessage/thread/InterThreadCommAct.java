package com.zsy.frame.sample.control.android.a12threadmessage.thread;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.lib.ui.annotation.BindView;
import com.zsy.frame.sample.R;

/**
 * @description：
 * 一：认识Looper与Handler对象；
 * 当主线程诞生时，就会去执行一个代码循环（looper）,以便见识它的信息队列（MQ）。当UI事件发生了，通常会立即丢一个消息（Message）到MQ;
 * 此时里面取出该消息，并且处理之；通常是进程被删除是，主线程才会被删除；
 * Android里有一个Looper类别，其对象是含有一个信息循环（Message Loop）。也就是说，一个主线程有它自己专属的Looper对象，此线程诞生时，就会执行此对象里的信息循环；
 * 此外，一个主线程还会有其专属的MQ信息结构；由于主线程会持续监视MQ的动态，所以在程序的任何函数，只要将信息（以Message类别的对象表示之）丢人线程的MQ里，就能与主线程沟通了；
 * 在Android里，也定义了一个Handle类别，在程序的任何函数里，可以诞生Handler对象来将Message对象丢人MQ里，而与主线程进行沟通；
 * 
 * 
 * @author samy
 * @date 2015-4-14 下午10:29:10
 */
public class InterThreadCommAct extends BaseAct {
	@BindView(id = R.id.button1, click = true)
	private Button button1;
	@BindView(id = R.id.button2, click = true)
	private Button button2;
	@BindView(id = R.id.button3, click = true)
	private Button button3;
	@BindView(id = R.id.textView1)
	private TextView textView1;

	private Timer timer = new Timer();
	private int k = 0;

	private Handler subclassHandler;
	public String str;

	// 创建一个邮差
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			showToastMsg((String) msg.obj + "==main_thread");
			textView1.setText((String) msg.obj + "==main_thread");
		};
	};

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a12threadmessage_thread_inter_thread_com);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		/**
		 * 启动主线程去执行子线程。而主线程则返回到信息回圈，并持续监视MQ的动态 
		 */
		// 交替数据
		new Thread(new AlternateRunable()).start();
//		Thread background = new Thread(new Runnable() {};
//		background.start();
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.button1:
				showToastMsg(Thread.currentThread().getName());
				handler.removeMessages(1);
				Message message = handler.obtainMessage(1, 1, 1, "my is mainthread show send  to myself");
				// 通过邮差把Message送入MQ里面；
				handler.sendMessage(message);
				break;
			case R.id.button2:
				// 计数器线程
				TimerTask timerTask = new TimerTask() {
					@Override
					public void run() {
						handler.removeMessages(1);
						Message message = handler.obtainMessage(1, 1, 1, Thread.currentThread().getName() + ":" + String.valueOf(k++));
						// 通过邮差把Message送入MQ里面；
						handler.sendMessage(message);
					}
				};
				timer.schedule(timerTask, 500, 1000);
				break;
			case R.id.button3:
				/**
				 * Handler实例化必须开启Looper
				 * Activity主线程由于已经开启Looper所以在主线程中实例化Handler是不需要开启Looper的
				 * 故在次线程最后中开去Looper; 开完后记得关闭；一般在主线程关闭；handler.getLooper().quit();【通过handler拿到looper退出 】
				 * 
				 * 线程开始的时候Looper准备
				 * 线程结束的时候Looper循环
				 * 记录Looper对象，准备停止
				 * 一个Handler只对应一个Looper
				 * 一个Handler可以对应多个Message
				 * 一个Looper只对应一个MessageQueue。
				 */
				// 主线程给次线程发送消息
				subclassHandler.removeMessages(1);
				Message message2 = subclassHandler.obtainMessage(1, 1, 1, "from main thread");
				subclassHandler.sendMessage(message2);
				break;
		}
	}

	@Override
	protected void onDestroy() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
		if (subclassHandler != null) {
			subclassHandler.getLooper().quit();
		}
		super.onDestroy();
	}

	/**
	 * @description：注意在次线程中，一定不能更新UI 
	 * 多个线程之间的通信，也可以用这样的方式通信处理显示
	 * @author samy
	 * @date 2015-4-15 下午10:33:27
	 */
	class AlternateRunable implements Runnable {
		public String str;

		@Override
		public void run() {
			// 此时就诞生一个looper对象，准备好一个消息回圈（Message Loop）和MQ数据结构
			Looper.prepare();
			// 此时就诞生一个Handler对象，可以协助将信息丢到线程的MQ上
			subclassHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					// 接收主线程发送的消息
					str = Thread.currentThread().getName() + ",value=" + msg.obj + "==AlternateRunable";
					// 发送消息给主线程
					handler.removeMessages(1);
					Message message = handler.obtainMessage(1, 1, 1, str);
					// 通过邮差把Message送入MQ里面；
					handler.sendMessage(message);
				}
			};
			// 开始执行信息回圈，并持续监视子线程的MQ动态
			Looper.loop();
		}

	}
}
