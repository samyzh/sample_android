package com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event;

public class SecondEvent {

	private String mMsg;

	public SecondEvent(String msg) {
		mMsg = "MainEvent:" + msg;
	}

	public String getMsg() {
		return mMsg;
	}
}
