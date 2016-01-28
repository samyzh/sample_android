package com.zsy.frame.sample.control.android.a06fourcomponents.service.base;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

import com.zsy.frame.sample.control.android.a06fourcomponents.service.helps.DiskTools;

/**
 * 这里可以做缓存进程显示处理
 * @description：IntentService
    intentservice:[onHandleIntent]
    IntentService 实际上是Looper,Handler,Service 的集合体,他不仅有服务的功能,还有处理和循环消息的功能.
    //IntentService使用队列的方式将请求的Intent加入队列，然后开启一个worker thread(线程)来处理队列中的Intent  
     //对于异步的startService请求，IntentService会处理完成一个之后再处理第二个  
    因为大多被启动类型的服务不需要同时处理多个请求（这实际是一个危险的多线程场景），因此使用IntentService类来实现自己的服务可能是最好的。所有请求都在一个单线程中，不会阻塞应用程序的主线程（UI Thread），同一时间只处理一个请求。
    
    使用IntentService需要两个步骤：
    　　1、写构造函数
    　　2、复写onHandleIntent()方法
    　　好处：处理异步请求的时候可以减少写代码的工作量，比较轻松地实现项目的需求
    
  IntentService常用于一次性运行，自动结束的情况;
  	和普通服务最大的区别在于：
  		（普通服务）服务开启后不用自行开启服务，需要客户端发送停止服务的命令；
 * 
 * @author samy
 * @date 2014年8月23日 下午4:59:52
 */
public class DownLoadIntentService extends IntentService {
	private final String IMAGE_PATH = "http://www.baidu.com/img/bdlogo.gif";

	public DownLoadIntentService() {
		super("DownLoadIntentService");// 给Intentservice设置服务名字；
	}
	// 经测试，IntentService里面是可以进行耗时的操作的  
    //IntentService使用队列的方式将请求的Intent加入队列，然后开启一个worker thread(线程)来处理队列中的Intent  
    //对于异步的startService请求，IntentService会处理完成一个之后再处理第二个  
	@Override
	protected void onHandleIntent(Intent intent) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(IMAGE_PATH);
		try {
			HttpResponse response = httpClient.execute(httpPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				byte[] result = EntityUtils.toByteArray(response.getEntity());
				boolean flag = DiskTools.saveToDisk("bdlogo.gif", result);
				if (flag) {
					Toast.makeText(getApplicationContext(), "下载完毕", 1).show();
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
