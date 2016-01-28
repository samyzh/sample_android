package com.zsy.frame.sample.control.android.a01ui.a19progress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBarUtils;
import fr.castorflex.android.smoothprogressbar.SmoothProgressDrawable;

public class SmoothProgressBarAct extends BaseAct {
	private ProgressBar mProgressBar1;
	private SmoothProgressBar mGoogleNow;
	private SmoothProgressBar mPocketBar;

	View mProgressBar;

	/**
	 * 目前发现自定义控件在构造类时处理数据有点问题;
	 * 当时现在发现跟项目的重复的ID app_name有关系
	 * 现在就发现这个的构造有问题；也自己自定义的样式有关
	 */
	// SmoothProgressBarAct() {
	// super();
	// setHiddenActionBar(false);
	// }

	@Override
	protected void initWidget(Bundle savedInstance) {
		super.initWidget(savedInstance);

		mProgressBar1 = (ProgressBar) findViewById(R.id.progressbar2);
		mPocketBar = (SmoothProgressBar) findViewById(R.id.pocket);

		mProgressBar1.setIndeterminateDrawable(new SmoothProgressDrawable.Builder(this).interpolator(new AccelerateInterpolator()).build());

		mGoogleNow = (SmoothProgressBar) findViewById(R.id.google_now);
		mPocketBar.setSmoothProgressDrawableBackgroundDrawable(SmoothProgressBarUtils.generateDrawableWithColors(getResources().getIntArray(R.array.pocket_background_colors), ((SmoothProgressDrawable) mPocketBar.getIndeterminateDrawable()).getStrokeWidth()));

		findViewById(R.id.button_make).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SmoothProgressBarAct.this, SmoothProgressBarNextAct.class);
				startActivity(intent);
			}
		});

		findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPocketBar.progressiveStart();
			}
		});

		findViewById(R.id.finish).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPocketBar.progressiveStop();
			}
		});
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a19progress_smoothprogressbar);
	}
	
//    @Override
//    public void onCleanStarted(Context context) {
//        if (isProgressBarVisible()) {
//            showProgressBar(false);
//        }
//
//        if (!RubbishCleanActivity.this.isFinishing()) {
//            showDialogLoading();
//        }
//    }
//    @Override
//    public void onScanStarted(Context context) {
//        mProgressBarText.setText(R.string.scanning);
//        showProgressBar(true);
//    }
//
//    @Override
//    public void onScanProgressUpdated(Context context, int current, int max) {
//        mProgressBarText.setText(getString(R.string.scanning_m_of_n, current, max));
//
//    }

	private boolean isProgressBarVisible() {
		return mProgressBar.getVisibility() == View.VISIBLE;
	}

	private void showProgressBar(boolean show) {
		if (show) {
			mProgressBar.setVisibility(View.VISIBLE);
		}
		else {
			mProgressBar.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
			mProgressBar.setVisibility(View.GONE);
		}
	}
	
//	  <LinearLayout
//      android:id="@+id/progressBar"
//      android:layout_width="match_parent"
//      android:layout_height="match_parent"
//      android:background="?android:attr/windowBackground"
//      android:gravity="center"
//      android:orientation="vertical"
//      android:visibility="gone">
//
//      <fr.castorflex.android.circularprogressbar.CircularProgressBar
//          android:id="@+id/progressBar2"
//          android:layout_width="50dip"
//          android:layout_height="50dip"
//          android:indeterminate="true"
//          app:cpb_colors="@array/gplus_colors" />
//
//      <TextView
//          android:id="@+id/progressBarText"
//          android:layout_width="wrap_content"
//          android:layout_height="wrap_content"
//          android:paddingTop="4dip"
//          android:singleLine="true"
//          android:text="@string/scanning"
//          android:textAppearance="?android:attr/textAppearanceSmall" />
//
//  </LinearLayout>

}
