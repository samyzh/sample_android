package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import com.zsy.frame.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

public class ScaleActivity extends Activity {
	
	private ImageView scale_iamge;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_scale);
		scale_iamge = (ImageView) findViewById(R.id.scale_image);
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_base_scale);
		anim.setFillAfter(true);
		scale_iamge.startAnimation(anim);
	}
	
	public void sclae(View view) {
		Animation anim = new ScaleAnimation(2.0f, 1.0f, 2.0f, 1.0f, 
				Animation.RELATIVE_TO_SELF, 0.5f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(2000);
		anim.setFillAfter(true);
		BounceInterpolator bounce = new BounceInterpolator();
		anim.setInterpolator(bounce);
		scale_iamge.startAnimation(anim);
	}
}
