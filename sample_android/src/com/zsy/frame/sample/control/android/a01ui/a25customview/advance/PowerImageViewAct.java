package com.zsy.frame.sample.control.android.a01ui.a25customview.advance;

import android.app.Activity;
import android.os.Bundle;

import com.zsy.frame.sample.R;

/**
 * @description：
 * 在AndroidManifest.xml中还有一点需要注意，有些4.0以上系统的手机启动了硬件加速功能之后会导致GIF动画播放不出来，
 * 因此我们需要在AndroidManifest.xml中去禁用硬件加速功能，可以通过指定android:hardwareAccelerated属性来完成
 * 
 *发现这个播放gif图片还是有点问题；
 * @author samy
 * @date 2015-3-29 下午8:50:41
 */
public class PowerImageViewAct extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a25customview_advance_powerimageview);
	}

}
