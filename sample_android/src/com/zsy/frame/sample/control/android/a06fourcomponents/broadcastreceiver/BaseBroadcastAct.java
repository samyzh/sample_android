package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zsy.frame.sample.R;

/**
 * @description：
 广播Intent的三种方式总结(普通广播 、有序广播、粘性广播)
1.android有序广播和无序广播的区别 
   (1) BroadcastReceiver所对应的广播分两类：普通广播和有序广播。 
            普通广播通过Context.sendBroadcast()方法来发送。它是完全异步的。 
            所有的receivers接收器的执行顺序不确定。    因此，所有的receivers接收器接收broadcast的顺序不确定。 
            这种方式效率更高。但是BroadcastReceiver无法使用setResult系列，getResult系列及abort系列API 
            有序广播是通过Context.sendOrderedBroadcast来发送。所有的receiver依次执行。 
   (2)BroadcastReceiver可以使用setResult系列函数来结果传给下一个BroadcastReceiver，通过getResult系列函数来取得上个BroadcastReceiver返回的结果，并可以abort系列函数来让系统丢弃该广播让，使用该广播不再传送到别的BroadcastReceiver。 
            可以通过在intent-filter中设置android:priority属性来设置receiver的优先级。优先级相同的receiver其执行顺序不确定。 
            如果BroadcastReceiver是代码中注册的话，且其intent-filter拥有相同android:priority属性的话，先注册的将先收到广播。 
            有序广播，即从优先级别最高的广播接收器开始接收，接收完了如果没有丢弃，就下传给下一个次高优先级别的广播接收器进行处理，依次类推，直到最后。 
2.sendBroadcast和sendStickyBroadcast的区别 
  sendBroadcast中发出的intent在ReceverActivity不处于onResume状态是无法接受到的，即使后面再次使其处于该状态也无法接受到。 
    而sendStickyBroadcast发出的Intent当ReceverActivity重新处于onResume状态之后就能重新接受到其Intent.这就是the Intent will be held to be re-broadcast to future receivers这句话的表现。就是说sendStickyBroadcast发出的最后一个Intent会被保留，下次当Recevier处于活跃的时候，又会接受到它。 
3. FLAG的影响 
    1)FLAG_RECEIVER_REPLACE_PENDING 
    这个flag 将会将之前的Intent 替代掉。加了这个flag，在发送一系列的这样的Intent 之后， 中间有些Intent 有可能在你还没有来得及处理的时候，就被替代掉了。 
    2)FLAG_RECEIVER_REGISTERED_ONLY： 
    如果Intent 加了这个Flag， 那么在Androidmanifest.xml 里定义的Receiver 是接收不到这样的Intent 的。 
    3)FLAG_RECEIVER_REGISTERED_ONLY_BEFORE_BOOT： 
    如果Intent加了这个Flag，那么在启动检查时只能接受在代码中注册的Receiver。这个标志是唯一使用的系统服务作为一种方便避免实施更复杂的机制在启动完成检测。
 
android-BroadcastReceiver 发送有序广播
普通广播（Normal Broadcast）：
一，优缺点：和有序广播的优缺点相反！
二，发送广播的方法：sendBroadcast()


有序广播（Ordered Broadcast）：
一，优缺点
优点：1，按优先级的不同，优先Receiver可对数据进行处理，并传给下一个Receiver
      2，通过abortBroadcast可终止广播的传播  
缺点：效率低  
二，发送广播的方法：sendOrderedBroadcast()   
三，优先接收到Broadcast的Receiver可通过setResultExtras(Bundle)方法将处理结果存入Broadcast中，
下一个Receiver 通过 Bundle bundle=getResultExtras(true)方法获取上一个 Receiver传来的数据     
程序效果：点击按钮，两个Receiver接收同一条广播，在logcat中打印出数据（按照Receiver的优先顺序，Receiver2先，Receiver1后）      

3.3不同的广播类型
普通广播 ： 通过Context.sendBroadcast发送的广播即为普通广播，对于普通广播接收者接收到它的顺序是不定的，所以接收者接收到后无法使用其他接收者对它的处理结果也无法停止它（即不可调用abortBroadcast方法）。
有序广播 : 通过Context.sendOrderedBroadcast发送的广播即为有序广播， 与普通广播的不同在于，接收者是有序接收到广播的并且可以对广播进行修改或是取消广播向下传递 。系统根据接收者定义的优先级顺序决定哪个接收者先接收到它，
		    接收者处理完后可以将结果传递给优先级低的接收者也可以停止广播使得其他优先级低的接收者无法接收到该广播。
		    优先级通过android:priority属性定义，数值越大优先级别越高，取值范围：-1000到1000， Android系统收到短信、接到电话后发送的广播都是有序广播 ，所以可以进行短信或电话的拦截，即取消广播。
粘性广播 : 即 Sticky Broadcast。 如果发送者发送了某个广播，而接收者在这个广播发送后才注册自己的Receiver，这时接收者便无法接收到刚才的广播，
		  为此Android引入了 StickyBroadcast，在广播发送结束后会保存刚刚发送的广播（Intent），这样当接收者注册完Receiver后就可以继续使用刚才的广播 。 如果在接收者注册完成前发送了多条相同Action的粘性广播，注册完成后只会收到一条该Action的广播，并且消息内容是最后一次广播内容。 系统网络状态的改变发送的广播就是粘性广播。 粘性广播通过Context的 sendStickyBroadcast(Intetn) 接口发送，需要添加权限:
		<uses-permission android:name="android.permission.BROADCAST_STICKY"/>

 * @author samy
 * @date 2015-3-8 下午8:03:41
 */
public class BaseBroadcastAct extends Activity {
	private Button button1;
	private Button orderButton;
	private Button button3;
	private Button button4;

	private int mStickyBrcCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_broadcastreceiver_basebroadcast);
		button1 = (Button) this.findViewById(R.id.button1);
		orderButton = (Button) this.findViewById(R.id.button2);
		button3 = (Button) this.findViewById(R.id.button3);
		button4 = (Button) this.findViewById(R.id.button4);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.android.action.broadcast");
				sendBroadcast(intent);
			}
		});

		orderButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver.OrderedBroadcast.test");
				// intent.setAction("abc");
				// intent.putExtra("name", "jack");
				Bundle bundle = new Bundle();
				bundle.putString("a", "main");
				intent.putExtras(bundle);
				sendOrderedBroadcast(intent, null);
				// 03-08 20:32:27.400: I/System.out(11397): TwoBroadcastReceiver
				// 03-08 20:32:27.400: I/System.out(11397): a=main
				// 03-08 20:32:27.405: I/System.out(11397): OneBroadcastReceiver
				// 03-08 20:32:27.405: I/System.out(11397): a=main,b=bbb
			}
		});
		/**
		 * 记住，发送这种广播，得配置权限
		 */
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mStickyBrcCount++;
				Intent intent = new Intent("com.android.action.sticky.broadcast");
				intent.putExtra("sent_count", mStickyBrcCount);
				sendStickyBroadcast(intent);
			}
		});
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BaseBroadcastAct.this, StickBroadcastNextAct.class);
				startActivity(intent);
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		mStickyBrcCount = 0;
	}

}
