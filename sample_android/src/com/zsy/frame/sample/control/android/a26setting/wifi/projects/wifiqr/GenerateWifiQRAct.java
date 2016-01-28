package com.zsy.frame.sample.control.android.a26setting.wifi.projects.wifiqr;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.decoding.DecodeFormatManager;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.decoding.RGBLuminanceSource;
import com.zsy.frame.sample.control.android.a26setting.wifi.projects.wifiqr.bean.UserWifiInfo;
import com.zsy.frame.sample.control.android.a26setting.wifi.utils.WifiAdmin;

/**
 * @description：分享WIFI
 * @author samy
 * @date 2015-2-26 下午4:20:12
 */
public class GenerateWifiQRAct extends Activity {
	private String result = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a26setting_wifi_projects_wifiqr_wificapture_generate);
		mResultImg = (ImageView) findViewById(R.id.generateImgId);
		mDecodeReslutTxt = (TextView) findViewById(R.id.decodeReslutTxtId);

		WifiAdmin wifiAdmin = new WifiAdmin(this);
		wifiAdmin.startScan();
		/**
		SSID: UTSZ, 
		BSSID: 80:f6:2e:4e:4d:c0, 
		MAC: 38:bc:1a:9f:9e:7b, 
		Supplicant state: COMPLETED, 
		RSSI: -43, 
		CHANNEL:0, 
		Link speed: 150, 
		Net ID: 28, 
		Metered hint: false
		 */
		WifiInfo connectWifiInfo = wifiAdmin.getWifiInfo();
		System.out.println("connectWifiInfo == " + connectWifiInfo.toString());
		List<ScanResult> scanResultList = wifiAdmin.getWifiList();
		// [SSID: 360??WiFi-XJHexSSID: 0x3336303f3f576946692d584a, BSSID: 46:27:1e:ab:97:07, capabilities: [WPA2-PSK-CCMP][ESS], level: -63, frequency: 2437, timestamp: 99610422374, distance: ?(cm), distanceSd: ?(cm),
		// SSID: CMCCHexSSID: 0x434d4343, BSSID: c4:ca:d9:1c:1f:81, capabilities: [WPA2-EAP-CCMP][ESS], level: -70, frequency: 2412, timestamp: 99610422361, distance: ?(cm), distanceSd: ?(cm),
		// SSID: CMCCHexSSID: 0x434d4343, BSSID: c4:ca:d9:0d:05:01, capabilities: [WPA2-EAP-CCMP][ESS], level: -77, frequency: 2437, timestamp: 99610422398, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:5b:31, capabilities: [WAPI-CERT][ESS], level: -75, frequency: 2412, timestamp: 99514054700, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:4d:d1, capabilities: [WAPI-CERT][ESS], level: -59, frequency: 2462, timestamp: 99610422436, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:2b:51, capabilities: [WAPI-CERT][ESS], level: -89, frequency: 2462, timestamp: 99610422449, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:4d:c0, capabilities: [ESS], level: -44, frequency: 5805, timestamp: 99610422411, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5b:80, capabilities: [ESS], level: -61, frequency: 5785, timestamp: 99610422536, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:6f:40, capabilities: [ESS], level: -71, frequency: 5765, timestamp: 99610422506, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5f:80, capabilities: [ESS], level: -60, frequency: 5765, timestamp: 99610422499, distance: ?(cm), distanceSd: ?(cm),
		// SSID: CMCC-WEBHexSSID: 0x434d43432d574542, BSSID: c4:ca:d9:1c:1f:80, capabilities: [ESS], level: -69, frequency: 2412, timestamp: 99610422343, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5f:90, capabilities: [ESS], level: -63, frequency: 2412, timestamp: 99610422349, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:2d:e0, capabilities: [ESS], level: -67, frequency: 5785, timestamp: 99610422525, distance: ?(cm), distanceSd: ?(cm),
		// SSID: and-BusinessHexSSID: 0x616e642d427573696e657373, BSSID: c4:ca:d9:0d:05:02, capabilities: [ESS], level: -79, frequency: 2437, timestamp: 99610422404, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:7c:a0, capabilities: [ESS], level: -66, frequency: 5745, timestamp: 99610422461, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:7c:b0, capabilities: [ESS], level: -72, frequency: 2412, timestamp: 99610422337, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:71:60, capabilities: [ESS], level: -73, frequency: 5785, timestamp: 99610422531, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5b:20, capabilities: [ESS], level: -75, frequency: 5745, timestamp: 99610422455, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:4d:d0, capabilities: [ESS], level: -60, frequency: 2462, timestamp: 99610422430, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5b:30, capabilities: [ESS], level: -78, frequency: 2412, timestamp: 99514054694, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5a:40, capabilities: [ESS], level: -83, frequency: 5765, timestamp: 99610422512, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:2b:50, capabilities: [ESS], level: -90, frequency: 2462, timestamp: 99610422442, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:6f:50, capabilities: [ESS], level: -94, frequency: 2462, timestamp: 99514054780, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:2e:10, capabilities: [ESS], level: -96, frequency: 2462, timestamp: 99514054774, distance: ?(cm), distanceSd: ?(cm),
		// SSID: CMCC-WEBHexSSID: 0x434d43432d574542, BSSID: c4:ca:d9:0d:05:00, capabilities: [ESS], level: -70, frequency: 2437, timestamp: 99514054737, distance: ?(cm), distanceSd: ?(cm),
		// SSID: and-BusinessHexSSID: 0x616e642d427573696e657373, BSSID: c4:ca:d9:1b:ab:52, capabilities: [ESS], level: -71, frequency: 2437, timestamp: 99610422380, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:54:f1, capabilities: [WAPI-CERT][ESS], level: -94, frequency: 2412, timestamp: 99514054687, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:32:d1, capabilities: [WAPI-CERT][ESS], level: -84, frequency: 2437, timestamp: 99514054731, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:2b:40, capabilities: [ESS], level: -80, frequency: 5745, timestamp: 99514054798, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:5f:91, capabilities: [WAPI-CERT][ESS], level: -68, frequency: 2412, timestamp: 99610422355, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:5b:91, capabilities: [WAPI-CERT][ESS], level: -72, frequency: 2437, timestamp: 99610422392, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:7c:b1, capabilities: [WAPI-CERT][ESS], level: -77, frequency: 2412, timestamp: 99610422367, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZ-WAPIHexSSID: 0x5554535a2d57415049, BSSID: 80:f6:2e:4e:5a:51, capabilities: [WAPI-CERT][ESS], level: -92, frequency: 2437, timestamp: 99610422417, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5b:90, capabilities: [ESS], level: -68, frequency: 2437, timestamp: 99610422386, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:5b:40, capabilities: [ESS], level: -77, frequency: 5765, timestamp: 99610422518, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:71:70, capabilities: [ESS], level: -76, frequency: 2412, timestamp: 99610422330, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:2e:00, capabilities: [ESS], level: -81, frequency: 5785, timestamp: 99610422542, distance: ?(cm), distanceSd: ?(cm),
		// SSID: UTSZHexSSID: 0x5554535a, BSSID: 80:f6:2e:4e:60:70, capabilities: [ESS], level: -98, frequency: 2437, timestamp: 99610422423, distance: ?(cm), distanceSd: ?(cm)]
		if (null != scanResultList) {
			String ssid = connectWifiInfo.getSSID();
			for (ScanResult scanResult : scanResultList) {
				System.out.println("scanResult ssid == " + scanResult.SSID + " ssid == " + ssid);
				if (ssid.equals("\"" + scanResult.SSID + "\"")) {
					result += scanResult.SSID;
					result += "#";
					try {
						List<UserWifiInfo> userWifiInfos = wifiAdmin.getUserWifiInfoList();
						if (null != userWifiInfos) {
							for (UserWifiInfo userWifiInfo : userWifiInfos) {
								if (ssid.equals("\"" + userWifiInfo.Ssid + "\"")) {
									result += userWifiInfo.Password;
									result += "#";
									break;
								}
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}

					String capabilites = scanResult.capabilities;
					if (capabilites.contains("wpa") && capabilites.contains("wep")) {
						result += "1";
					}
					else if (capabilites.contains("wep")) {
						result += "2";
					}
					else {
						result += "3";
					}

					if (TextUtils.isEmpty(result)) {
						Toast.makeText(this, "你自己都没连上wifi，还分享个毛啊！", Toast.LENGTH_LONG).show();
						finish();
						return;
					}
					else {
						generate(result);
					}
					break;
				}
			}
		}
		else {
			Toast.makeText(this, "你自己都没连上wifi，还分享个毛啊！", Toast.LENGTH_LONG).show();
			finish();
			return;
		}
	}

	@SuppressWarnings("deprecation")
	private void generate(String result) {
		if (TextUtils.isEmpty(result)) { return; }
		try {
			// 判断result合法性
			if (result == null || "".equals(result) || result.length() < 1) { return; }
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(result, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			// 下面这里按照二维码的算法，逐个生成二维码的图片，
			// 两个for循环是图片横列扫描的结果
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					}
					else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}
				}
			}
			// 生成二维码图片的格式，使用ARGB_8888
			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			// 显示到一个ImageView上面
			mResultImg.setBackground(new BitmapDrawable(bitmap));
			decodeBitmap(bitmap);
		}
		catch (WriterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description：生产完图片显示在Imageview上显示后，再次解析部分数据显示在Textview控件上
	 * @author samy
	 * @date 2015-2-27 下午7:33:39
	 */
	private String decodeBitmap(Bitmap bitmap) {
		MultiFormatReader multiFormatReader = new MultiFormatReader();
		// 解码的参数
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>(2);
		// 可以解析的编码类型
		Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
		if (decodeFormats == null || decodeFormats.isEmpty()) {
			decodeFormats = new Vector<BarcodeFormat>();
			// 这里设置可扫描的类型，我这里选择了都支持
			decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
		}
		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);

		// 设置继续的字符编码格式为UTF8
		// hints.put(DecodeHintType.CHARACTER_SET, "UTF8");

		// 设置解析配置参数
		multiFormatReader.setHints(hints);

		// 开始对图像资源解码
		try {
			// Bitmap bitmap = ((BitmapDrawable) qr_image.getDrawable()).getBitmap();
			RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
			// BitmapLuminanceSource source = new BitmapLuminanceSource(bitmap);
			BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
			Result rawResult = multiFormatReader.decode(bitmap1);
			// Result rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new com.uperone.zxing.decoding.BitmapLuminanceSource(bitmap))));
			mDecodeReslutTxt.setText(new StringBuilder().append("包括内容：").append(rawResult.getText()).append("\n编码方式：").append(rawResult.getBarcodeFormat()).toString());
		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static final int QR_HEIGHT = 520, QR_WIDTH = 520;
	private ImageView mResultImg = null;
	private TextView mDecodeReslutTxt = null;
}
