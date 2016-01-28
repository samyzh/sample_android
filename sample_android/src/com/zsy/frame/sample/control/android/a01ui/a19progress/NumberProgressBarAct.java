package com.zsy.frame.sample.control.android.a01ui.a19progress;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

public class NumberProgressBarAct extends BaseAct {
	private int counter = 0;
	private Timer timer;
	
//	
//
//	public NumberProgressBarAct() {
//		super();
//		setHiddenActionBar(false);
//	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		final NumberProgressBar bnp = (NumberProgressBar) findViewById(R.id.numberbar1);
		counter = 0;
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						bnp.incrementProgressBy(1);
						counter++;
						if (counter == 110) {
							bnp.setProgress(0);
							counter = 0;
						}
					}
				});
			}
		}, 1000, 100);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		timer.cancel();
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a01ui_a19progress_numberprogressbar);
	}
}
