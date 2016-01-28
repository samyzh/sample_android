package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat;

import java.util.Date;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils.BluetoothClientService;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils.BluetoothTools;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils.TransmitBean;

/**
 * @description：聊天作为客户端显示界面
 * @author samy
 * @date 2015-2-8 下午8:22:55
 */
public class BTChatClientAct extends BaseAct {
	private TextView serversText;
	private EditText chatEditText;
	private EditText sendEditText;
	private Button sendBtn;

	// private List<BluetoothDevice> deviceList = new ArrayList<BluetoothDevice>();
	int count = 1;

	public BTChatClientAct() {
		super();
		setHiddenActionBar(false);
	}

	// 广播接收器
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();

			if (BluetoothTools.ACTION_NOT_FOUND_SERVER.equals(action)) {
				// 未发现设备
				serversText.append("未发现设备" + count + "次\r\n");
				count++;
			}
			else if (BluetoothTools.ACTION_FOUND_DEVICE.equals(action)) {
				// 获取到设备对象
				BluetoothDevice device = (BluetoothDevice) intent.getExtras().get(BluetoothTools.DEVICE);

				String address = device.getAddress();
				String name = device.getName();
				Log.e("add+name2", "address=" + address + ",name=" + name);
				// if (address.equals(BluetoothTools.MEIZU_M9) || address.equals(BluetoothTools.HTC_Wildfire) || address.equals(BluetoothTools.Nexus_S)) {
				// deviceList.add(device);
				// serversText.append(device.getName() + "\r\n");
				// }
			}
			else if (BluetoothTools.ACTION_CONNECT_SUCCESS.equals(action)) {
				// 连接成功
				serversText.append("连接成功");
				sendBtn.setEnabled(true);
			}
			/**
			* 读取数据对应发送数据；
			* BluetoothCommunThread.writeObject();
			*/
			else if (BluetoothTools.ACTION_DATA_TO_GAME.equals(action)) {
				// 接收数据
				TransmitBean data = (TransmitBean) intent.getExtras().getSerializable(BluetoothTools.DATA);
				String msg = "from remote " + new Date().toLocaleString() + " :\r\n" + data.getMsg() + "\r\n";
				chatEditText.append(msg);

			}
			else if (BluetoothTools.ACTION_CONNECT_ERROR.equals(action)) {
				chatEditText.append("连接失败");
			}
		}
	};

	@Override
	protected void onStart() {
		// 清空设备列表
		// deviceList.clear();

		// 开启后台service
		Intent startService = new Intent(BTChatClientAct.this, BluetoothClientService.class);
		startService(startService);

		// 注册BoradcasrReceiver
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothTools.ACTION_NOT_FOUND_SERVER);
		intentFilter.addAction(BluetoothTools.ACTION_FOUND_DEVICE);
		intentFilter.addAction(BluetoothTools.ACTION_DATA_TO_GAME);
		intentFilter.addAction(BluetoothTools.ACTION_CONNECT_SUCCESS);
		intentFilter.addAction(BluetoothTools.ACTION_CONNECT_ERROR);

		registerReceiver(broadcastReceiver, intentFilter);
		super.onStart();
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		// setTheme(android.R.style.Theme_DeviceDefault);
		setTitle("蓝牙聊天客户端");

		serversText = (TextView) findViewById(R.id.clientServersText);
		chatEditText = (EditText) findViewById(R.id.clientChatEditText);
		sendEditText = (EditText) findViewById(R.id.clientSendEditText);
		sendBtn = (Button) findViewById(R.id.clientSendMsgBtn);

		sendBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 发送消息
				if ("".equals(sendEditText.getText().toString().trim())) {
					Toast.makeText(BTChatClientAct.this, "输入不能为空", Toast.LENGTH_SHORT).show();
				}
				else {
					// 发送消息
					TransmitBean data = new TransmitBean();
					data.setMsg(sendEditText.getText().toString());
					Intent sendDataIntent = new Intent(BluetoothTools.ACTION_DATA_TO_SERVICE);
					sendDataIntent.putExtra(BluetoothTools.DATA, data);
					sendBroadcast(sendDataIntent);
				}
			}
		});
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
		setContentView(R.layout.activity_a26setting_bluetooth_chat_client);
	}
}
