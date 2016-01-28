package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.base.OnTransactMessageService.LocalBinder;

public class OnTransactMessageAct extends Activity {
	private boolean mBound;// 判断是否绑定
	private OnTransactMessageService myService;
	private LocalBinder localBinder;

	private Button button1;
	private Button button2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_service_ontransact_message);
		button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBound) {
					int value = myService.getRandom();
					Toast.makeText(OnTransactMessageAct.this, "Get Message From Service-->" + value, Toast.LENGTH_SHORT).show();
				}
			}
		});

		button2 = (Button) this.findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Parcel parcelData = Parcel.obtain();
				parcelData.writeInt(23);
				parcelData.writeString("jack");
				// 从Service中取回值；
				Parcel reply = Parcel.obtain();
				try {
					localBinder.transact(IBinder.LAST_CALL_TRANSACTION, parcelData, reply, 0);
				}
				catch (RemoteException e) {
					e.printStackTrace();
				}

				Toast.makeText(OnTransactMessageAct.this, "---Activity中从Service中回传的值-->" + reply.readInt(), Toast.LENGTH_SHORT).show();
				Toast.makeText(OnTransactMessageAct.this, "---Activity中从Service中回传的值-->" + reply.readString(), Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
//		同一个进程里面的；目前这里实现的显示启动 ；
		bindService(new Intent(this, OnTransactMessageService.class), connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mBound) {
			unbindService(connection);
			mBound = false;
		}
	}

	public ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder binder) {
			// 连接上，直接通过Ibinder绑定Messenger信使；传递消息对象给Service;
			// LocalBinder localBinder = (LocalBinder) binder;
			localBinder = (LocalBinder) binder;
			myService = localBinder.getService();
			mBound = true;
		}
	};
}
