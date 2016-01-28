package com.zsy.frame.sample.control.android.a20thirdparty.eventbus.event;

import de.greenrobot.event.EventBus;

/**
 * @description：测试登录用到，先没有用到，后期改进
 * @author samy
 * @date 2015-2-25 上午10:53:47
 */
public class EventManager {
	/** 登录结果事件 */
	public void postLoginResultEvent(LoginResultEvent mEvent) {
		EventBus.getDefault().post(mEvent);
	}
	
	private static EventManager mInstance = null;
	public synchronized static EventManager getInstance() {
		if (mInstance == null) {
			mInstance = new EventManager();
		}
		return mInstance;
	}
}
