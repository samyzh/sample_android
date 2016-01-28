package com.zsy.frame.lib.extend.ui.base;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.zsy.frame.lib.extend.ui.helps.BaseAdaFilter;

/**
 * @description：基类BaseAda;
 * 如搜索过滤功能直接到继承这个类后实现Filterable，
 * 再次实现Filterable里面的方法；getFilter();
 * @author samy
 * @date 2015-3-24 下午4:22:56
 */
// public abstract class BaseAda<T> extends BaseAdapter implements Filterable {
public abstract class BaseAda<T> extends BaseAdapter {
	public List<T> group = new ArrayList<T>();
	protected Context mContext;
	protected LayoutInflater mInflater;
	protected BaseAdaFilter<T> baseAdaFilter;

	public BaseAda(Context context) {
		super();
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}

	// @Override
	// public Filter getFilter() {
	// if (null == baseAdaFilter) {
	// baseAdaFilter = new BaseAdaFilter<T>(this);
	// }
	// return baseAdaFilter;
	// }

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// final ViewHolder viewHolder;
	// if (convertView == null) {
	// convertView = mInflater.inflate(getItemLayout(), parent, false);
	// viewHolder = new ViewHolder(convertView);
	// convertView.setTag(viewHolder);
	// }
	// else {
	// viewHolder = (ViewHolder) convertView.getTag();
	// }
	// refreshItemData(viewHolder, position);
	// return convertView;
	// }

	@Override
	public int getCount() {
		return (group == null) ? 0 : group.size();
	}

	@Override
	public T getItem(int position) {
		/** 防止外部调用时越界 */
		if (group == null || position < 0 || position > group.size()) return null;
		return group.get(position);
	}

	public void removeItem(int position) {
		if (group == null || position < 0 || position > group.size()) return;
		group.remove(position);
		notifyDataSetChanged();
		return;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public boolean isEmpty() {
		return (group == null) ? true : group.isEmpty();
	}

	public void setGroup(List<T> g) {
		if (group != null) group.clear();
		group.addAll(g);
		notifyDataSetChanged();
	}

	public void addItem(T item) {
		group.add(item);
		notifyDataSetChanged();
	}

	public void addItemNoNotify(T item) {
		group.add(item);
	}

	public void addItems(List<T> items) {
		if (items != null) {
			group.addAll(items);
			notifyDataSetChanged();
		}
	}

	public void clearGroup(boolean notify) {
		if (group != null) {
			group.clear();
			if (notify) {
				notifyDataSetChanged();
			}
		}
	}

	/**
	 * 后期看如何优化这里的数据加载；先考虑到这个ViewHolder必须在子类才有布局加载数据。更换一种设计模式加载显示；
	 */
	// abstract void refreshItemData(ViewHolder holder, int position);

	// abstract int getItemLayout();
	//
	// static class ViewHolder {
	// // TextView dish_name_tv;
	// ViewHolder(View convertView) {
	// // dish_name_tv = (TextView) convertView.findViewById(R.id.dish_name_tv);
	// }
	// }
}
