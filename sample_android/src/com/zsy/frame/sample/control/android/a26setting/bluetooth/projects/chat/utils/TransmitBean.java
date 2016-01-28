package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.chat.utils;

import java.io.Serializable;

/**
 * 用于传输的数据类
 */
public class TransmitBean implements Serializable {
	/** 
	 * @description：
	 * @author samy
	 * @date 2015-2-8 下午7:25:27
	 */
	private static final long serialVersionUID = -2819928641932356185L;
	private String msg = "";

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}
}
