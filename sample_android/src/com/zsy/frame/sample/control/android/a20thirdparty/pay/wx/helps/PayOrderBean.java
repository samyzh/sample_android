package com.zsy.frame.sample.control.android.a20thirdparty.pay.wx.helps;

import java.io.Serializable;

/**
 * @description：订单类
 * @author samy
 * @date 2015年6月30日 下午12:13:05
 */
public class PayOrderBean implements Serializable{

	private static final long serialVersionUID = -8361888105572851507L;

	private String orderNumber;
	
	private WeixinPay weixinPay;

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}


	public WeixinPay getWeixinPay() {
		return weixinPay;
	}

	public void setWeixinPay(WeixinPay weixinPay) {
		this.weixinPay = weixinPay;
	}

}
