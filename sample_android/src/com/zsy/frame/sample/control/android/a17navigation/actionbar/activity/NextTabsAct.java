package com.zsy.frame.sample.control.android.a17navigation.actionbar.activity;

import java.lang.reflect.Method;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnActionExpandListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment.AlbumFragment;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.fragment.ArticleListFragment;
import com.zsy.frame.sample.control.android.a17navigation.actionbar.widget.MyTabListener;

/**
 * @description：Next卡片切换页
 * @author samy
 * @date 2014年10月26日 下午3:24:13
 */
public class NextTabsAct extends Activity {
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle("Next卡片切换页");
		setContentView(R.layout.activity_a17navigation_next_tabs);
		actionBar = getActionBar();
		// 但如果系统大于或者等于4.0的话，那么点击应用图标是无效的。必须加上setHomeButtonEnabled=true，4.0一下 默认为true。
		actionBar.setDisplayHomeAsUpEnabled(true);
		initData();

		/**
		 * 可见在这里SpinnerAdapter下拉会跟Tab功能切换冲突；
		 * actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		 * // 导航模式必须设为NAVIGATION_MODE_LIST
		 */
		// SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_spinner_dropdown_item);
		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);// 导航模式必须设为NAVIGATION_MODE_LIST
		// actionBar.setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);

		// setActionBarLayout(R.layout.view_a17navigation_actionbar_port_layout);
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
		// 设置ActionBar标题不显示
		// actionBar.setDisplayShowTitleEnabled(false);
		// 设置ActionBar的背景
		// actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg_selector));
		// 返回的监听必须设置；
		actionBar.setHomeButtonEnabled(true);
		// 设置ActionBar左边默认的图标是否可用
		actionBar.setDisplayUseLogoEnabled(true);
		// 设置导航模式为Tab选项标签导航模式
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// actionBar.setListNavigationCallbacks(adapter, callback);
		// actionBar.setCustomView(view, layoutParams);
		// actionBar.setIcon(icon);
		// 设置ActinBar添加Tab选项标签
		actionBar.addTab(actionBar.newTab().setText("ArticleList").setTabListener(new MyTabListener<ArticleListFragment>(this, ArticleListFragment.class)));
		actionBar.addTab(actionBar.newTab().setText("Album").setTabListener(new MyTabListener<AlbumFragment>(this, AlbumFragment.class)));
	}

	/**
	 * @description：设置ActionBar的布局
	 * @author samy
	 * @date 2014年7月29日 下午9:47:34
	 */
	public void setActionBarLayout(int layoutId) {
		ActionBar actionBar = getActionBar();
		if (null != actionBar) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);

			LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
			actionBar.setCustomView(v, layout);
		}
	}

	/**
	 * 在这里配合Fragment，实现不同下拉的页面导航
	 */
	OnNavigationListener mOnNavigationListener = new OnNavigationListener() {

		@Override
		public boolean onNavigationItemSelected(int position, long itemId) {
			// Fragment newFragment = null;
			// switch (position) {
			// case 0:
			// newFragment = new Fragment1();
			// break;
			// case 1:
			// newFragment = new Fragment2();
			// break;
			// case 2:
			// newFragment = new Fragment3();
			// break;
			// default:
			// break;
			// }
			// getFragmentManager().beginTransaction().replace(R.id.container, newFragment, strings[position]).commit();
			return true;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_a17navigation_actionbar_next, menu);
		/**
		 * 系统自带ActionView----->SearchView如果显示在上面默认的有search显示样式；
		 */
		MenuItem searchItem = menu.findItem(R.id.menu_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		// 配置SearchView的属性
		// 。。。。。。。。。。。。。
		// 是否点击扩展监听；
		searchItem.setOnActionExpandListener(new OnActionExpandListener() {
			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				Toast.makeText(NextTabsAct.this, "search on expand", Toast.LENGTH_SHORT).show();
				return true;
			}

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				Toast.makeText(NextTabsAct.this, "search on collapse", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		/**
		 * 系统自带ActionProvider----->ShareActionProvider如果显示在上面默认的有share显示样式；
		 */
		MenuItem shareItem = menu.findItem(R.id.action_share);
		ShareActionProvider provider = (ShareActionProvider) shareItem.getActionProvider();
		provider.setShareIntent(getDefaultIntent());
		return super.onCreateOptionsMenu(menu);
	}

	private Intent getDefaultIntent() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}

	/**
	 * overflow中的Action按钮显示图标(系统默认的显示)
	 */
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				}
				catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	/**
	 * ActionBar导航和Back键在设计上的区别
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// finish();
				Intent upIntent = NavUtils.getParentActivityIntent(this);
				if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
					TaskStackBuilder.create(this).addNextIntentWithParentStack(upIntent).startActivities();
				}
				else {
					upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					NavUtils.navigateUpTo(this, upIntent);
				}
				// Intent intent = new Intent(this, HomeActivity.class);
				// intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				// startActivity(intent);
				return true;
			case R.id.action_share:
				Toast.makeText(this, "action_share", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.action_samy:
				Toast.makeText(this, "action_samy", Toast.LENGTH_SHORT).show();
			case R.id.menu_search:
				Toast.makeText(this, "menu_search", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_refresh:
				Toast.makeText(this, "menu_refresh", Toast.LENGTH_SHORT).show();
				return true;
			case R.id.menu_collected:
				Toast.makeText(this, "menu_collected", Toast.LENGTH_SHORT).show();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.menuBtnId:
				showToast(this, "menuBtn");
				break;
			case R.id.noteBtnId:
				showToast(this, "noteBtn");
				break;
			case R.id.downloadBtnId:
				showToast(this, "downloadBtn");
				break;
			case R.id.editBtnId:
				showToast(this, "editBtn");
				break;
		}
	}

	/**
	 * @description： 显示提示信息
	 * @author samy
	 * @date 2014年7月29日 下午9:47:20
	 */
	private void showToast(Context context, String msg) {
		if (null == context || TextUtils.isEmpty(msg)) { return; }
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

}
