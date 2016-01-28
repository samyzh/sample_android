package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps;

import java.text.DecimalFormat;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAda;
import com.zsy.frame.sample.R;

public class EarningRecordForFiveAdapter extends BaseAda<EarnRecord> {

	public EarningRecordForFiveAdapter(Context context) {
		super(context);
		this.mContext = context;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public int getItemViewType(int position) {
		EarnRecord mEarnRecord = (EarnRecord) getItem(position);
		if (TextUtils.isEmpty(mEarnRecord.getOrderNo())) {// 显示无收银记录
			return 0;
		}
		else {
			return 1;
		}
	}

	@Override
	public boolean isEnabled(int position) {
		if (getItemViewType(position) == 0) {
			return false;
		}
		return super.isEnabled(position);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (getItemViewType(position) == 0) {
			if (null == convertView)
				convertView = mInflater.inflate(R.layout.item_a20thirdparty_qrcode_barcode_generate_wealth_empty, parent, false);
		}
		else {
			ViewHolder viewHolder;
			if (null == convertView) {
				viewHolder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.item_a20thirdparty_qrcode_barcode_generate_earn_record_forfive, parent, false);
				viewHolder.nickName_tv = (TextView) convertView.findViewById(R.id.nickName_tv);
				viewHolder.consumeDate_tv = (TextView) convertView.findViewById(R.id.consumeDate_tv);
				viewHolder.sumOfConsumption_tv = (TextView) convertView.findViewById(R.id.sumOfConsumption_tv);
				convertView.setTag(viewHolder);
			}
			else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			setEarningRecordData(position, viewHolder);
		}
		
		final EarnRecord mEarnRecord = getItem(position);
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		return convertView;
	}

	private void setEarningRecordData(int position, ViewHolder viewHolder) {
		EarnRecord mEarnRecord = getItem(position);
		if (null != mEarnRecord) {
			viewHolder.nickName_tv.setText(mEarnRecord.getUserName());
			viewHolder.consumeDate_tv.setText(DateUtil.getTradeTime(mEarnRecord.getConsumeDate()));
			DecimalFormat df = new DecimalFormat("###0.00 ");
			viewHolder.sumOfConsumption_tv.setText("+"+df.format(Float.parseFloat(mEarnRecord.getSumOfConsumption())) + "元");
		}
	}

	private static class ViewHolder {
		private TextView nickName_tv;// 昵称
		private TextView consumeDate_tv;// 时间
		private TextView sumOfConsumption_tv;// 消费金额
	}

}
