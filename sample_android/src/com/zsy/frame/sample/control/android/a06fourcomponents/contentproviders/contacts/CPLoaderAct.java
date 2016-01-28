package com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.contacts;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zsy.frame.sample.R;

/**
 * @description：主界面
 *                  CursorAdapter(Context context, Cursor c)
 *                  This constructor was deprecated in API level 11. This option is discouraged, as it results in Cursor queries being performed on the application's UI thread and thus can cause poor responsiveness or even Application Not Responding errors. As an alternative, use LoaderManager with a CursorLoader.
 * @author samy
 * @date 2014年7月13日 下午12:16:39
 */
public class CPLoaderAct extends Activity implements LoaderCallbacks<Cursor> {
	private Button button;
	private ListView listView;
	private MyAdapter adapter;

	private LoaderManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_contentproviders_contacts_cploader);
		listView = (ListView) this.findViewById(R.id.listView1);
		button = (Button) this.findViewById(R.id.button1);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// 使用内容解析者访问
				ContentResolver contentResolver = getContentResolver();
				
				/**
				 * 类似于访问其他应用的提供者
				 */
				Uri uri = Uri.parse("content://com.zsy.tc.PersonContentProvider/person");
				ContentValues values = new ContentValues();
				values.put("name", "samy");
				contentResolver.insert(uri, values);
				// 重新出发回调方法；onLoadFinished；
				manager.restartLoader(1001, null, CPLoaderAct.this);
			}
		});
		manager = getLoaderManager();
		manager.initLoader(1001, null, this);
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// 拿到数据提供者的数据；这里没有用异步去查询数据，没有优化处理；
		// 方法一：
		CursorLoader loader = new CursorLoader(this);
		Uri uri = Uri.parse("content://com.zsy.tc.PersonContentProvider/person");
		loader.setUri(uri);
		loader.setProjection(new String[] { "name" });
		// 方法二：
		// Uri uri = ContactsContract.Contacts.CONTENT_URI;
		// CursorLoader loader = new CursorLoader(this, uri, null, null, null, null);

		// 异步获取Loader<Cursor>[优化处理]
		// return new MyAsyLoader(MainActivity.this);

		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		// 负责显示；
		List<String> list = new ArrayList<String>();
		while (data.moveToNext()) {
			String name = data.getString(data.getColumnIndex("name"));
			list.add(name);
		}
		adapter = new MyAdapter(list);
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
			// Cursor cursor = service.listPersonCursor();
			// @Override
			// public Cursor listPersonCursor() {
			// return manager.queryByCursor("person", null, null, null, null, null, null);
			// }

			return null;
		}
	}

	public class MyAdapter extends BaseAdapter {
		private List<String> list;

		public MyAdapter(List<String> list) {
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			TextView textView = null;
			if (convertView == null) {
				textView = new TextView(CPLoaderAct.this);
			}
			else {
				textView = (TextView) convertView;
			}
			textView.setTextSize(20);
			textView.setText(list.get(position));
			return textView;
		}

	}
}
