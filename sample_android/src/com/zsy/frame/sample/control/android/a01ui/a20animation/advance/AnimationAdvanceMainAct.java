package com.zsy.frame.sample.control.android.a01ui.a20animation.advance;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween.AllTweenAct;
import com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween.ShowMainAct;
import com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween.ShowTweenAct;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AnimationAdvanceMainAct extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_advance_main);

		findViewById(R.id.showmain).setOnClickListener(this);
		findViewById(R.id.showtween).setOnClickListener(this);
		findViewById(R.id.alltween).setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.showmain:
			// TODO implement
			startActivity(new Intent(AnimationAdvanceMainAct.this, ShowMainAct.class));
			break;
		case R.id.showtween:
			// TODO implement
			startActivity(new Intent(AnimationAdvanceMainAct.this, ShowTweenAct.class));
			break;
		case R.id.alltween:
			// TODO implement
			startActivity(new Intent(AnimationAdvanceMainAct.this, AllTweenAct.class));
			break;
		}
	}
}
