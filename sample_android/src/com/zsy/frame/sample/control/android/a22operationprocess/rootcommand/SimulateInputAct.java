package com.zsy.frame.sample.control.android.a22operationprocess.rootcommand;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stericson.RootTools.RootTools;
import com.zsy.frame.sample.R;

/**
 * @description：
 * Simulate Input的主类
 * @author samy
 * @date 2015-4-12 下午7:57:54
 */
public class SimulateInputAct extends Activity {
	private String TAG = "SimulateInput";
	private Button btnTestKey, btnTestSwipe, btnTestTap, btnExit;
	private RootCommand rootCommand = new RootCommand();

	private TextView show_ps_detail_tv;
	private Button restart_test;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a22operationprocess_rootcommand_simulateinput);
		// 判断是否root过，没root过不可用
		if (RootTools.isRootAvailable() == false) {
			Toast.makeText(this, "本程序需要使用ROOT权限。", Toast.LENGTH_SHORT).show();
			this.finish();
		}

		rootCommand.init();

		// 模拟按下Home键
		btnTestKey = (Button) this.findViewById(R.id.btnTestKey);
		btnTestKey.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 命令格式：input keyevent keycode
				rootCommand.run("/system/bin/input keyevent " + KeyEvent.KEYCODE_HOME);
			}
		});

		// 模拟滑动触摸屏
		btnTestSwipe = (Button) this.findViewById(R.id.btnTestSwipe);
		btnTestSwipe.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int x2 = SimulateInputAct.this.getWindow().getDecorView().getWidth() - 10;
				// 先去到桌面
				rootCommand.run("/system/bin/input keyevent " + KeyEvent.KEYCODE_HOME);
				// 滑动桌面，命令格式：input swipe x1 y1 x2 y2
				for (int i = 0; i < 4; i++) {
					rootCommand.run("/system/bin/input swipe 10 300 " + x2 + " 400");
					rootCommand.run("/system/bin/input swipe " + x2 + " 300 10 400");
				}
			}
		});

		// 模拟点击触摸屏
		btnTestTap = (Button) this.findViewById(R.id.btnTestTap);
		btnTestTap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] location = new int[2];
				btnTestSwipe.getLocationOnScreen(location);
				int x = location[0] + btnTestSwipe.getWidth() / 2;
				int y = location[1] + btnTestSwipe.getHeight() / 2;
				// 模拟点击btnTestTap
				rootCommand.run("/system/bin/input tap " + x + " " + y);
			}
		});

		// 退出程序
		btnExit = (Button) this.findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rootCommand.release();
				SimulateInputAct.this.finish();
			}
		});
		show_ps_detail_tv = (TextView) findViewById(R.id.show_ps_detail_tv);
		findViewById(R.id.command_ps_show_btn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// 可用于直接执行系统里面的编译过的c/c++程序
					// Process process = Runtime.getRuntime().exec("/data/data/hello");
					// Process process = Runtime.getRuntime().exec("date");
					Process process = Runtime.getRuntime().exec("ps");
					InputStream is = process.getInputStream();
					DataInputStream dis = new DataInputStream(is);
					String result;
					StringBuilder sb = new StringBuilder();
					while ((result = dis.readLine()) != null) {
						sb.append(result);
						sb.append("\n");
					}
					// System.out.println(sb.toString());
					show_ps_detail_tv.setText(sb.toString());
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		restart_test = (Button) this.findViewById(R.id.restart_test);
		restart_test.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// try {
				// Process process = Runtime.getRuntime().exec("am instrument -e class com.huika.huixin.member.test.huiXinTest#testLogin -w com.huika.huixin.member.test/android.test.InstrumentationTestRunner");
				// }
				// catch (IOException e) {
				// e.printStackTrace();
				// }
				rootCommand.run("am instrument -e class com.huika.huixin.member.test.huiXinTest#testLogin -w com.huika.huixin.member.test/android.test.InstrumentationTestRunner");
				// rootCommand.run("am instrument -e class ");
				// rootCommand.run("com.huika.huixin.member.test.huiXinTest#testLogin -w  ");
				// rootCommand.run("com.huika.huixin.member.test/android.test.InstrumentationTestRunner");
			}
		});
	}
}
