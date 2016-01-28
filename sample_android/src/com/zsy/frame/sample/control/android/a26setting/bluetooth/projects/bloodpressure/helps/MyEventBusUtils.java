package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.helps;

import android.support.v4.app.Fragment;

public class MyEventBusUtils {

	/**
	 * show or hidden home left event
	 * */
	public static class MyBackEvent {

	}

	/**
	 * home left click event
	 * **/
	public static class HomeChooseEvent {
		public Class<? extends Fragment> chooseClass;

		public HomeChooseEvent(Class<? extends Fragment> chooseClass) {
			this.chooseClass = chooseClass;
		}
	}

	public static class HomeLeftItemRefreshEvent {
	}
}
