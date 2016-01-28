package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import com.zsy.frame.sample.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * 出现这种现象是因为当我们在onCreate中调用AnimationDrawable的start方法时，窗口Window对象还没有完全初始化，AnimationDrawable不能完全追加到窗口Window对象中，
 * 那么该怎么办呢？
 * 我们需要把这段代码放在onWindowFocusChanged方法中，
 * 当Activity展示给用户时，onWindowFocusChanged方法就会被调用，我们正是在这个时候实现我们的动画效果。
 * 当然，onWindowFocusChanged是在onCreate之后被调用的
 * @description：
 * @author samy
 * @date 2016年1月25日 下午6:30:09
 */
public class FrameActivity extends Activity {

	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_frame);
		image = (ImageView) findViewById(R.id.frame_image);

		image.setBackgroundResource(R.anim.anim_a01ui_a20animation_base_frame);
		AnimationDrawable anim = (AnimationDrawable) image.getBackground();
		anim.start();
	}

	// @Override
	// public void onWindowFocusChanged(boolean hasFocus) {
	// super.onWindowFocusChanged(hasFocus);
	// image.setBackgroundResource(R.anim.frame);
	// AnimationDrawable anim = (AnimationDrawable) image.getBackground();
	// anim.start();
	// }

	public void stopFrame(View view) {
		AnimationDrawable anim = (AnimationDrawable) image.getBackground();
		if (anim.isRunning()) { // 如果正在运行,就停止
			anim.stop();
		}
	}

	public void runFrame(View view) {
		// 完全编码实现的动画效果
		AnimationDrawable anim = new AnimationDrawable();
//		amp0-3
		for (int i = 0; i <= 3; i++) {
			// 根据资源名称和目录获取R.java中对应的资源ID
			int id = getResources().getIdentifier("a01ui_a20animation_base_frame_amp" + i, "drawable", getPackageName());
			// 根据资源ID获取到Drawable对象
			Drawable drawable = getResources().getDrawable(id);
			// 将此帧添加到AnimationDrawable中
			anim.addFrame(drawable, 300);
		}
		anim.setOneShot(false); // 设置为loop
		image.setBackgroundDrawable(anim); // 将动画设置为ImageView背景
		anim.start(); // 开始动画
	}
}