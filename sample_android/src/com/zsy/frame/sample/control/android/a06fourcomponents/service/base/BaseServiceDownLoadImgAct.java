package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.base.DownLoadBindingService.LocalBinder;

/**
一： 服务可用于以下的场景：
1、用户离开activity后，仍需继续工作，例如从网络下载文件，播放音乐
2、无论activity出现（重现）或离开，都需持续工作，例如网络聊天应用 
3、连接网络服务，正在使用一个远程API提供的服务 （IPC）
4、定时触发的服务，例如Linux中的cron。
二：Service
Android Service是分为两种：
　　本地服务（Local Service）： 同一个apk内被调用[可以分为三种startsevice，intentservice]
　　远程服务（Remote Service）：被另一个apk调用
启动Service有两种方式：
    1：bindService；
     	Intent intent=new Intent(this,LocalService.class);
        bindService(intent, new ServiceConnection(){}
    	使用bindService()方法启用服务，调用者与服务绑定在了一起，调用者一旦退出，服务也就终止，大有“不求同时生，必须同时死”的特点。
    2：startService；
    	startService(new Intent("com.demo.SERVICE_DEMO")); 
		stopService(new Intent("com.demo.SERVICE_DEMO")); 

声明周期为：
context.startService() ->onCreate()- >onStart()-onStartCommand->Service running--调用context.stopService() ->onDestroy() 
context.bindService()->onCreate()->onBind()->Service running--调用>onUnbind() -> onDestroy() 

Android之Service与IntentService的比较

用IntentService的好处：
1：我们省去了在Service中手动开线程的麻烦；
2：当操作完成时，我们不用手动停止Service
3：你所需要做的就是实现 onHandleIntent() 方法，在该方法内实现你想进行的操作。
	另外，继承IntentService时，你必须提供一个无参构造函数，且在该构造函数内，你需要调用父类的构造函数；
	Intent被处理时(即onHandleIntent()处于运行中)，该Service仍然可以接受新的请求，但接受到新的请求后并没有立即执行，而是将它们放入了工作队列中，等待被执行
	但你若是想在Service中让多个线程并发的话，就得另想法子喽。比如，使用第一种方法，在Service内部起多个线程，但是这样的话，你可要处理好线程的同步;如下：		
		
服务的区别
        开始服务,开始服务如果不停止，那么服务不会停止。
        绑定服务,绑定者退出，那么绑定的服务也就自动退出。
        绑定服务可以利用aidl完成IPC功能(实现进程与进程间的通讯)，开始服务不能完成。
 * @description：主界面
 * @author samy
 * @date 2014年8月23日 下午4:56:32
 */
public class BaseServiceDownLoadImgAct extends Activity {
	/** button1,button2,button3用到的都是start服务， */
	private Button button1;
	private Button button2;
	private Button button3;

	private DownLoadBindingService bindingService;
	private boolean flag = false;// 判断是否被绑定

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_service_base_downloadimg);
		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
		button3 = (Button) this.findViewById(R.id.button3);

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// Intent intent = new Intent(MainActivity.this, DownLoadBindingService.class);
				// startService(intent);
				Intent intent = new Intent(BaseServiceDownLoadImgAct.this, DownLoadBindingService.class);
				bindService(intent, connection, Context.BIND_AUTO_CREATE);
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseServiceDownLoadImgAct.this, DownLoadService.class);
				startService(intent);
			}
		});

		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 连续两次启动IntentService，会发现应用程序不会阻塞，
				// 而且最重的是第二次的请求会再第一个请求结束之后运行(这个证实了IntentService采用单独的线程每次只从队列中拿出一个请求进行处理)
				Intent intent = new Intent(BaseServiceDownLoadImgAct.this, DownLoadIntentService.class);
				startService(intent);
				startService(intent);
			}
		});

		//
		// // 表示卸载Service服务
		// button4.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// if (flag) {
		// unbindService(connection);
		// flag = false;
		// }
		// }
		// });
	}

	public ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName className) {
			flag = false;
		}

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			LocalBinder localBinder = (LocalBinder) service;
			bindingService = localBinder.getService();
			flag = true;
		}
	};
}
