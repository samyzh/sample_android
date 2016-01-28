package com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.contacts;

import android.app.Activity;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

import com.zsy.frame.sample.R;

/**
 * @description：主界面用于Contentprovider的特性，获取系统联系人数据；
 * @author samy
 * @date 2014年7月13日 下午7:57:53
 */
public class LoaderSysContactAct extends Activity implements LoaderCallbacks<Cursor> {
	private TextView textView;
	private LoaderManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_contentproviders_contacts_loadersyscontact);
		textView = (TextView) this.findViewById(R.id.msg);
		manager = getLoaderManager();
		manager.initLoader(1001, null, this);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		Uri uri = ContactsContract.Contacts.CONTENT_URI;
		CursorLoader loader = new CursorLoader(this, uri, null, null, null, null);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		StringBuffer buffer = new StringBuffer();
		while (data.moveToNext()) {
			int indexName = data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			String display_name = data.getString(indexName);
			buffer.append("name: " + display_name + " Tel:");
			// 表示获得通讯录每一条记录的id
			int _id = data.getInt(data.getColumnIndex(ContactsContract.Contacts._ID));
			// 通过id,可以拿到Cursor；
			Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + _id, null, null);
			while (cursor.moveToNext()) {
				String phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				buffer.append(phone + " \n ");
			}
		}
		textView.setText(buffer.toString());
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {

	}

}
