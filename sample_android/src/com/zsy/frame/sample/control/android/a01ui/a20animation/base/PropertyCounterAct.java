package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import com.zsy.frame.sample.R;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PropertyCounterAct extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_property_counter);

	}

	public <T> void click(View view) {
		final Button button = (Button) view;
		/**
		 * Int型
		 */
		// ValueAnimator valueAnimator = ValueAnimator.ofInt(0,100);
		// valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
		// @Override
		// public void onAnimationUpdate(ValueAnimator animation) {
		// Integer value = (Integer) animation.getAnimatedValue();
		// button.setText("" + value);
		// }
		// });
		/**
		 * T型
		 */
		ValueAnimator valueAnimator = ValueAnimator.ofObject(new TypeEvaluator<T>() {

			@Override
			public T evaluate(float fraction, T startValue, T endValue) {
				return null;
			}
		}, 100);
		valueAnimator.setDuration(5000);
		valueAnimator.start();

//		AlphaAnimation alpha = new AlphaAnimation(0, 1);
//		alpha.setDuration(3000);
//		ListView listView = new ListView(context);
//		// LayoutAnimationController可以控制一组控件按照规定显示
//		LayoutAnimationController animationController = new LayoutAnimationController(alpha);
//		animationController.setOrder(LayoutAnimationController.ORDER_RANDOM);
//		listView.startLayoutAnimation();
	}

}
