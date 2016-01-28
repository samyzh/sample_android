package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

import com.zsy.frame.sample.control.android.a06fourcomponents.service.helps.DiskTools;

/**
 * @description：普通service
 * @author samy
 * @date 2014年8月23日 下午4:56:43
 */
public class DownLoadBindingService extends Service {
	private final String IMAGE_PATH = "http://www.baidu.com/img/bdlogo.gif";

	// private NotificationManager manager;
	// private NotificationCompat.Builder builder;

	private final IBinder iBinder = new LocalBinder();

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 1) {
				// 停止当前的Service下载普通服务得自己自动去停止自己；Intentservice不用去停止；
				stopSelf();
				Toast.makeText(getApplicationContext(), "下载完毕!!", 1).show();
			}
		}
	};

	public DownLoadBindingService() {
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 结合通知栏处理
		// manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// builder = new NotificationCompat.Builder(getApplicationContext());
		// builder.setSmallIcon(R.drawable.ic_launcher);
		// builder.setContentTitle("down load file");
		// builder.setContentText("down load....");
		// manager.notify(1001, builder.build());
	}

	public class LocalBinder extends Binder {
		public DownLoadBindingService getService() {
			return DownLoadBindingService.this;
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		System.out.println("--->>onBind");
		return iBinder;
	}

	/**
	 * 需要下载网络图片，下载完成之后需要停止Service
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new MyThread()).start();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public class MyThread implements Runnable {
		@Override
		public void run() {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(IMAGE_PATH);
			try {
				HttpResponse response = httpClient.execute(httpPost);
				if (response.getStatusLine().getStatusCode() == 200) {
					byte[] result = EntityUtils.toByteArray(response.getEntity());
					boolean flag = DiskTools.saveToDisk("aa.gif", result);
					if (flag) {
						// Message message = Message.obtain();
						// message.what = 1;
						handler.sendEmptyMessage(1);
					}
					// InputStream inputStream = null;
					// // 获得文件总长度
					// long total_length = response.getEntity().getContentLength();
					// int sum_length = 0;// 每次读取下载的总长度
					// int len = 0;
					// byte[] data = new byte[1024];
					// ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					// try {
					// inputStream = response.getEntity().getContent();
					// while ((len = inputStream.read(data)) != -1) {
					// outputStream.write(data, 0, len);
					// sum_length += len;
					// // 换算每次下载的文件大小的刻度
					// int value = (int) ((sum_length / (float) total_length) * 100);
					// Message message = Message.obtain();
					// message.arg1 = value;
					// handler.sendMessage(message);
					// }
					// if (DiskTools.saveToDisk("aa.gif", outputStream.toByteArray())) {
					// handler.sendEmptyMessage(1);// 停止服务执行
					// }
					// }
					// catch (Exception e) {
					// e.printStackTrace();
					// }
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
