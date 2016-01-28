package com.zsy.frame.sample.control.android.a26setting.cloud.printer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.print.PrintHelper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.zsy.frame.sample.R;

/**
 * A demo to print bitmap with Google cloud print service.
 * <P>
 * At present, the mobile terminal print, Android only through a Chrome browser
 * or the third party application. After android 4.4, Google integrate Cloud
 * print application of official
 * <P>
 * @author Kinny.Qin 2014-06-27
 * 1．无线打印机的打印原理如下图：
说明：移动设备调用Google开放的API将本地打印数据发送到谷歌服务，谷歌服务再将这些数据发送到HP服务，HP服务接着将数据发送到打印机，并且向打印机发送打印指令。

2.操作流程
(1)先将支持云打印功能的打印机绑定（使用打印机邮件地址）到谷歌服务器
(2) PC端设置云打印：关联谷歌账户
(3)android移动端登陆跟PC端相同的gmail账号，打开设置中的打印—>HP打印服务插件----->云打印
(4)确保网络能够访问google，最好是能使用VPN
(5)调用自己的app实现打印
上述步骤可以参考：
http://oa.zol.com.cn/418/4185224.html 
https://support.google.com/cloudprint/?hl=zh-Hans。

3.开放的打印API
Google4.4之后，开放了打印的API，包括打印位图的PrintHelper，打印HTML、自定义文档等等的PrintManager。
可以参考：https://developer.android.com/reference/android/print/PrintManager.html。
4.设备系统要求
 移动设备系统要求android4.4+，打印机要求支持云打印功能
 * 
 */
public class CloudPrinterTestAct extends Activity implements OnClickListener {
	private Button btnPrint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a26setting_cloud_printer_cloudprinter_testact);
		btnPrint = (Button) findViewById(R.id.btnprint);
		btnPrint.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int view_id = v.getId();
		switch (view_id) {
			case R.id.btnprint:
				print();
				break;
		}

	}

	/**
	 * Use API PrintHelper to print BitMap
	 */
	private void print() {
		// Get the print manager.
		PrintHelper printHelper = new PrintHelper(this);

		// Set the desired scale mode.
		printHelper.setScaleMode(PrintHelper.SCALE_MODE_FIT);

		// Get the bitmap for the ImageView's drawable.
		// Bitmap bitmap = ((BitmapDrawable)
		// imageView.getDrawable()).getBitmap();
		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher);
		// judge whether the system support print function
		boolean isSystemSupport = PrintHelper.systemSupportsPrint();
		if (isSystemSupport) {
			// Print the bitmap.
			printHelper.printBitmap("Print Bitmap", bitmap);
		}
		else {
			Toast.makeText(getApplicationContext(), "Your system don't support print", Toast.LENGTH_LONG).show();
		}
	}

}
