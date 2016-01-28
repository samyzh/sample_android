package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events;

public class BlueToothEvent {
	/**同步数据*/
	public int currentBloodPressure;
	/**收缩压*/
	public int systolic;
	/**舒张压*/
	public int diastolic;
	/**脉搏*/
	public int pulse;

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[currentBloodPressure=" + currentBloodPressure + ", systolic=" + systolic + ", diastolic=" + diastolic + ", pulse=" + pulse + "]";
	}

	/**设备连接*/
	public static class GattConnectedEvent extends BlueToothEvent {

	}

	/**断开连接*/
	public static class GattDisConnectedEvent extends BlueToothEvent {

	}

	/**服务发现*/
	public static class GattServicesDiscoveredEvent extends BlueToothEvent {

	}

	/**血压同步数据*/
	public static class DataSysEvent extends BlueToothEvent {

	}

	/**测量结果*/
	public static class ResultEvent extends BlueToothEvent {

	}

	protected static class DataWritenEvent extends BlueToothEvent {
	}

	/**未搜索到设备*/
	public static class NotFindDeviceEvent extends BlueToothEvent {
	}

}
