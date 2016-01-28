package com.zsy.frame.sample.control.android.a01ui.a08listview.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BasePinnedAda;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.support.bean.ProductCommentArray;
import com.zsy.frame.sample.support.bean.ShopEvaluateBean2.ShopEvaluate;
import com.zsy.frame.sample.utils.DateTimeTool;

public class ShopEvaluteAdapter2 extends BasePinnedAda<ShopEvaluate> {
	public ShopEvaluteAdapter2(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_a01ui_a08listview_shop_evaluate, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		setItemData(holder, position);

		return convertView;
	}

	@Override
	public int getCount() {
		if (group == null || group.isEmpty()) return 0;
		int count = 0;
		for (ShopEvaluate bean : group) {
			count += bean.comments.size();
		}
		return count;
	}

	/** 更新悬浮头 */
	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
		((TextView) header.findViewById(R.id.evaluteHeaderTv)).setText(getRealPeriodStr(position));
	}

	/** 是否将悬浮头往上推 */
	@Override
	protected boolean isPushUp(int position) {
		return isLast(position);
	}

	public boolean isLast(int position) {
		int size = 0;
		for (ShopEvaluate bean : group) {
			size += bean.comments.size();
			if (size < position + 1) continue;
			int realPosition = position - (size - bean.comments.size());// 拿到在当前数组中的位置
			return realPosition == bean.comments.size() - 1;// 为0则为头
		}
		return false;
	}

	/** 获取真正的bean */
	public ProductCommentArray getRealItem(int position) {
		int size = 0;
		int realPosition = 0;
		ProductCommentArray item = null;
		for (ShopEvaluate bean : group) {
			size += bean.comments.size();
			if (size < position + 1) {
				continue;
			}
			realPosition = position - (size - bean.comments.size());// 真实位置
			item = bean.comments.get(realPosition);
			break;
		}
		return item;
	}

	/** 获取真正的期数 */
	public String getRealPeriodStr(int position) {
		int size = 0;
		String period = "0";
		String count = "0";
		for (ShopEvaluate bean : group) {
			size += bean.comments.size();
			if (size < position + 1) continue;
			period = bean.period;
			count = bean.periodCount;
			break;
		}
		return "第" + period + "评价(" + count + "条)";
	}

	/** 判断当前位置是否为头 */
	public boolean isHeaderPositon(int position) {
		int size = 0;
		for (ShopEvaluate bean : group) {
			size += bean.comments.size();
			if (size < position + 1) continue;
			int realPosition = position - (size - bean.comments.size());// 拿到在当前数组中的位置
			return realPosition == 0;// 为0则为头
		}
		return false;
	}

	private void setItemData(ViewHolder holder, int position) {
		ProductCommentArray bean = getRealItem(position);
		holder.comment_level_rb.setRating(bean.commentLevel);
		holder.comment_dt_tv.setText(DateTimeTool.ymdDate(bean.commentDt));

		String userNameOrPhone = "";
		if (TextUtils.isEmpty(bean.commentUser)) {
			userNameOrPhone = replaceUserPhone(bean.phone);
		}
		else {
			if (1 == bean.isAnonymous) {
				userNameOrPhone = replaceUserAccountName(bean.commentUser);
			}
			else {
				userNameOrPhone = bean.commentUser;
			}
		}
		holder.comment_user_tv.setText(userNameOrPhone);

		holder.comment_content_tv.setText(bean.commentContent);

		// 根据位置隐藏显示头
		if (isHeaderPositon(position)) {
			holder.evaluteHeaderTv.setText(getRealPeriodStr(position));
			holder.evaluteHeaderTv.setVisibility(View.VISIBLE);
		}
		else holder.evaluteHeaderTv.setVisibility(View.GONE);
	}

	/**
	 * @description：截取手机号
	 * @author samy
	 * @date 2014年7月17日 下午8:13:14
	 */
	private String replaceUserPhone(String temp) {
		if (!TextUtils.isEmpty(temp)) {
			temp = temp.substring(0, 3) + "****" + temp.substring(temp.length() - 4, temp.length());
			return temp;
		}
		else {
			return "";
		}
	}

	/**
	 * 截取名字
	 * 
	 * @param temp
	 * @return
	 */
	private String replaceUserAccountName(String temp) {
		if (temp.length() >= 1) {
			temp = temp.substring(0, 1) + "******";
			return temp;
		}
		else {
			return "";
		}
	}

	private static class ViewHolder {
		RatingBar comment_level_rb;
		TextView comment_dt_tv, comment_user_tv, comment_content_tv, evaluteHeaderTv;

		ViewHolder(View view) {
			comment_level_rb = (RatingBar) view.findViewById(R.id.comment_level_rb);
			comment_dt_tv = (TextView) view.findViewById(R.id.comment_dt_tv);
			comment_user_tv = (TextView) view.findViewById(R.id.comment_user_tv);
			comment_content_tv = (TextView) view.findViewById(R.id.comment_content_tv);
			evaluteHeaderTv = (TextView) view.findViewById(R.id.evaluteHeaderTv);// 评价期数的头
		}
	}

}
