package com.zsy.frame.sample.control.android.a26setting.wifi.base;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.wifi.utils.WifiAdmin;

public class WifiBaseAct extends Activity {
	private Context context = null;
	public SetWifiHandler setWifiHandler;
	private RelayListAdapter wifiListAdapter;
	private Button button1;
	private ImageButton refresh_list_btn;
	private ImageButton wifi_on_off_btn;
	private ListView wifi_list;

	// private WifiManager wifiManager = null;
	// private LinkWifi linkWifi;
	private WifiAdmin wifiAdmin;

	public class SetWifiHandler extends Handler {
		public SetWifiHandler(Looper mainLooper) {
			super(mainLooper);
		}

		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:// 请求操作某一无线网络
					ScanResult wifiinfo = (ScanResult) msg.obj;
					configWifiRelay(wifiinfo);
					// initLabelData();
					break;
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a26setting_wifi_wifibase);
		context = this;
		wifiAdmin = new WifiAdmin(this);

		setWifiHandler = new SetWifiHandler(Looper.getMainLooper());

		button1 = (Button) findViewById(R.id.button1);
		refresh_list_btn = (ImageButton) findViewById(R.id.refresh_list_btn);
		wifi_on_off_btn = (ImageButton) findViewById(R.id.wifi_on_off_btn);
		wifi_list = (ListView) findViewById(R.id.wifi_list);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// WIFI热点问题，现单独做个界面处理显示；
				Toast.makeText(context, " WIFI热点问题，现单独做个界面处理显示", Toast.LENGTH_LONG).show();
			}
		});
		refresh_list_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(context, "请求刷新wifi列表！", Toast.LENGTH_LONG).show();
				wifiAdmin.startScan();
			}
		});

		if (wifiAdmin.checkWifiState()) {
			wifi_on_off_btn.setBackgroundResource(R.drawable.a26setting_wifi_wifibase_wifi_on);
		}
		else {
			wifi_on_off_btn.setBackgroundResource(R.drawable.a26setting_wifi_wifibase_wifi_off);
		}

		wifi_on_off_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Toast.makeText(context, "正在请求开关WIFI...", Toast.LENGTH_LONG).show();
				// 这里需要注意延迟的设计，因为wifi开关由延迟
				wifiAdmin.checkWifiEnale();
			}
		});

		regWifiReceiver();
		wifiAdmin.startScan();
	}

	private void regWifiReceiver() {
		System.out.println("注册一个当wifi热点列表发生变化时要求获得通知的消息");
		IntentFilter labelIntentFilter = new IntentFilter();
		labelIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
		labelIntentFilter.addAction("android.net.wifi.STATE_CHANGE"); // ConnectivityManager.CONNECTIVITY_ACTION);
		// labelIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		// android.net.wifi.WIFI_STATE_CHANGED
		labelIntentFilter.setPriority(1000); // 设置优先级，最高为1000
		context.registerReceiver(wifiResultChange, labelIntentFilter);

	}

	/**
	 * 接收wifi热点改变事件
	 */
	private final BroadcastReceiver wifiResultChange = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
				System.out.println("wifi列表刷新了");
				showWifiList();
			}
			else if (action.equals("android.net.wifi.STATE_CHANGE")) {
				// else if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
				System.out.println("wifi状态发生了变化");
				// 刷新状态显示
				showWifiList();
				if (wifiAdmin.checkWifiState()) {
					wifi_on_off_btn.setBackgroundResource(R.drawable.a26setting_wifi_wifibase_wifi_on);
				}
				else {
					wifi_on_off_btn.setBackgroundResource(R.drawable.a26setting_wifi_wifibase_wifi_off);
				}
			}
		}
	};

	/**
	 * @description：显示附近WIFI的列表网络信息
	 * @author samy
	 * @date 2015-2-26 下午7:12:13
	 */
	private void showWifiList() {
		// 剔除ssid中的重复项，只保留相同ssid中信号最强的哪一个
		List<ScanResult> wifiList = wifiAdmin.getWifiList();
		List<ScanResult> newWifList = new ArrayList<ScanResult>();
		boolean isAdd = true;
		if (wifiList != null) {
			for (int i = 0; i < wifiList.size(); i++) {
				isAdd = true;
				for (int j = 0; j < newWifList.size(); j++) {
					if (newWifList.get(j).SSID.equals(wifiList.get(i).SSID)) {
						isAdd = false;
						if (newWifList.get(j).level < wifiList.get(i).level) {
							// ssid相同且新的信号更强
							newWifList.remove(j);
							newWifList.add(wifiList.get(i));
							break;
						}
					}
				}
				if (isAdd) {
					newWifList.add(wifiList.get(i));
				}
			}
		}
		wifiListAdapter = new RelayListAdapter(context, newWifList, setWifiHandler);
		wifi_list.setAdapter(wifiListAdapter);
	}

	private void configWifiRelay(final ScanResult wifiinfo) {
		System.out.println("SSID=" + wifiinfo.SSID);
		// 如果本机已经配置过的话
		if (wifiAdmin.isExsits(wifiinfo.SSID) != null) {
			final int netID = wifiAdmin.isExsits(wifiinfo.SSID).networkId;

			String actionStr;
			// 如果目前连接了此网络
			if (wifiAdmin.getNetworkId() == netID) {
				// if (wifiManager.getConnectionInfo().getNetworkId() == netID) {
				actionStr = "断开";
			}
			else {
				actionStr = "连接";
			}
			System.out.println("wifiManager.getConnectionInfo().getNetworkId()=" + wifiAdmin.getNetworkId());

			new AlertDialog.Builder(context).setTitle("提示").setMessage("请选择你要进行的操作？").setPositiveButton(actionStr, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					if (wifiAdmin.getNetworkId() == netID) {
						// wifiManager.disconnect();
						wifiAdmin.disconnectWifi(netID);
					}
					else {
						WifiConfiguration config = wifiAdmin.isExsits(wifiinfo.SSID);
						wifiAdmin.setMaxPriority(config);
						// wifiAdmin.connectWifiBySSID(config.networkId);
						wifiAdmin.connectToNetID(config.networkId);
					}

				}
			}).setNeutralButton("忘记", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					wifiAdmin.removeNetWorkById(netID);
					return;
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					return;
				}
			}).show();
			return;
		}

		String capabilities = "";
		if (wifiinfo.capabilities.contains("WPA2-PSK")) {// WPA-PSK加密
			capabilities = "psk2";
		}
		else if (wifiinfo.capabilities.contains("WPA-PSK")) {// WPA-PSK加密
			capabilities = "psk";
		}
		else if (wifiinfo.capabilities.contains("WPA-EAP")) {// WPA-EAP加密
			capabilities = "eap";
		}
		else if (wifiinfo.capabilities.contains("WEP")) {// WEP加密
			capabilities = "wep";
		}
		else {// 无密码
			capabilities = "";
		}
		if (!capabilities.equals("")) {// 有密码，提示输入密码进行连接
			LayoutInflater factory = LayoutInflater.from(context);
			final View inputPwdView = factory.inflate(R.layout.view_a26setting_wifi_wifibase_dialog_inputpwd, null);
			new AlertDialog.Builder(context).setTitle("请输入该无线的连接密码").setMessage("无线SSID：" + wifiinfo.SSID).setIcon(android.R.drawable.ic_dialog_info).setView(inputPwdView).setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					EditText pwd = (EditText) inputPwdView.findViewById(R.id.etPassWord);
					String wifipwd = pwd.getText().toString();
					// 此处加入连接wifi代码
					int netID = wifiAdmin.createWifiInfo2(wifiinfo, wifipwd);

					wifiAdmin.connectToNetID(netID);
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			}).setCancelable(false).show();

		}
		else {// 无密码
			new AlertDialog.Builder(context).setTitle("提示").setMessage("你选择的wifi无密码，可能不安全，确定继续连接？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					// 此处加入连接wifi代码
					int netID = wifiAdmin.createWifiInfo2(wifiinfo, "");
					wifiAdmin.connectToNetID(netID);
				}
			}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					return;
				}
			}).show();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		context.unregisterReceiver(wifiResultChange); // 注销此广播接收器
	}

}
