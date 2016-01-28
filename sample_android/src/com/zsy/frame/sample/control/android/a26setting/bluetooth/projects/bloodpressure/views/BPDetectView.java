package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zsy.frame.sample.R;

/**
 * Created by rainy.liao on 2014/12/1. 血压检测控件
 */
public class BPDetectView extends FrameLayout {
	public static final int STATE_PREPARING = 0;// 准备中
	public static final int STATE_DETECTING = 1;// 检测中
	public static final int STATE_COMPLETE = 2;// 检测完毕

	private String aboveTipText = null;
	private String belowTipText = null;
	private int progress = 0; // 当前进度
	private DataSaveListen dataSaveListen = null;

	private int smallTextSize = 32;
	private int larginTextSize = 100;
	/**
	 * 当前状态
	 */
	private int state = STATE_PREPARING;

	private TextView aboveTxtView;
	private TextView belowTxtView;
	private ScaleDiskView scaleDiskView;

	private boolean isInit = false;

	public BPDetectView(Context context) {
		super(context);
	}

	public BPDetectView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BPDetectView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		initView();
	}

	private void initView() {
		if (isInit) { return; }
		isInit = true;
		aboveTxtView = (TextView) this.findViewById(R.id.blood_data_text);
		belowTxtView = (TextView) this.findViewById(R.id.blood_tip_text);
		scaleDiskView = (ScaleDiskView) this.findViewById(R.id.blood_scaleDiskView);

		belowTxtView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (state == STATE_COMPLETE && dataSaveListen != null) {
					dataSaveListen.dataSaveClicked();
					belowTxtView.setText(getResources().getString(R.string.blood_detect_data_saved));
				}
			}
		});
	}

	public void setAboveTipText(String aboveTipText) {
		this.aboveTipText = aboveTipText;
		initView();
		aboveTxtView.setText(this.aboveTipText);
	}

	public void setBelowTipText(String belowTipText) {
		this.belowTipText = belowTipText;
		initView();
		belowTxtView.setText(belowTipText);
	}

	public TextView getAboveTxtView() {
		return aboveTxtView;
	}

	public TextView getBelowTxtView() {
		return belowTxtView;
	}

	/**
	 * 设置当前状态
	 * @param state
	 */
	public void setState(int state) {
		this.state = state;
		switch (state) {
			case STATE_PREPARING:
				setAboveTipText(getResources().getString(R.string.blood_detect_before_above_tip));
				setBelowTipText(getResources().getString(R.string.blood_detect_before_below_tip));
				setSaveButtonState(false);
				aboveTxtView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, smallTextSize);
				break;
			case STATE_DETECTING:
				setAboveTipText(progress + "");
				setBelowTipText(getResources().getString(R.string.blood_detecting_tip));
				setSaveButtonState(false);
				aboveTxtView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, larginTextSize);
				break;
			case STATE_COMPLETE:
				// TODO 根据测量结果值 ，设置结果
				setAboveTipText(getResources().getString(R.string.blood_detect_complete_result_mild));
				setBelowTipText(getResources().getString(R.string.blood_detect_save_data));
				setSaveButtonState(true);
				aboveTxtView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, smallTextSize);
				break;
		}
	}

	public void setSaveButtonState(boolean enable) {
		initView();
		belowTxtView.setEnabled(enable);
	}

	/**
	 * 设置进度百分比，如20%，请传入20
	 * @param progrees
	 */
	public void setProgrees(int progrees) {
		this.progress = progrees;
		setAboveTipText(progress + "");
		if (this.scaleDiskView != null) {
			scaleDiskView.setProgrees(progrees);
		}
	}

	/**
	 * 设置数据保存监听器
	 * @param dataSaveListen
	 */
	public void setDataSaveListen(DataSaveListen dataSaveListen) {
		this.dataSaveListen = dataSaveListen;
	}

	/**
	 * 保存数据监听器
	 */
	public interface DataSaveListen {
		public void dataSaveClicked();
	}
}
