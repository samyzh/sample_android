package com.zsy.frame.sample.control.android.a20thirdparty.qrcode.helps;

import java.util.ArrayList;
import java.util.List;

public class Group<T extends AutoType> extends ArrayList<T> implements AutoType {

	private static final long serialVersionUID = 1L;

	public static <E extends AutoType> Group<E> convertToGroup(List<E> list) {
		Group<E> group = new Group<E>();
		if (list != null && list.size() > 0) {
			for (E e : list) {
				group.add(e);
			}
		}
		return group;
	}
}
