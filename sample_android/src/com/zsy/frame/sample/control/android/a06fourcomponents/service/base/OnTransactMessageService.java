package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.widget.Toast;

public class OnTransactMessageService extends Service {
	private Random random = new Random();
	private LocalBinder localBinder;

	public OnTransactMessageService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		localBinder = new LocalBinder();
		return localBinder;
	}

	/**
	 * 这里只是演示Service和Activity数据通信
	 * 
	 * @description：在service中自定义的方法；提供给Client调用的，通常是Activity；
	 * @author samy
	 * @date 2014年8月29日 下午9:18:21
	 */
	public int getRandom() {
		return random.nextInt(100);
	}

	public class LocalBinder extends Binder {
		public OnTransactMessageService getService() {
			return OnTransactMessageService.this;
		}
		
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
			Toast.makeText(OnTransactMessageService.this, "---Service中接收Activity传过来的值-->" + data.readInt(), Toast.LENGTH_SHORT).show();
			Toast.makeText(OnTransactMessageService.this, "---Service中接收Activity传过来的值-->" + data.readString(), Toast.LENGTH_SHORT).show();

			reply.writeInt(getRandom());
			reply.writeString("samy from MyService");
			return super.onTransact(code, data, reply, flags);
		}
	}
}
