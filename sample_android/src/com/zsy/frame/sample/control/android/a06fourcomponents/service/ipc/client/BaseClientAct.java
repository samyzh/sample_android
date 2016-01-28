package com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.client;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.IDataService;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.Person;

/**
 * 谈谈Android的IPC机制：
答：IPC是内部进程通信的简称，是共享"命名管道"的资源。Android中的IPC机制是为了让Activity和Service之间可以随时的进行交互，
故在Android中该机制，只适用于Activity和Service之间的通信，类似于远程方法调用，类似于C/S模式的访问。
通过定义AIDL接口文件来定义IPC接口。Servier端实现IPC接口，Client端调用IPC接口本地代理。
 * 
 * IPC开发： 
 * 【1】定义AIDL接口 ，Eclipse将自动为Service建立接口IService
 * 【2】Client连接Service，连接到IService暴露给Client的Stub，获得stub对象；换句话，Service通过接口中的Stub向client提供服务，在IService中对抽象IService.Stub具体实现。
 * 【3】Client和Service连接后，Client可向使用本地方法那样，简单地直接调用IService.Stub里面的方法。
 * @description：IPC
 * @author samy:步骤3：Client和Service建立连接，获得stub
 * @date 2014年8月31日 下午4:16:12
 */
public class BaseClientAct extends Activity {
	private Button button1;
	private Button button2;
	private Button button3;
	private TextView textView3;
	private IDataService dataService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_service_ipc_server_baseclentipc);
		button1 = (Button) this.findViewById(R.id.button1);
		button2 = (Button) this.findViewById(R.id.button2);
		button3 = (Button) this.findViewById(R.id.button3);
		textView3 = (TextView) this.findViewById(R.id.textView3);

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 得绑定那个IPC服务的名字；这里因不同包名字了，故不能通过类名来直接获取；一般情况下应保持类名的包结构一致；
				// IDataService跟配置文件的设置有很大关系;IDataService.class.getName()这样写最靠谱；
				// IDataService.class.getSimpleName()
				// Intent intent = new Intent(IDataService.class.getName());
				Intent intent = new Intent("com.zsy.service.ipc.data.IDataService");// com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.server.ServerService
				bindService(intent, connection, Context.BIND_AUTO_CREATE);
				// 对应服务启动的位置
				// <service android:name="com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.server.ServerService" >
				// <intent-filter>
				// <action android:name="com.zsy.service.ipc.data.IDataService" >
				// </action>
				// </intent-filter>
				// </service>

				textView3.setText("绑定服务的包名：" + "com.zsy.service.ipc.data.IDataService对应的服务的启动位置");
				// textView3.setText("绑定服务的包名：" + IDataService.class.getName());
				// 我的这个包里面还有层次，如*.part1、*.part2,etc
				// i.setClassName("com.wei.android.learning", "com.wei.android.learning.part5.TestRemoteService");
			}
		});
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				try {
					// int age,double score,float salary,boolean isExit,char sex,long id
					/**
					 * 给服务器设置数据，并从服务器返回数据
					03-07 20:50:30.590: I/System.out(32006): --获得name的值->>hello
					03-07 20:50:30.590: I/System.out(32006): --获得age的值->>23
					03-07 20:50:30.590: I/System.out(32006): --获得score的值->>65.0
					03-07 20:50:30.590: I/System.out(32006): --获得salary的值->>3000.0
					03-07 20:50:30.590: I/System.out(32006): --获得isExit的值->>true
					03-07 20:50:30.590: I/System.out(32006): --获得sex的值->>M
					03-07 20:50:30.590: I/System.out(32006): --获得id的值->>10010101
					03-07 20:50:30.590: I/System.out(32323): --->>1
					03-07 20:50:30.590: I/System.out(32006): -->>jack
					03-07 20:50:30.595: I/System.out(32323): ---->>true
					03-07 20:50:30.595: I/System.out(32323): --list2--->>hello world!!
					03-07 20:50:30.595: I/System.out(32006): --Person-->>Person [age=190, name=XXXsamy]
					 */
					int result = dataService.getData("hello", 23, 65, 3000.0f, true, 'M', 10010101);
					System.out.println("--->>" + result);

					List<String> list = new ArrayList<String>();
					list.add("jack");
					List<String> list2 = new ArrayList<String>();
					boolean result2 = dataService.getList(list, list2);
					System.out.println("---->>" + result2);
					System.out.println("--list2--->>" + list2.get(0));

					Person person = new Person();
					person.setAge(100);
					person.setName("XXX");
					dataService.showPerson(person);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				btn3();
			}
		});
	}

	private void btn3() {
		Intent intent = new Intent("com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.server.MyBindServices");
		// Intent intent = new Intent("com.zsy.service.ipc.service.MyBindServices2");
		this.bindService(intent, connection2, Context.BIND_AUTO_CREATE);
	}

	// private void bindService(){
	// if(conn == null){
	// conn = new CounterServiceConnection();
	// Intent i = new Intent();
	// i.setClassName("com.wei.android.learning","com.wei.android.learning.part5.TestRemoteService");
	// bindService(i, conn,Context.BIND_AUTO_CREATE);
	// updateServiceStatus();
	// }
	// }
	// }

	// private void releaseService(){
	// if(conn !=null){
	// unbindService(conn); //断开连接，解除绑定
	// conn = null;
	// updateServiceStatus();
	// }
	// }

	// 步骤3.2 class CounterServiceConnection实现ServiceConnection接口，需要具体实现里面两个触发onServiceConnected()和onServiceDisconnected()
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// 【3】Client和Service连接后，Client可向使用本地方法那样，简单地直接调用IService.Stub里面的方法。
			dataService = IDataService.Stub.asInterface(arg1);
		}
	};

	private ServiceConnection connection2 = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName arg0) {

		}

		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {

			IDataService iDataService = IDataService.Stub.asInterface(arg1);
			try {
				Person person = iDataService.getPerson();
				textView3.setText("name:" + person.getName() + "---" + "age:" + person.getAge());
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			// BaseClientAct.this.unbindService(this);
		}
	};

	private void releaseService() {
		if (connection != null) {
			unbindService(connection); // 断开连接，解除绑定
			connection = null;
		}
	}

}
