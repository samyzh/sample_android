package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps;

import java.io.Serializable;

/**
 * @description：优惠买单推送记录
 */
public class PayPushRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String content;
	private String time;
	private int num;
	private String account;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "PayPushRecord [content=" + content + ", time=" + time + ", num=" + num + ", account=" + account + "]";
	}

}
