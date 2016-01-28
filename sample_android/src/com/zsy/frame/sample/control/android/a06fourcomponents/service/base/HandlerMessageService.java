package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.widget.Toast;

public class HandlerMessageService extends Service {
	// 定义Service显示消息
	public static final int MSG_SAY_HELLO = 1;
	private Messenger replyMessenger;// 响应Client的数据，从Service发送Message给Client端

	// 表示接收从Client发送的Message
	final Messenger messenger = new Messenger(new IncomingHandler());

	// private Random random = new Random();

	public HandlerMessageService() {
	}

	// 处理从Client传送过来的消息
	class IncomingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == MSG_SAY_HELLO) {
				Toast.makeText(getApplicationContext(), "-->>Hello world-" + msg.obj, 1).show();
				Message message = Message.obtain();
				message.obj = "我拿到了消息，告诉Client";
				replyMessenger = msg.replyTo;
				try {
					replyMessenger.send(message);
				}
				catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return messenger.getBinder();
	}

	// /**
	// * 这里只是演示Service和Activity数据通信
	// *
	// * @description：在service中自定义的方法；提供给Client调用的，通常是Activity；
	// * @author samy
	// * @date 2014年8月29日 下午9:18:21
	// */
	// public int getRandom() {
	// return random.nextInt(100);
	// }

	public class LocalBinder extends Binder {
		public HandlerMessageService getService() {
			return HandlerMessageService.this;
		}
	}
}
