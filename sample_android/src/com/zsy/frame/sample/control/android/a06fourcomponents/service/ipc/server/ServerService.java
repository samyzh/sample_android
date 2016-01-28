package com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.server;

import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.IDataService;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.ipc.data.Person;

/**
 * 注册服务,通过启动服务去启动IPC服务；详情看配置文件
 * 
 * @description：
 *               Client连接Service，连接到IService暴露给Client的Stub，获得stub对象；
 *               换句话，Service通过接口中的Stub向client提供服务，在IService中对抽象IService.Stub具体实现。
 *               步骤二： Remote Service的编写，通过onBind()，在client连接时，传递stub对象
 * @author samy
 * @date 2014年8月31日 下午5:22:34
 */
public class ServerService extends Service {

	public ServerService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * 步骤2.1：具体实现接口中暴露给client的Stub，提供一个stub inner class来具体实现
	 */
	Binder binder = new IDataService.Stub() {
		@Override
		public int getData(String name, int age, double score, float salary, boolean isExit, char sex, long id) throws RemoteException {
			System.out.println("--获得name的值->>" + name);
			System.out.println("--获得age的值->>" + age);
			System.out.println("--获得score的值->>" + score);
			System.out.println("--获得salary的值->>" + salary);
			System.out.println("--获得isExit的值->>" + isExit);
			System.out.println("--获得sex的值->>" + sex);
			System.out.println("--获得id的值->>" + id);
			if (name.equals("hello")) { return 1; }
			return -1;
		}

		@Override
		public boolean getList(List<String> list, List<String> list2) throws RemoteException {
			System.out.println("-->>" + list.get(0));

			list2.add("hello world!!");
			if (list.get(0).equals("jack")) { return true; }
			return false;
		}

		@Override
		public void showPerson(Person person) throws RemoteException {
			System.out.println("--Person-->>" + person.toString());
		}

		@Override
		public Person getPerson() throws RemoteException {
			// 后加的，在绑定服务处理；MyBindServices服务中用到
			return null;
		}
	};

	// 步骤2.2：当client连接时，将触发onBind()，Service向client返回一个stub对象，由此client可以通过stub对象来访问Service，本例中通过stub.getCounter()就可以获得计时器的当前计数。在这个例子中，我们向所有的client传递同一stub对象。
	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	@Override
	public void onDestroy() {
		// 销毁
		super.onDestroy();
	}
}
