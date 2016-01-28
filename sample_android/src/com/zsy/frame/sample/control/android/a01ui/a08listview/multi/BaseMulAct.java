package com.zsy.frame.sample.control.android.a01ui.a08listview.multi;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.zsy.frame.sample.R;
/**
 * @description：主界面
 * @author samy
 * @date 2014年8月25日 上午12:29:29
 */
public class BaseMulAct extends Activity {
	private MyListView listView;
	private MyListView subListView;
	private MyAdapter myAdapter;
	private SubAdapter subAdapter;
	
	String cities[][] = new String[][] { new String[] { "全部美食", "本帮江浙菜", "川菜", "粤菜", "湘菜", "东北菜", "台湾菜", "新疆/清真", "素菜", "火锅", "自助餐", "小吃快餐", "日本", "韩国料理", "东南亚菜", "西餐", "面包甜点", "其他" }, new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" }, new String[] { "全部购物", "综合商场", "服饰鞋包", "运动户外", "珠宝饰品", "化妆品", "数码家电", "亲子购物", "家居建材", "书店", "书店", "眼镜店", "特色集市", "更多购物场所", "食品茶酒", "超市/便利店", "药店" }, new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" }, new String[] { "全", "咖啡厅", "酒吧", "茶馆", "KTV", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全部", "咖啡厅", "酒吧", "茶馆", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" }, new String[] { "全部休", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" }, new String[] { "全部休闲", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" }, new String[] { "全部休闲娱", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏" }, new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全部休闲aaa", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆", "桌面游戏" }, };
	String foods[] = new String[] { "全部频道", "美食", "休闲娱乐", "购物", "酒店", "丽人", "运动健身", "结婚", "亲子", "爱车", "生活服务" };
	int images[] = new int[] { R.drawable.a01ui_a08listview_multi_ic_category_0, R.drawable.a01ui_a08listview_multi_ic_category_10, R.drawable.a01ui_a08listview_multi_ic_category_30, R.drawable.a01ui_a08listview_multi_ic_category_20, R.drawable.a01ui_a08listview_multi_ic_category_60, R.drawable.a01ui_a08listview_multi_ic_category_50, R.drawable.a01ui_a08listview_multi_ic_category_45, R.drawable.a01ui_a08listview_multi_ic_category_50, R.drawable.a01ui_a08listview_multi_ic_category_70, R.drawable.a01ui_a08listview_multi_ic_category_65, R.drawable.a01ui_a08listview_multi_ic_category_80 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a08listview_multi_baselistview);
		init();
		myAdapter = new MyAdapter(getApplicationContext(), foods, images);
		listView.setAdapter(myAdapter);
		selectDefult();
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				final int location = position;
				myAdapter.setSelectedPosition(position);
				myAdapter.notifyDataSetInvalidated();
				subAdapter = new SubAdapter(getApplicationContext(), cities, position);
				subListView.setAdapter(subAdapter);
				subListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
						Toast.makeText(getApplicationContext(), cities[location][position], Toast.LENGTH_SHORT).show();
					}
				});
			}
		});

	}

	private void init() {
		listView = (MyListView) findViewById(R.id.listView);
		subListView = (MyListView) findViewById(R.id.subListView);
	}

	private void selectDefult() {
		final int location = 0;
		myAdapter.setSelectedPosition(0);
		myAdapter.notifyDataSetInvalidated();
		subAdapter = new SubAdapter(getApplicationContext(), cities, 0);
		subListView.setAdapter(subAdapter);
		subListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Toast.makeText(getApplicationContext(), cities[location][position], Toast.LENGTH_SHORT).show();
			}
		});
	}

}
