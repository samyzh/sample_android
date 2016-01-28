package com.zsy.frame.sample.control.android.a01ui.a08listview.multi.entity;

import java.util.ArrayList;

/**
 * @description：菜单实体
 * @author samy
 * @date 2014年9月10日 下午9:42:06
 */
public class MenuItem {
	// 是否有子菜单
	private boolean hasChild;
	// 菜单名字
	private String name;
	// 子菜单集合
	private ArrayList<MenuItem> childMenuItems;

	// 提供两种构造函数
	public MenuItem() {
	}

	public MenuItem(boolean hasChild, String name, ArrayList<MenuItem> childMenuItems) {
		this.hasChild = hasChild;
		this.name = name;
		this.childMenuItems = childMenuItems;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<MenuItem> getChildMenuItems() {
		return childMenuItems;
	}

	public void setChildMenuItems(ArrayList<MenuItem> childMenuItems) {
		this.childMenuItems = childMenuItems;
	}

	@Override
	public String toString() {

		return name;
	}

}
