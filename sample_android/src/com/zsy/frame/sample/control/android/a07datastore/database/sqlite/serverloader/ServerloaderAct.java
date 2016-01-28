package com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.dao.PersonServiceImp;
import com.zsy.frame.sample.control.android.a07datastore.database.sqlite.serverloader.service.PersonService;

/**
 * @description：
 * String[] from = { "name", "address" }; int[] to = { R.id.textView1, R.id.textView2 }; adapter = new SimpleCursorAdapter(MainActivity.this, R.layout.activity_main, data, from, to); listView.setAdapter(adapter);
 *  adapter.notifyDataSetChanged();
 *  As an alternative, use LoaderManager with a CursorLoader.
 *  这里优化了很多，通过LoadManager异步加载数据；
 *  
 *  可以借鉴的地方
 *  SimpleCursorAdapter和LoaderCallbacks 用法；
 * PersonServiceImp Web端数据库的写法应用
 * @author samy
 * @date 2014年7月13日 下午5:49:53
 */
public class ServerloaderAct extends Activity implements LoaderCallbacks<Cursor> {
	private ListView listView;
	private LoaderManager manager;
	/** SimpleCursorAdapter用法 */
	private SimpleCursorAdapter adapter;
	private static PersonService service;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a07datastore_database_sqlite_serverloader_serverloader);
		listView = (ListView) this.findViewById(R.id.listView1);
		manager = getLoaderManager();
		manager.initLoader(1001, null, this);
		// 主线程调用loader;
		manager.getLoader(1001).onContentChanged();

		service = new PersonServiceImp(this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// 异步获取Loader<Cursor>
		return new MyAsyLoader(ServerloaderAct.this);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// SimpleCursorAdapter用法；This constructor is deprecated
		// SimpleAdapter不同于SimpleCursorAdapter【Map数据】
		// simpleAdapter = new SimpleAdapter(MainActivity.this, data, R.layout.list_item, new String[] { "id", "name", "age" }, new int[] { R.id.tvId, R.id.tvname, R.id.tvage });
		adapter = new SimpleCursorAdapter(this, R.layout.activity_a07datastore_database_sqlite_serverloader_serverloader, data, new String[] { "name", "address" }, new int[] { R.id.textView1, R.id.textView2 });
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

	/**
	 * @description：异步加载数据
	 * @author samy
	 * @date 2014年7月13日 下午5:51:02
	 */
	public static class MyAsyLoader extends AsyncTaskLoader<Cursor> {

		public MyAsyLoader(Context context) {
			super(context);
		}

		@Override
		protected void onStartLoading() {
			super.onStartLoading();
			if (takeContentChanged()) {
				forceLoad();
			}
		}

		/**
		 * 完成查询数据
		 */
		@Override
		public Cursor loadInBackground() {
			Cursor cursor = service.listPersonCursor();
			return cursor;
		}
	}
}
