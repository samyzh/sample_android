package com.zsy.frame.sample.control.android.a01ui.a19progress;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.lzyzsd.circleprogress.CircleProgress;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;

public class CircleProgressAct extends BaseAct {
	private Timer timer;
	private DonutProgress donutProgress;
	private CircleProgress circleProgress;
	private ArcProgress arcProgress;

	
	public CircleProgressAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showActivity(aty, CircleProgressViewPagerAct.class);
			}
		});
		donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
		circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
		arcProgress = (ArcProgress) findViewById(R.id.arc_progress);

		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						donutProgress.setProgress(donutProgress.getProgress() + 1);
						circleProgress.setProgress(circleProgress.getProgress() + 1);
						arcProgress.setProgress(arcProgress.getProgress() + 1);
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
		setContentView(R.layout.activity_a01ui_a19progress_circleprogress);
	}
}
