package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.views;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.decoding.RGBLuminanceSource;

public class EcodeingProductView extends LinearLayout {
	EditText qr_text;
	String TAG = "..";
	int QR_WIDTH = 350, QR_HEIGHT = 350;
	ImageView qr_image;
	Button bt, bt2;
	Bitmap bitmap;
	TextView qr_result;

	public EcodeingProductView(Context context) {
		super(context);
		this.setOrientation(LinearLayout.VERTICAL);
		qr_image = new ImageView(context);
		qr_text = new EditText(context);
		qr_result = new TextView(context);
		bt = new Button(context);
		bt.setText("生成二维码图片");
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				createQRCode();
			}
		});
		bt2 = new Button(context);
		bt2.setText("解析二维码图片");
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				handleDecode();
			}
		});

		addView(qr_text);
		addView(qr_image);
		addView(bt);
		addView(bt2);
		addView(qr_result);

	}

	/**
	 * @description：生成QR图
	 * @author samy
	 * @date 2015-2-27 下午2:02:47
	 */
	private void createQRCode() {
		final int BLACK = 0xff000000;
		try {
			// 需要引入core包
			QRCodeWriter writer = new QRCodeWriter();
			String text = qr_text.getText().toString();
			Log.i(TAG, "生成的文本：" + text);
			if (text == null || "".equals(text) || text.length() < 1) { return; }
			// 把输入的文本转为二维码
			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT);
			System.out.println("w:" + martix.getWidth() + "h:" + martix.getHeight());

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int width = bitMatrix.getWidth();
			int height = bitMatrix.getHeight();
			int[] pixels = new int[width * height];
			// int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * width + x] = BLACK;
						// pixels[y * width + x] = 0xff000000;
					}
					else {
						pixels[y * width + x] = 0xffffffff;
					}
				}
			}
			bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			qr_image.setImageBitmap(bitmap);
			try {
				saveMyBitmap(bitmap, "code");
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		catch (WriterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description：保持生产的QR图片到SD里面，方便后面解析QR图片使用
	 * @author samy
	 * @date 2015-2-27 下午2:03:37
	 */
	public void saveMyBitmap(Bitmap bm, String bitName) throws IOException {
		File f = new File("/mnt/sdcard/" + bitName + ".png");
		f.createNewFile();
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bm.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description：解析QR图片
	 * @author samy
	 * @date 2015-2-27 下午2:04:39
	 */
	private void handleDecode() {
		// 解码的参数
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		/**
		 * 多格式解码处理显示
		 */
		// 可以解析的编码类型
		// Vector<BarcodeFormat> decodeFormats = new Vector<BarcodeFormat>();
		// if (decodeFormats == null || decodeFormats.isEmpty()) {
		// decodeFormats = new Vector<BarcodeFormat>();
		// // 这里设置可扫描的类型，我这里选择了都支持
		// decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
		// decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
		// decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
		// }
		// hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
		// MultiFormatReader multiFormatReader = new MultiFormatReader();
		// // 设置解析配置参数
		// multiFormatReader.setHints(hints);
		// 开始对图像资源解码
		// try {
		// Result rawResult = multiFormatReader.decodeWithState(new BinaryBitmap(new HybridBinarizer(new com.uperone.zxing.decoding.BitmapLuminanceSource(bitmap))));
		// mDecodeReslutTxt.setText(new StringBuilder().append("包括内容：").append(rawResult.getText()).append("\n编码方式：").append(rawResult.getBarcodeFormat()).toString());
		// }
		// catch (NotFoundException e) {
		// e.printStackTrace();
		// }

		
		// 设置继续的字符编码格式为UTF8
		hints.put(DecodeHintType.CHARACTER_SET, "UTF8");

		// 获得待解析的图片
		Bitmap bitmap = ((BitmapDrawable) qr_image.getDrawable()).getBitmap();
		RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		/**
		 * 多格式解码处理显示
		 */
//		Result rawResult = multiFormatReader.decode(bitmap1);
		/**
		 * 单格式（utf-8格式）解码处理显示
		 */
		QRCodeReader reader = new QRCodeReader();
		Result result;
		try {
			result = reader.decode(bitmap1);
			result = reader.decode(bitmap1, hints);
			// 得到解析后的文字
			qr_result.setText(result.getText());
		}
		catch (NotFoundException e) {
			e.printStackTrace();
		}
		catch (ChecksumException e) {
			e.printStackTrace();
		}
		catch (FormatException e) {
			e.printStackTrace();
		}
	}

}
