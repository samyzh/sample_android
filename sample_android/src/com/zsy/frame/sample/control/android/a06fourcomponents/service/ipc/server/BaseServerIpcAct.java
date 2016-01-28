package com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.server;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.Person;

/**
 * @description：
 * 在Android中，不同app属不同进程（process），进程是安全策略的边界，一个进程不能访问其他进程的存储（例如采用ContentProvider）。
 * 在Remote Service中将涉及进程间通信，也就是通常讲的IPC（interprocess commnication），
 * 需要在进程A和进程B之间建立连接，以便进行相互的通信或数据传递 。
 * 
 * 当然同一App应用的activity 与service也可以在不同进程间，这可以设置Service配置中,android:process=":remote"
 * 
 * 
 * Android提供AIDL（Android Interface Definition Language）工具帮助IPC之间接口的建立，大大地简化了开发者视图。
 * 通过下面的步骤实现client和service之间的通信：
【1】定义AIDL接口 ，Eclipse将自动为Service建立接口IService
【2】Client连接Service，连接到IService暴露给Client的Stub，获得stub对象；换句话，Service通过接口中的Stub向client提供服务，在IService中对抽象IService.Stub具体实现。 
【3】Client和Service连接后，Client可向使用本地方法那样，简单地直接调用IService.Stub里面的方法。
 * @author samy
 * @date 2015-3-8 下午3:28:24
 */
public class BaseServerIpcAct extends Activity {
	private Button button1;
	private Button button2;

	private Person person;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_service_ipc_server_baseserveripc);
		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 启动服务
				Intent intent = new Intent(BaseServerIpcAct.this, ServerService.class);
				startService(intent);
				Toast.makeText(BaseServerIpcAct.this, "开始服务启动完成", Toast.LENGTH_SHORT).show();
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btn2();
			}
		});
	}

	private void btn2() {
		person = new Person();
		// person.age;
		// person.name;
		person.setAge(18);
		person.setName("samy");
		// 绑定和启动有Intentfilter过滤来设置启动（发现他们的IntentFilter得跟Name一样才有用）
		Intent intent = new Intent("com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.server.MyBindServices");
		// Intent intent = new Intent("com.zsy.service.ipc.service.MyBindServices");
		this.bindService(intent, serviceConnection2, Context.BIND_AUTO_CREATE);
		Toast.makeText(this, "绑定服务启动完成，并设置值", Toast.LENGTH_SHORT).show();
	}

	ServiceConnection serviceConnection2 = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			((MyBindServices.MyBind) service).setPerson(person);
		}
	};

}
