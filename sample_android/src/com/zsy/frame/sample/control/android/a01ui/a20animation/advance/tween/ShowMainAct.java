package com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween;

import com.zsy.frame.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

/**
 * Created by admin on 2015/11/14.
 */
public class ShowMainAct extends Activity {

	private ImageView example;
	private RadioGroup chooseanim;
	private AlphaAnimation alphaAnimation;
	private RotateAnimation rotateAnimation;
	private ScaleAnimation scaleAnimation;
	private TranslateAnimation translateAnimation;
	private Animation animation;
	private CheckBox detachWallpaper, fillAfter, fillBefore, repeatMode;
	private EditText duration, repeatCount, fromAlpha, toAlpha, fromDegrees, toDegrees, pivotX, pivotY, fromXScale,
			toXScale, fromYScale, toYScale, fromXDelta, toXDelta, fromYDelta, toYDelta;

	private int animid;
	private int durationnum = 3000, repeatcount = 0;
	float fromalpha = 1f, toalpha = 0f, fromdegrees = 0f, todegrees = 360f, pivotx = 0f, pivoty = 0f, fromxScale = 1f,
			toxScale = 0.5f, fromyScale = 1f, toyScale = 0.5f, fromxDelta = 0f, toxDelta = 100f, fromyDelta = 0f,
			toyDelta = 100f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a20animation_advance_tween_showmain);

		findview();

		alphaAnimation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_advance_tween_alpha);
		rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_advance_tween_rotate);
		scaleAnimation = (ScaleAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_advance_tween_scale);
		translateAnimation = (TranslateAnimation) AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_advance_tween_translate);

		animation = alphaAnimation;

	}

	private void findview() {
		example = (ImageView) findViewById(R.id.example);

		detachWallpaper = (CheckBox) findViewById(R.id.detachWallpaper);
		fillAfter = (CheckBox) findViewById(R.id.fillAfter);
		fillBefore = (CheckBox) findViewById(R.id.fillBefore);
		repeatMode = (CheckBox) findViewById(R.id.repeatMode);

		duration = (EditText) findViewById(R.id.duration);
		repeatCount = (EditText) findViewById(R.id.repeatCount);
		fromAlpha = (EditText) findViewById(R.id.fromAlpha);
		toAlpha = (EditText) findViewById(R.id.toAlpha);
		fromDegrees = (EditText) findViewById(R.id.fromDegrees);
		toDegrees = (EditText) findViewById(R.id.toDegrees);
		pivotX = (EditText) findViewById(R.id.pivotX);
		pivotY = (EditText) findViewById(R.id.pivotY);
		fromXScale = (EditText) findViewById(R.id.fromXScale);
		toXScale = (EditText) findViewById(R.id.toXScale);
		fromYScale = (EditText) findViewById(R.id.fromYScale);
		toYScale = (EditText) findViewById(R.id.toYScale);
		fromXDelta = (EditText) findViewById(R.id.fromXDelta);
		toXDelta = (EditText) findViewById(R.id.toXDelta);
		fromYDelta = (EditText) findViewById(R.id.fromYDelta);
		toYDelta = (EditText) findViewById(R.id.toYDelta);

		chooseanim = (RadioGroup) findViewById(R.id.chooseanim);

		chooseanim.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				hideother();
				animid = checkedId;
				switch (checkedId) {

				case R.id.alpha:
					animation = alphaAnimation;
					fromAlpha.setVisibility(View.VISIBLE);
					toAlpha.setVisibility(View.VISIBLE);

					break;
				case R.id.rotate:
					animation = rotateAnimation;
					fromDegrees.setVisibility(View.VISIBLE);
					toDegrees.setVisibility(View.VISIBLE);

					pivotX.setVisibility(View.VISIBLE);
					pivotY.setVisibility(View.VISIBLE);
					break;
				case R.id.scale:
					animation = scaleAnimation;
					fromXScale.setVisibility(View.VISIBLE);
					toXScale.setVisibility(View.VISIBLE);
					fromYScale.setVisibility(View.VISIBLE);
					toYScale.setVisibility(View.VISIBLE);

					pivotX.setVisibility(View.VISIBLE);
					pivotY.setVisibility(View.VISIBLE);
					break;
				case R.id.translate:
					animation = translateAnimation;
					fromXDelta.setVisibility(View.VISIBLE);
					toXDelta.setVisibility(View.VISIBLE);
					fromYDelta.setVisibility(View.VISIBLE);
					toYDelta.setVisibility(View.VISIBLE);
					break;

				}

			}
		});

		example.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				switch (animid) {

				case R.id.alpha:

					alphaAnimation = new AlphaAnimation(fromalpha, toalpha);
					setdefault(alphaAnimation);
					animation = alphaAnimation;
					break;
				case R.id.rotate:

					rotateAnimation = new RotateAnimation(fromdegrees, todegrees, pivotx, pivoty);
					setdefault(rotateAnimation);
					animation = rotateAnimation;
					break;
				case R.id.scale:

					scaleAnimation = new ScaleAnimation(fromxScale, toxScale, fromyScale, toyScale, pivotx, pivoty);
					setdefault(scaleAnimation);
					animation = scaleAnimation;
					break;
				case R.id.translate:
					translateAnimation = new TranslateAnimation(fromxDelta, toxDelta, fromyDelta, toyDelta);
					setdefault(translateAnimation);
					animation = translateAnimation;
					break;

				}

				animation.setInterpolator(new LinearInterpolator());
				v.startAnimation(animation);
			}
		});

		detachWallpaper.setOnCheckedChangeListener(onCheckedChangeListener);
		fillAfter.setOnCheckedChangeListener(onCheckedChangeListener);
		fillBefore.setOnCheckedChangeListener(onCheckedChangeListener);
		repeatMode.setOnCheckedChangeListener(onCheckedChangeListener);

		hideother();
		fromAlpha.setVisibility(View.VISIBLE);
		toAlpha.setVisibility(View.VISIBLE);
		animid = R.id.alpha;

		duration.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				if (s.toString() != null & s.toString().length() > 0) {
					durationnum = Integer.parseInt(s.toString());
				} else {
					durationnum = 3000;
				}

			}
		});

		repeatCount.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					repeatcount = Integer.parseInt(s.toString());
				} else {
					repeatcount = 0;
				}

			}
		}

		);

		fromAlpha.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					fromalpha = Float.parseFloat(s.toString());
				} else {
					fromalpha = 1f;
				}

			}
		}

		);

		toAlpha.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					toalpha = Float.parseFloat(s.toString());
				} else {
					toalpha = 0f;
				}

			}
		}

		);

		fromDegrees.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					fromdegrees = Float.parseFloat(s.toString());
				} else {
					fromdegrees = 0f;
				}

			}
		}

		);

		toDegrees.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					todegrees = Float.parseFloat(s.toString());
				} else {
					todegrees = 360f;
				}

			}
		}

		);

		pivotX.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					pivotx = Float.parseFloat(s.toString());
				} else {
					pivotx = 0f;
				}

			}
		}

		);

		pivotY.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					pivoty = Float.parseFloat(s.toString());
				} else {
					pivoty = 0f;
				}

			}
		}

		);

		fromXScale.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					fromxScale = Float.parseFloat(s.toString());
				} else {
					fromxScale = 1f;
				}

			}
		}

		);

		toXScale.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					toxScale = Float.parseFloat(s.toString());
				} else {
					toxScale = 0.5f;
				}

			}
		}

		);

		fromYScale.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					fromyScale = Float.parseFloat(s.toString());
				} else {
					fromyScale = 1f;
				}

			}
		}

		);

		toYScale.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					toyScale = Float.parseFloat(s.toString());
				} else {
					toyScale = 0.5f;
				}

			}
		}

		);

		fromXDelta.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					fromxDelta = Float.parseFloat(s.toString());
				} else {
					fromxDelta = 0;
				}

			}
		}

		);

		toXDelta.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					toxDelta = Float.parseFloat(s.toString());
				} else {
					toxDelta = 100f;
				}

			}
		}

		);

		fromYDelta.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					fromyDelta = Float.parseFloat(s.toString());
				} else {
					fromyDelta = 0f;
				}

			}
		}

		);

		toYDelta.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.toString() != null & s.toString().length() > 0) {
					toyDelta = Float.parseFloat(s.toString());
				} else {
					toyDelta = 100f;
				}

			}
		}

		);
	}

	CheckBox.OnCheckedChangeListener onCheckedChangeListener = new CheckBox.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		}
	};

	private void hideother() {

		fromAlpha.setVisibility(View.GONE);
		toAlpha.setVisibility(View.GONE);

		fromDegrees.setVisibility(View.GONE);
		toDegrees.setVisibility(View.GONE);

		pivotX.setVisibility(View.GONE);
		pivotY.setVisibility(View.GONE);

		fromXScale.setVisibility(View.GONE);
		toXScale.setVisibility(View.GONE);
		fromYScale.setVisibility(View.GONE);
		toYScale.setVisibility(View.GONE);

		fromXDelta.setVisibility(View.GONE);
		toXDelta.setVisibility(View.GONE);
		fromYDelta.setVisibility(View.GONE);
		toYDelta.setVisibility(View.GONE);
	}

	private void setdefault(Animation animation) {
		animation.setDetachWallpaper(detachWallpaper.isChecked());
		animation.setFillAfter(fillAfter.isChecked());
		animation.setFillBefore(fillBefore.isChecked());

		int mode = 1;
		if (repeatMode.isChecked()) {
			mode = 2;
		}
		animation.setRepeatMode(mode);

		animation.setDuration(durationnum);

		animation.setRepeatCount(repeatcount);
	}
}
