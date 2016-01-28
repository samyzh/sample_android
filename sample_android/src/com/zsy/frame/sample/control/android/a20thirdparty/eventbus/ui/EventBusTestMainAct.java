package com.zsy.frame.sample.control.android.a20thirdparty.eventbus.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event.FirstEvent;
import com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event.SecondEvent;
import com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event.ThirdEvent;

import de.greenrobot.event.EventBus;

/**
 * @description：在EventBus中的观察者通常有四种订阅函数（就是某件事情发生被调用的方法）
1、onEvent
2、onEventMainThread
3、onEventBackground
4、onEventAsync
这四种订阅函数都是使用onEvent开头的，它们的功能稍有不同,在介绍不同之前先介绍两个概念：
告知观察者事件发生时通过EventBus.post函数实现，这个过程叫做事件的发布，观察者被告知事件发生叫做事件的接收，是通过下面的订阅函数实现的。

onEvent:如果使用onEvent作为订阅函数，那么该事件在哪个线程发布出来的，onEvent就会在这个线程中运行，也就是说发布事件和接收事件线程在同一个线程。使用这个方法时，在onEvent方法中不能执行耗时操作，如果执行耗时操作容易导致事件分发延迟。
onEventMainThread:如果使用onEventMainThread作为订阅函数，那么不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行，接收事件就会在UI线程中运行，这个在Android中是非常有用的，因为在Android中只能在UI线程中跟新UI，所以在onEvnetMainThread方法中是不能执行耗时操作的。
onEvnetBackground:如果使用onEventBackgrond作为订阅函数，那么如果事件是在UI线程中发布出来的，那么onEventBackground就会在子线程中运行，如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行。
onEventAsync：使用这个函数作为订阅函数，那么无论事件在哪个线程发布，都会创建新的子线程在执行onEventAsync
 * @author samy
 * @date 2015-2-24 下午4:15:49
 */
public class EventBusTestMainAct extends Activity {
	Button btn;
	TextView tv;
	EventBus eventBus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a20thirdparty_eventbus_test_main);
		EventBus.getDefault().register(this);

		btn = (Button) findViewById(R.id.btn_try);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), SecondAct.class);
				startActivity(intent);
			}
		});
	}

	/**
	 * @description： 使用onEventMainThread来接收事件，那么不论分发事件在哪个线程运行，接收事件永远在UI线程执行，
	 *               这对于android应用是非常有意义的
	 * @author samy;FirstEvent接收函数一
	 * @date 2014年11月25日 上午11:37:45
	 */
	public void onEventMainThread(FirstEvent event) {
		Log.d("samy", "onEventMainThread收到了消息：" + event.getMsg());// onEventMainThread收到了消息：FirstEvent btn clicked
		Toast.makeText(EventBusTestMainAct.this, "onEventMainThread收到了消息：" + event.getMsg(), Toast.LENGTH_LONG).show();
	}

	// SecondEvent接收函数一
	public void onEventMainThread(SecondEvent event) {
		Log.d("samy", "onEventMainThread收到了消息：" + event.getMsg());// onEventMainThread收到了消息：MainEvent:SecondEvent btn clicke
		Toast.makeText(EventBusTestMainAct.this, "onEventMainThread收到了消息：" + event.getMsg(), Toast.LENGTH_LONG).show();
	}

	/**
	 * @description： 使用onEventBackgroundThread来接收事件，如果分发事件在子线程运行，那么接收事件直接在同样线程
	 *               运行，如果分发事件在UI线程，那么会启动一个子线程运行接收事件
	 * @author samy
	 * @date 2014年11月25日 上午11:38:30
	 */
	public void onEventBackgroundThread(SecondEvent event) {// onEventBackground收到了消息：MainEvent:SecondEvent btn clicked
		Log.d("samy", "onEventBackground收到了消息：" + event.getMsg());
		Toast.makeText(EventBusTestMainAct.this, "onEventBackground收到了消息：" + event.getMsg(), Toast.LENGTH_LONG).show();
	}

	/**
	 * @description：使用onEventAsync接收事件，无论分发事件在（UI或者子线程）哪个线程执行，接收都会在另外一个子线程执行 
	 * @author samy
	 * @date 2014年11月25日 上午11:38:51
	 */
	public void onEventAsync(SecondEvent event) {
		Log.d("samy", "onEventAsync收到了消息：" + event.getMsg());// onEventAsync收到了消息：MainEvent:SecondEvent btn clicked
		Toast.makeText(EventBusTestMainAct.this, "onEventAsync收到了消息：" + event.getMsg(), Toast.LENGTH_LONG).show();
	}

	/**
	 * @description：使用onEvent来接收事件，那么接收事件和分发事件在一个线程中执行
	 * @author samy
	 * @date 2014年11月25日 上午11:37:27
	 */
	public void onEvent(ThirdEvent event) {
		Log.d("samy", "OnEvent收到了消息：" + event.getMsg());// onEventMainThread收到了消息：FirstEvent btn clicked
		Toast.makeText(EventBusTestMainAct.this, "OnEvent收到了消息：" + event.getMsg(), Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
