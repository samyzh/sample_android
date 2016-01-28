package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a06fourcomponents.service.helps.DiskTools;

/**
 * @description：结合通知栏下载更新;// return Service.START_NOT_STICKY;粘性service用法；
 * @author samy
 * @date 2014年8月23日 下午4:59:25
 */
public class DownLoadService extends Service {

	private final String IMAGE_PATH = "http://www.baidu.com/img/bdlogo.gif";

	// 声明通知管理
	private NotificationManager manager;
	private NotificationCompat.Builder builder;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				stopSelf();// 停止当前的Service下载
				Toast.makeText(getApplicationContext(), "下载完毕!!", 1).show();
				manager.cancel(1001);
			}
			builder.setProgress(100, msg.arg1, false);
			// 提示通知更新
			manager.notify(1001, builder.build());
		}
	};

	public DownLoadService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		builder = new NotificationCompat.Builder(getApplicationContext());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("down load file");
		builder.setContentText("down load....");
		manager.notify(1001, builder.build());
	}

	public class MyThread implements Runnable {
		@Override
		public void run() {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(IMAGE_PATH);
			try {
				HttpResponse response = httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() == 200) {
					// byte[] result = EntityUtils.toByteArray(response
					// .getEntity());
					// boolean flag = DiskTools.saveToDisk("aa.gif", result);
					// if (flag) {
					// // Message message = Message.obtain();
					// // message.what = 1;
					// handler.sendEmptyMessage(1);
					// }
					InputStream inputStream = null;
					// 获得文件总长度
					long total_length = response.getEntity().getContentLength();
					int sum_length = 0;// 每次读取下载的总长度
					int len = 0;
					byte[] data = new byte[1024];
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					try {
						inputStream = response.getEntity().getContent();
						while ((len = inputStream.read(data)) != -1) {
							outputStream.write(data, 0, len);
							sum_length += len;
							// 换算每次下载的文件大小的刻度
							int value = (int) ((sum_length / (float) total_length) * 100);
							Message message = Message.obtain();
							message.arg1 = value;
							handler.sendMessage(message);
						}
						if (DiskTools.saveToDisk("aa.gif", outputStream.toByteArray())) {
							handler.sendEmptyMessage(1);// 停止服务执行
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 需要下载网络图片，下载完成之后需要停止Service
	 * 
	 * 收到客户端请求时触发，由于onStartCommand()运行在主线程，将进行本次服务的初始化，并开启后台线程运行相关的处理，具体参加线程的学习笔记，本例只考察Service相关内容。
	 * 返回值将告诉系统如果service的进程被杀掉，将会如何：
	START_STICKY表示服务将回到开始状态，如同onStartCommand()被called，但是Intent不会重发；
	START_REDELIVER_INTENT表示要求系统重新发送一次Intent，即服务将重新触发执行onStartCommand()；
	START_NOT_STICKY表示无需干预，服务停止，等待有新的命令触发。
	
	START_STICKY：如果service进程被kill掉，保留service的状态为开始状态，但不保留递送的intent对象。随后系统会尝试重新创建service，由于服务状态为开始状态，所以创建服务后一定会调用onStartCommand(Intent,int,int)方法。如果在此期间没有任何启动命令被传递到service，那么参数Intent将为null。
	START_NOT_STICKY：“非粘性的”。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统不会自动重启该服务。
	START_REDELIVER_INTENT：重传Intent。使用这个返回值时，如果在执行完onStartCommand后，服务被异常kill掉，系统会自动重启该服务，并将Intent的值传入。
	START_STICKY_COMPATIBILITY：START_STICKY的兼容版本，但不保证服务被kill后一定能重启。
	 * 
	 * 
	 * 如果有三个客户端都通过startService()，也只会启动一个服务。
	 * 只有第一个发出命令时，系统发现服务没有启动，将启动服务，其余的直接触发onStartCommand()。
	 * 通过startService()并不仅仅是发送命令，而是告诉系统服务维持运行，直至通知它服务停止。
	 * 
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// //经测试，Service里面是不能进行耗时的操作的，必须要手动开启一个工作线程来处理耗时操作
		new Thread(new MyThread()).start();
		return super.onStartCommand(intent, flags, startId);

		// int a = Service.START_NOT_STICKY;
		// int b = Service.START_STICKY;
		// int c = Service.START_REDELIVER_INTENT;
		// return Service.START_NOT_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
