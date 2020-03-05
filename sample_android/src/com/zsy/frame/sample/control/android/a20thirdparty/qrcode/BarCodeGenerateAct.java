package com.zsy.frame.sample.control.android.a20thirdparty.qrcode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps.EarnRecord;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps.EarningRecordForFiveAdapter;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps.Group;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps.PayPushRecord;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.util.QRUtils;
import com.zsy.frame.sample.control.android.a20thirdparty.qrcode.views.SampleListLinearLayout;

/**
 * @description：我要收银
 * @author samy
 * @date 2015-3-1 下午3:40:24
 */
public class BarCodeGenerateAct extends BaseAct {
	public static final String ACTION_PAY_PUSH_MESSAGE_BROADCAST = "COM.HUIKA.HUIXIN.MERCHANT.APP.ACTION_PAY_PUSH_MESSAGE_BROADCAST";

	private ImageView qr_show;
	private SampleListLinearLayout lv_record;
	private Animation rotateAnimation;
	private EarningRecordForFiveAdapter mRecordAdapter;// 收银列表的adapter
	private Group<EarnRecord> mPriceDatas = new Group<EarnRecord>();
	private ImageButton btn_head_left;
	private ImageButton btnHeadRight;

	// 扫一扫支付
	public static final String SCAN_HOST_PAY = "hx://pay?showAccount=";

	private BroadcastReceiver pushPayMsgBroadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ACTION_PAY_PUSH_MESSAGE_BROADCAST)) {
				// if (intent.getAction().equals(Constant.ACTION_PAY_PUSH_MESSAGE_BROADCAST)) {
				PayPushRecord payPushRecord = (PayPushRecord) intent.getSerializableExtra("pay_push_record");
				showNewCashDialog(payPushRecord);
				startLoadDatas();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		registerReceiver(pushPayMsgBroadcastReceiver, new IntentFilter(ACTION_PAY_PUSH_MESSAGE_BROADCAST));
		// registerReceiver(pushPayMsgBroadcastReceiver, new IntentFilter(Constant.ACTION_PAY_PUSH_MESSAGE_BROADCAST));
		// if (PreferencesHelper.getPushMsgNum(GlobalMethods.getMyUID()) > 0) {
		// showNewCashDialog(PreferencesHelper.getPayPushRecordByPere(GlobalMethods.getMyUID()));
		// }
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		
		btn_head_left = (ImageButton) findViewById(R.id.btn_head_left);
		btnHeadRight = (ImageButton) findViewById(R.id.btn_head_right);
		btn_head_left.setOnClickListener(this);
		btnHeadRight.setOnClickListener(this);
		btnHeadRight.setImageResource(R.drawable.a20thirdparty_qrcode_barcode_generate_btn_icon_refresh_selector);
		btnHeadRight.setVisibility(View.VISIBLE);
		qr_show = (ImageView) findViewById(R.id.qr_show);

		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		int w = outMetrics.widthPixels * 8 / 11;
		ViewGroup.LayoutParams layoutParams = qr_show.getLayoutParams();
		layoutParams.height = layoutParams.width = w;
		qr_show.setLayoutParams(layoutParams);

		try {
			Bitmap bitmap = QRUtils.encodeToQRWidth(SCAN_HOST_PAY + "samyshophaha", w);
			qr_show.setImageBitmap(bitmap);
		}
		catch (Exception e) {
			e.printStackTrace();
			showToastMsg("二维码生成异常！");
		}

		lv_record = (SampleListLinearLayout) findViewById(R.id.lv_record);
		mRecordAdapter = new EarningRecordForFiveAdapter(this);
		mRecordAdapter.setGroup(mPriceDatas);
		lv_record.setAdapter(mRecordAdapter);

		lv_record.requestLayout();

		startLoadDatas();
	}

	@Override
	public void widgetClick(View v) {
		super.widgetClick(v);
		switch (v.getId()) {
			case R.id.btn_head_left:
				finish();
				break;
			case R.id.btn_head_right:// 刷新
				startLoadDatas();
				break;
		}
	}

	private void startLoadDatas() {
		if (rotateAnimation == null) {
			// 加载动画
			rotateAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
		}
		btnHeadRight.startAnimation(rotateAnimation);

		setData();
		// btnHeadRight.clearAnimation();
		// mPriceDatas.clear();
		// if (result.getData() == null || result.getData().isEmpty()) {// 无记录
		// EarnRecord emptyRecord = new EarnRecord();
		// mPriceDatas.add(emptyRecord);
		// }
		// else {
		// Group<EarnRecord> group = result.getData();
		// if (group.size() > 5) {// 限定显示五条数据
		// Group<EarnRecord> fiveGroup = new Group<EarnRecord>();
		// for (int i = 0; i < 5; i++) {
		// fiveGroup.add(group.get(i));
		// }
		// result.setData(fiveGroup);
		// }
		// mPriceDatas.addAll(result.getData());
		// CURRENT_PAGE = 1;
		// }
		// mRecordAdapter.notifyDataSetChanged();
	}

	private void setData() {
		for (int i = 0; i < 10; i++) {
			EarnRecord emptyRecord = new EarnRecord();
			emptyRecord.setOrderNo("12345545");
			emptyRecord.setNickName("samy");
			emptyRecord.setConsumeDate("123456789012");
			emptyRecord.setSumOfConsumption("13000");
			// emptyRecord.getConsumeDate() =
			// consumeDate;// 消费时间
			// consumeType;// 交易类型
			// nickName;// 昵称
			// orderNo;// 订单
			// realName;// 用户真实名字
			// umOfConsumption;// 消费金额
			// userName;// 用户名
			// remark;// 备注信息
			mPriceDatas.add(emptyRecord);
		}
		mRecordAdapter.setGroup(mPriceDatas);

		mRecordAdapter.notifyDataSetChanged();
//		btnHeadRight.clearAnimation();
	}

	// 显示推送的订单
	public void showNewCashDialog(PayPushRecord payPushRecord) {
		// if (TextUtils.isEmpty(payPushRecord.getAccount()) || TextUtils.isEmpty(payPushRecord.getContent())) {
		// return;
		// }
		// View contentView = inflater.inflate(R.layout.cash_record_dialog, null);
		// TextView titleTv = (TextView) contentView.findViewById(R.id.cash_title);
		// TextView customerTv = (TextView) contentView.findViewById(R.id.cash_consumer);
		// TextView timeView = (TextView) contentView.findViewById(R.id.cash_time);
		// titleTv.setText("成功收银:" + payPushRecord.getContent() + "元");
		// customerTv.setText("付款用户 :" + payPushRecord.getAccount());
		// timeView.setText("交易时间:" + DateUtil.getTradeTime(payPushRecord.getTime()));
		// Dialog dialog = MMAlert.createShowCustomeDialog(this, contentView, null, null);
		// dialog.show();
	}

	@Override
	protected void onPause() {
		// PreferencesHelper.clearPushPayMsg(GlobalMethods.getMyUID());
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(pushPayMsgBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a20thirdparty_qrcode_barcode_generate_cash_record);
	}

}
