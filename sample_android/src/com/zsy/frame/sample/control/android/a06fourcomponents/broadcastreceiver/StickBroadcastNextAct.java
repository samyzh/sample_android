package com.zsy.frame.sample.control.android.a06fourcomponents.broadcastreceiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.zsy.frame.sample.R;

/**
 * @description：
 * 点击send broadcast 和 send stickybroadcast按钮，随便点击4次，此时对应的receiver并没有注册，所以是不会有人响应这两条广播的。然后点击next activity，当打开新的activity后对应的receiver被注册，此时从日志中就能看出已经收到了send stickybroadcast发出的广播，但没有send broadcast发出的广播。这就是sendStickyBroadcast的特别之处，它将发出的广播保存起来，一旦发现有人注册这条广播，则立即能接收到。
日志打印为： action = com.android.action.sticky.broadcastand count = 4
从上面的日志信息可以看出sendStickyBroadcast只保留最后一条广播，并且一直保留下去，这样即使已经处理了这条广播但当再一次注册这条广播后依然可以收到它。
如果你只想处理一遍，removeStickyBroadcast方法可以帮你，处理完了后就将它删除吧。
 * @author samy
 * @date 2015-3-8 下午9:34:17
 */
public class StickBroadcastNextAct extends Activity {
	private final static String TAG = "MyReceiverActivity";
	private IntentFilter mIntentFilter;
	private TextView textView1;
	private TextView textView2;

	private BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			int count = intent.getIntExtra("sent_count", -1);
			Log.d(TAG, "action = " + action + "and count = " + count);
			textView2.setText("action = " + action + "and count = " + count);
			// context.removeStickyBroadcast(intent);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_broadcastreceiver_stickstickbroadcast_next);

		textView1 = (TextView) findViewById(R.id.textView1);
		textView2 = (TextView) findViewById(R.id.textView2);

		mIntentFilter = new IntentFilter();
		mIntentFilter.addAction("com.android.action.broadcast");
		mIntentFilter.addAction("com.android.action.sticky.broadcast");
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mReceiver, mIntentFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mReceiver);

	}

}