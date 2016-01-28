package com.zsy.frame.sample.support.bean;

/**
 * @description：主装Arraylist用；
 * @author samy
 * @date 2014年8月16日 上午11:11:08
 */
public class ActivityInfo {
	public String title;
	public Class<?> name;

	public ActivityInfo(String title, Class<?> name) {
		this.title = title;
		this.name = name;
	}

	public String toString() {
		return title;
	}
}
