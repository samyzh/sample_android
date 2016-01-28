package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps;

import java.io.Serializable;

import android.text.Html;
import android.text.Spanned;

/**
 * 
 * @description 详细描述：收银记录
 */
public class EarnRecord implements AutoType, Serializable {
	private static final long serialVersionUID = -2222949858833949185L;
	private String consumeDate;// 消费时间
	private String consumeType;// 交易类型
	private String nickName;// 昵称
	private String orderNo;// 订单
	private String realName;// 用户真实名字
	private String sumOfConsumption;// 消费金额
	private String userName;// 用户名
	private String remark;// 备注信息
	public int section;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getConsumeType() {
		return consumeType;
	}

	public void setConsumeType(String consumeType) {
		this.consumeType = consumeType;
	}

	public String getConsumeDate() {
		return consumeDate;
	}

	public void setConsumeDate(String consumeDate) {
		this.consumeDate = consumeDate;
	}
	
	public String getSumOfConsumption() {
		return MoneyTools.coreMatchCutMoney(sumOfConsumption);
	}
	
	public Spanned getShowSumOfConsumption()
	{
		return Html.fromHtml("<font color=#333333>" + MoneyTools.coreMatchCutMoney(sumOfConsumption) + "</font><font color=#969696>元</font>");
	}

	public void setSumOfConsumption(String sumOfConsumption) {
		this.sumOfConsumption = sumOfConsumption;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Override
	public String toString() {
		return "EarnRecord [consumeType=" + consumeType + ", consumeDate=" + consumeDate + ", sumOfConsumption=" + sumOfConsumption + ", orderNo=" + orderNo + ", userName=" + userName + ", nickName=" + nickName + ", realName=" + realName + ", remark=" + remark + "]";
	}

}
