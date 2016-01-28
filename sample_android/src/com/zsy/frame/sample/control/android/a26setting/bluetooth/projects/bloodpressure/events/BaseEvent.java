package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events;


/**
 * Event事件基类
 */
public class BaseEvent {
	/**服务端执行操作成功*/
	public static final int REQUESTCODE_SUCCESS = 1;
	/**异常，解析错误等*/
	public static final int REQUESTCODE_EXCEPTION = -1;
	/**网络超时*/
	public static final int REQUESTCODE_TIMEOUT = -2;
	/**网络请求是否成功*/
	public boolean httpRequestSuccess;
	/**
	 * 返回码 1成功，-1解析失败 -2 网络超时
	 */
	public int resultCode;// 返回码
	public String resultMessage;// 返回主体信息
	public Throwable error;// 异常信息

	@Override
	public String toString() {
		return this.getClass() + " [requestSuccess=" + httpRequestSuccess + ", resultCode=" + resultCode + ", resultMessage=" + resultMessage + ", error=" + error + "]";
	}

	/**登陆event*/
	public static class LoginEvent extends BaseEvent {

	}

	/**注册Event*/
	public static class RegisterEvent extends BaseEvent {

	}

	/**设置用户信息*/
	public static class SetUserInfoEvent extends BaseEvent {

	}

	/**上传血压数据*/
	public static class UploadBloodPresureEvent extends BaseEvent {

	}

	/**获取健康资讯*/
	public static class GetHealthInformationEvent extends BaseEvent {

	}

	/**获取印花信息*/
	public static class GetPrintingInformationEvent extends BaseEvent {

	}

	/**获取身体数据*/
	public static class GetPhysicalDataEvent extends BaseEvent {

	}

	/**获取身体数据*/
	public static class GetBloodPressureHistoryEvent extends BaseEvent {

	}

	/**手机注册获取验证码*/
	public static class GetPhoneVerificationEvent extends BaseEvent {

	}

}
