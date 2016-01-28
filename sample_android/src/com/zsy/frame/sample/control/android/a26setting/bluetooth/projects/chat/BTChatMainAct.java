package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

/**
 * 程序运行前提：两台具有蓝牙的手机均安装该程序
 * 程序功能：
 * 1.一台手机运行程序后选择服务端，自动打开蓝牙及蓝牙被发现（手机的打开蓝牙被发现功能需要人工确认），等待被连接
 * 2.另一台手机运行程序后选择客户端，自动搜索选择了服务端的手机，进行自动配对（手机与手机的配对需要人工确认），并自动连接。这时在两台手机上均可看见连接成功，然后可以互发消息进行聊天
 * 3.程序退出后自动关闭蓝牙
 * 程序需要修改的地方：
 * 需要在BluetoothTools类中修改BluetoothAddress和BluetoothAddress2两个变量，修改成准备测试的两个手机的蓝牙地址。
 * 如果不知道手机的蓝牙地址，在BluetoothClientService类中的discoveryReceiver里面有打印语句。或debug跟踪查看。
 * 该程序是作者因为项目需要写的测试程序，经测试完全可以正常运行，请根据需要自行修改代码
 */
/**
/**
 * 以下是开发中的几个关键步骤：
1，首先开启蓝牙
2，搜索可用设备
3，创建蓝牙socket，获取输入输出流
4，读取和写入数据
5，断开连接关闭蓝牙
 * @description：蓝牙聊天程序主入口
 * @author samy
 * @date 2015-2-8 下午7:17:34
 */
public class BTChatMainAct extends BaseAct {
	private Button startServerBtn;
	private Button startClientBtn;

	public BTChatMainAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a26setting_bluetooth_chat_main);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		startServerBtn = (Button) findViewById(R.id.startServerBtn);
		startClientBtn = (Button) findViewById(R.id.startClientBtn);

		startServerBtn.setOnClickListener(this);
		startClientBtn.setOnClickListener(this);
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
		// 蓝牙同时的本质是蓝牙套接字，一个主动发起连接的的设备做客户端，一个监听连接的设备做服务端，类似sokcet网络编程，利用多线程，读取数据流就可完成蓝牙通信。
			case R.id.startServerBtn:
				// 打开服务器
				Intent serverIntent = new Intent(BTChatMainAct.this, BTChatServerAct.class);
				serverIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(serverIntent);
				break;

			case R.id.startClientBtn:
				// 打开客户端
				Intent clientIntent = new Intent(BTChatMainAct.this, BTChatClientAct.class);
				clientIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(clientIntent);
				break;
		}
	}
}
