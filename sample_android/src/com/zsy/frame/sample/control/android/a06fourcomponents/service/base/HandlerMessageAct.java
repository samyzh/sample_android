package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.view.View;
import android.widget.Toast;

import com.zsy.frame.sample.R;

public class HandlerMessageAct extends Activity {
	private boolean mBound;// 判断是否绑定
	private Messenger messenger;// 声明信使，用来发送Message到Service
	private HandlerMessageService myService;

	// 表示从Service获取的Message
	private Messenger clientMessenger = new Messenger(new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			Toast.makeText(HandlerMessageAct.this, "--获得Service的响应结果->>" + msg.obj, Toast.LENGTH_LONG).show();
			// System.out.println("--获得Service的响应结果->>" + msg.obj);
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_service_handlermessage);
	}

	@Override
	protected void onStart() {
		super.onStart();
		bindService(new Intent(this, HandlerMessageService.class), connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(connection);
			mBound = false;
		}
	}

	// 处理点击事件;// 向Service发送消息
	public void sayHello(View view) {
		if (mBound) {
//		方法一：	这里通过消息机制类处理Activity和Service之间的通信；
//		方法二：通过调用	 	ib.transact(1, pc, pc_reply, 0);
			Message message = Message.obtain();
			message.what = HandlerMessageService.MSG_SAY_HELLO;
			message.obj = "jack";
			message.replyTo = clientMessenger;
			try {
				messenger.send(message);
			}
			catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	public ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			messenger = null;
			mBound = false;
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {
			// 连接上，直接通过Ibinder绑定Messenger信使；传递消息对象给Service;
			messenger = new Messenger(binder);

			// LocalBinder localBinder = (LocalBinder) binder;
			// myService = localBinder.getService();
			mBound = true;
		}
	};
}
