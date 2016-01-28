package com.zsy.frame.lib.extend.ui.helps;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.widget.Filter;

import com.zsy.frame.lib.extend.ui.base.BaseAda;

/**
 * @description：自定义的（筛选、搜索）过滤器
 * @author samy
 * @date 2015-3-24 下午4:39:44
 */
public class BaseAdaFilter<T> extends Filter {
	private BaseAda<T> baseAda;
	protected List<T> group = null;
	protected List<T> groupUp = null;

	public BaseAdaFilter(BaseAda<T> baseAda) {
		super();
		this.baseAda = baseAda;
	}

	@Override
	protected FilterResults performFiltering(CharSequence prefix) {
		FilterResults results = new FilterResults();
		List<T> groups = null;
		if (TextUtils.isEmpty(prefix)) {
			groups = groupUp;
			results.values = groups;
			results.count = groups.size();
		}
		else {
			final List<T> oldGroups = groupUp;
			final int count = oldGroups.size();
			final List<T> newGroups = new ArrayList<T>();
			for (int i = 0; i < count; i++) {
				if (oldGroups.get(i).toString().toLowerCase().contains(prefix.toString().toLowerCase())) {
					newGroups.add(oldGroups.get(i));
				}
			}
			results.values = newGroups;
			results.count = newGroups.size();
		}
		return results;
	}

	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		// baseAda.setGroup( (ArrayList<T>) results.values);
		baseAda.group = (ArrayList<T>) results.values;
		if (results.count > 0) {
			baseAda.notifyDataSetChanged();
		}
		else {
			baseAda.notifyDataSetInvalidated();
		}
	}
}