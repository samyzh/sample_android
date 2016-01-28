package com.zsy.frame.sample.control.android.a01ui.a20animation.base;

import java.util.ArrayList;
import java.util.List;

import com.zsy.frame.sample.R;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class PropertyPathAct extends Activity implements OnClickListener {
	private int[] res = { R.id.composer_camera, R.id.composer_music, R.id.composer_place, R.id.composer_sleep, R.id.composer_thought};
	private List<ImageView> imageViewList = new ArrayList<ImageView>();
	private boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_base_property_path);

		for (int i = 0; i < res.length; i++) {
			ImageView imageView = (ImageView) findViewById(res[i]);
			imageView.setOnClickListener(this);
			imageViewList.add(imageView);
		}
		findViewById(R.id.button1).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//			最上层这个
			case R.id.button1:
				if (flag) {
					startAnim();
				}else {
					closeAnim();
				}
				break;
			default:
				Toast.makeText(this, "click"+v.getId(), Toast.LENGTH_LONG).show();
				break;
		}
	}

	private void closeAnim() {
		for (int i = 0; i < res.length; i++) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", i * 150, 0F);
			animator.setDuration(500);
			animator.setStartDelay(i * 300);
			animator.start();
			flag = true;
		}
	}

	private void startAnim() {
		for (int i = 0; i < res.length; i++) {
			ObjectAnimator animator = ObjectAnimator.ofFloat(imageViewList.get(i), "translationY", 0F, i * 150);
			animator.setDuration(500);
			animator.setStartDelay(i * 300);
			animator.start();
			flag = false;
		}
	}
}
