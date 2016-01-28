package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import com.zsy.frame.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

public class TranslateActivity extends Activity {

	private ImageView trans_iamge;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_translate);
		trans_iamge = (ImageView) findViewById(R.id.trans_image);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_base_translate);
		anim.setFillAfter(true);
		trans_iamge.startAnimation(anim);
	}

	public void translate(View view) {
		Animation anim = new TranslateAnimation(200, 0, 300, 0);
		anim.setDuration(2000);
		anim.setFillAfter(true);
//		OvershootInterpolator overshoot = new OvershootInterpolator();
//		anim.setInterpolator(overshoot);
//		LinearInterpolator lir = new LinearInterpolator();
		BounceInterpolator lir = new BounceInterpolator();
		anim.setInterpolator(lir);
		trans_iamge.startAnimation(anim);
	}
}
