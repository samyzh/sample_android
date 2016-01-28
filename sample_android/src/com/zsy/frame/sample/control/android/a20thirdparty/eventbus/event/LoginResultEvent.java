package com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event;

/**
 * @description：目前是测试列子
 * @author samy
 * @date 2014年11月25日 下午4:44:17
 */
public class LoginResultEvent {
	private boolean isSuceess;
	private String reason;

	public LoginResultEvent(boolean is) {
		setSuceess(is);
		this.setReason("suceess");
	}

	public LoginResultEvent(String reason) {
		setSuceess(false);
		this.setReason(reason);
	}

	private static LoginResultEvent mLoginResultEvent = null;

	public static synchronized LoginResultEvent getLoginResultEvent(boolean is) {
		if (null == mLoginResultEvent) {
			mLoginResultEvent = new LoginResultEvent(is);
		}
		else {
			mLoginResultEvent.setSuceess(is);
		}
		return mLoginResultEvent;
	}

	public static synchronized LoginResultEvent getLoginResultEvent(String reason) {
		if (null == mLoginResultEvent) {
			mLoginResultEvent = new LoginResultEvent(reason);
		}
		else {
			mLoginResultEvent.setSuceess(false);
			mLoginResultEvent.setReason(reason);
		}
		return mLoginResultEvent;
	}

	public boolean isSuceess() {
		return isSuceess;
	}

	public void setSuceess(boolean isSuceess) {
		this.isSuceess = isSuceess;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
