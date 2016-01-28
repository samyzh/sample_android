package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import com.zsy.frame.sample.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PropertyFirstAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_property_first);
	}

	public void clickIV(View view) {
		Toast.makeText(PropertyFirstAct.this, "clicked", Toast.LENGTH_LONG).show();
	}

	public void move(View view) {
		ImageView imageView = (ImageView) findViewById(R.id.imageView1);

//		 TranslateAnimation ta = new TranslateAnimation(0, 200, 0, 0);
//		 ta.setDuration(1000);
//		 ta.setFillAfter(true);
//		 imageView.startAnimation(ta);

//		 ObjectAnimator.ofFloat(imageView, "translationX", 0F,200F).setDuration(1000).start();
//		 ObjectAnimator.ofFloat(imageView, "translationY", 0F,200F).setDuration(1000).start();
//		 ObjectAnimator.ofFloat(imageView, "X", 0F,200F).setDuration(1000).start();
//		 ObjectAnimator.ofFloat(imageView, "rotation", 0F,200F).setDuration(1000).start();
//		/**
//		 * 方式一：
//		 */
//		ObjectAnimator.ofFloat(imageView, "rotation", 0F, 200F).setDuration(1000).start();
//		ObjectAnimator.ofFloat(imageView, "translationX", 0F, 200F).setDuration(1000).start();
//		ObjectAnimator.ofFloat(imageView, "translationY", 0F, 200F).setDuration(1000).start();
//		/**
//		 * 方式二：
//		 */
//		PropertyValuesHolder p1 = PropertyValuesHolder.ofFloat("rotation", 0F, 200F);
//		PropertyValuesHolder p2 = PropertyValuesHolder.ofFloat("translationX", 0F, 200F);
//		PropertyValuesHolder p3 = PropertyValuesHolder.ofFloat("translationY", 0F, 200F);
//		ObjectAnimator.ofPropertyValuesHolder(imageView, p1, p2, p3).setDuration(5000).start();
//		/**
//		 * 方式三：
//		 */
//		ObjectAnimator ObjectAnimator1 = ObjectAnimator.ofFloat(imageView, "rotation", 0F, 200F);
//		ObjectAnimator ObjectAnimator2 = ObjectAnimator.ofFloat(imageView, "translationX", 0F, 200F);
//		ObjectAnimator ObjectAnimator3 = ObjectAnimator.ofFloat(imageView, "translationY", 0F, 200F);
//		AnimatorSet set = new AnimatorSet();
//		// set.playTogether(ObjectAnimator1,ObjectAnimator2,ObjectAnimator3);
//		// set.playSequentially(ObjectAnimator1,ObjectAnimator2,ObjectAnimator3);
//		set.play(ObjectAnimator2).with(ObjectAnimator3);
//		set.play(ObjectAnimator1).with(ObjectAnimator2);
//		set.setDuration(8000).start();
		// 动画部分
		ObjectAnimator oaLinstener = ObjectAnimator.ofFloat(imageView, "alpha", 0F, 1F);
		oaLinstener.setDuration(1000);
		/**
		 * 方式一：
		 */
//		oaLinstener.addListener(new AnimatorListener() {
//
//			@Override
//			public void onAnimationStart(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationRepeat(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onAnimationEnd(Animator animation) {
//				// TODO Auto-generated method stub
//				Toast.makeText(PropertyFirstAct.this, "oaLinstener END", Toast.LENGTH_LONG).show();
//			}
//
//			@Override
//			public void onAnimationCancel(Animator animation) {
//				// TODO Auto-generated method stub
//
//			}
//		});
		/**
		 * 方式二
		 */
		oaLinstener.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				super.onAnimationEnd(animation);
				Toast.makeText(PropertyFirstAct.this, "oaLinstener END", Toast.LENGTH_LONG).show();
			}
		});
		oaLinstener.start();
	}

}
