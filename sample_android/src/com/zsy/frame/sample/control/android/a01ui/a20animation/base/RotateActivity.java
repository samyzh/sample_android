package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import com.zsy.frame.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

public class RotateActivity extends Activity {

	private int currAngle;
	private View piechart;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_rotate);

		piechart = findViewById(R.id.piechart);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_base_rotate);
		piechart.startAnimation(animation);
	}
	
	public void positive(View v) {
		Animation anim = new RotateAnimation(currAngle, currAngle + 180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		/** 匀速插值器 */
		LinearInterpolator lir = new LinearInterpolator();
		anim.setInterpolator(lir);
		anim.setDuration(1000);
		/** 动画完成后不恢复原状 */
		anim.setFillAfter(true);
		currAngle += 180;
		if (currAngle > 360) {
			currAngle = currAngle - 360;
		}
		piechart.startAnimation(anim);
	}
	
	public void negative(View v) {
		Animation anim = new RotateAnimation(currAngle, currAngle - 180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		/** 匀速插值器 */
		LinearInterpolator lir = new LinearInterpolator();
		anim.setInterpolator(lir);
		anim.setDuration(1000);
		/** 动画完成后不恢复原状 */
		anim.setFillAfter(true);
		currAngle -= 180;
		if (currAngle < -360) {
			currAngle = currAngle + 360;
		}
		piechart.startAnimation(anim);
	}
}