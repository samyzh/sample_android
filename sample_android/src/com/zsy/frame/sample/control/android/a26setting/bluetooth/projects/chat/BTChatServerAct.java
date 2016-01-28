package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils.BluetoothServerService;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils.BluetoothTools;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils.TransmitBean;

/**
 * @description：聊天作为服务端显示界面
 * @author samy
 * @date 2015-2-8 下午8:22:55
 */
public class BTChatServerAct extends BaseAct {
	private TextView serverStateTextView;
	private EditText msgEditText;
	private EditText sendMsgEditText;
	private Button sendBtn;

	public BTChatServerAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	protected void onStart() {
		// 开启后台service；开线程去接收客户端发送过来的广播
		Intent startService = new Intent(BTChatServerAct.this, BluetoothServerService.class);
		startService(startService);
		// 注册BoradcasrReceiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothTools.ACTION_DATA_TO_GAME);
		intentFilter.addAction(BluetoothTools.ACTION_CONNECT_SUCCESS);
		intentFilter.addAction(BluetoothTools.ACTION_CONNECT_ERROR);
		registerReceiver(broadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// 关闭后台Service
		Intent startService = new Intent(BluetoothTools.ACTION_STOP_SERVICE);
		sendBroadcast(startService);
		unregisterReceiver(broadcastReceiver);
		super.onStop();
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a26setting_bluetooth_chat_server);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		// setTheme(android.R.style.Theme_DeviceDefault_NoActionBar);
		// setTheme(android.R.style.Theme_DeviceDefault);
		setTitle("蓝牙聊天服务端");
		serverStateTextView = (TextView) findViewById(R.id.serverStateTxt);
		serverStateTextView.setText("等待连接...");
		msgEditText = (EditText) findViewById(R.id.serverEditText);
		sendMsgEditText = (EditText) findViewById(R.id.serverSendEditText);
		sendBtn = (Button) findViewById(R.id.serverSendMsgBtn);
		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ("".equals(sendMsgEditText.getText().toString().trim())) {
					Toast.makeText(BTChatServerAct.this, "输入不能为空", Toast.LENGTH_SHORT).show();
				}
				else {
					// 发送消息
					TransmitBean data = new TransmitBean();
					data.setMsg(sendMsgEditText.getText().toString());
					Intent sendDataIntent = new Intent(BluetoothTools.ACTION_DATA_TO_SERVICE);
					sendDataIntent.putExtra(BluetoothTools.DATA, data);
					sendBroadcast(sendDataIntent);
				}
			}
		});

		sendBtn.setEnabled(false);
	}

	// 广播接收器
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			/**
			 * 读取数据对应发送数据；
			 * BluetoothCommunThread.writeObject();
			 */
			if (BluetoothTools.ACTION_DATA_TO_GAME.equals(action)) {
				// 接收数据
				TransmitBean data = (TransmitBean) intent.getExtras().getSerializable(BluetoothTools.DATA);
				String msg = "from remote " + new Date().toLocaleString() + " :\r\n" + data.getMsg() + "\r\n";
				msgEditText.append(msg);
			}
			else if (BluetoothTools.ACTION_CONNECT_SUCCESS.equals(action)) {
				// 连接成功
				serverStateTextView.setText("连接成功");
				sendBtn.setEnabled(true);
			}
			else if (BluetoothTools.ACTION_CONNECT_ERROR.equals(action)) {
				// 连接失败
				serverStateTextView.setText("连接失败");
			}
		}
	};
}
