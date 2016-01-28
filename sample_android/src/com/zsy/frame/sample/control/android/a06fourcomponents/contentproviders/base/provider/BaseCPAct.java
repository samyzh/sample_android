package com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.base.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.base.model.Person;

/**
 * @description：主界面，操作界面；
 * @author samy
 * @date 2014年7月13日 下午8:34:14
 */
public class BaseCPAct extends Activity {
	private Button btnadd, btnqueryall;
	private EditText edtname, edtage;

	private ListView lvall;
	/** SimpleAdapter用法 */
	private SimpleAdapter simpleAdapter;

	private List<Person> persons = new ArrayList<Person>();

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			List<Map<String, Object>> data = (List<Map<String, Object>>) msg.obj;
			System.out.println(data.size());
			// SimpleAdapter不同于SimpleCursorAdapter【Map数据】
			// adapter = new SimpleCursorAdapter(this, R.layout.activity_main, data, new String[] { "name", "address" }, new int[] { R.id.textView1, R.id.textView2 });
			simpleAdapter = new SimpleAdapter(BaseCPAct.this, data, R.layout.item_a06fourcomponents_contentproviders_base_provider_basecp, new String[] { "id", "name", "age" }, new int[] { R.id.tvId, R.id.tvname, R.id.tvage });
			lvall.setAdapter(simpleAdapter);
		}
	};

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_contentproviders_base_provider_basecp);

		btnqueryall = (Button) this.findViewById(R.id.btnqueryall);
		btnadd = (Button) this.findViewById(R.id.btnadd);
		edtname = (EditText) this.findViewById(R.id.edtname);
		edtage = (EditText) this.findViewById(R.id.edtage);
		lvall = (ListView) this.findViewById(R.id.lvall);

		btnadd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentResolver contentResolver = BaseCPAct.this.getContentResolver();
				Uri url = BaseCPProvider.AUTH_URI_PERSON;

				ContentValues values = new ContentValues();
				values.put("name", edtname.getText().toString());
				values.put("age", edtage.getText().toString());
				Uri result = contentResolver.insert(url, values);

				System.out.println(result.toString());

				if (ContentUris.parseId(result) > 0) {
					Toast.makeText(BaseCPAct.this, "添加成功", Toast.LENGTH_LONG).show();
					// 添加成功后再启动线程查询【这个方法待改进】----->添加后查询所有数据；
					MyThread thread = new MyThread(BaseCPAct.this);
					thread.start();
				}
			}
		});
		// 查询所有
		btnqueryall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MyThread thread = new MyThread(BaseCPAct.this);
				thread.start();
			}
		});

		lvall.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Person person = persons.get(position);

				Bundle bundle = new Bundle();
				bundle.putInt("id", person.getId());
				bundle.putString("name", person.getName());
				bundle.putInt("age", person.getAge());
				Intent intent = new Intent(BaseCPAct.this, UpdateCPAct.class);

				intent.putExtra("item", bundle);
				startActivityForResult(intent, 1);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 2) {
			MyThread thread = new MyThread(BaseCPAct.this);
			thread.start();
		}
	}

	class MyThread extends Thread {
		Context context;

		public MyThread(Context context) {
			this.context = context;
			// 一定要清空。否则会 有问题，每执行一次都会把之前的全部的item加进去【数组和Adapter清空处理下】
			persons.clear();
			lvall.setAdapter(null);
		}

		@Override
		public void run() {
			Uri url = BaseCPProvider.AUTH_URI_PERSON;
			Cursor cursor = context.getContentResolver().query(url, new String[] { "_id", "name", "age" }, null, null, "_id");
			while (cursor.moveToNext()) {
				// System.out.println("_id:" + cursor.getInt(cursor.getColumnIndex("_id")));
				// System.out.println("name:" + cursor.getString(cursor.getColumnIndex("name")));
				// System.out.println("age:" + cursor.getInt(cursor.getColumnIndex("age")));

				Person person = new Person();
				person.setId(cursor.getInt(cursor.getColumnIndex("_id")));
				person.setName(cursor.getString(cursor.getColumnIndex("name")));
				person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
				persons.add(person);
			}
			cursor.close();

			// 单个查询；纯内容提供者用法；【查询第三方内容提供者数据集】
			// protected void btn2_click() {
			//
			// stus.clear();
			// // 必须知道协议名字；
			// String uriString = "content://com.example.day76contentproviderservices/stu/3";
			// // 获取数据获取者
			// ContentResolver contentResolver = this.getApplicationContext().getContentResolver();
			// Cursor cursor = contentResolver.query(Uri.parse(uriString), new String[] { "id", "name", "age" }, null, null, null);
			// while (cursor.moveToNext()) {
			// Stu stu = new Stu();
			// stu.setId(cursor.getInt(cursor.getColumnIndex("id")));
			// stu.setName(cursor.getString(cursor.getColumnIndex("name")));
			// stu.setAge(cursor.getInt(cursor.getColumnIndex("age")));
			// stus.add(stu);
			// }
			// cursor.close();
			// adapter.notifyDataSetChanged();
			// }

			// 全部查询；
			// protected void btn1_click() {
			//
			// stus.clear();
			// String uriString = "content://com.example.day76contentproviderservices/stu";
			// // 获取数据获取者
			// ContentResolver contentResolver = this.getApplicationContext().getContentResolver();
			//
			// Cursor cursor = contentResolver.query(Uri.parse(uriString), new String[] { "id", "name", "age" }, null, null, null);
			// while (cursor.moveToNext()) {
			// Stu stu = new Stu();
			// stu.setId(cursor.getInt(cursor.getColumnIndex("id")));
			// stu.setName(cursor.getString(cursor.getColumnIndex("name")));
			// stu.setAge(cursor.getInt(cursor.getColumnIndex("age")));
			// stus.add(stu);
			// }
			// cursor.close();
			// adapter.notifyDataSetChanged();
			// }

			List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = null;
			for (int i = 0; i < persons.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("id", persons.get(i).getId());
				map.put("name", persons.get(i).getName());
				map.put("age", persons.get(i).getAge());
				data.add(map);
			}
			if (data.size() >= persons.size()) {
			}
			Message msg = mHandler.obtainMessage();
			// 发送消息，传送的是一个List对象；
			msg.obj = data;
			mHandler.sendMessage(msg);
		}

	}

}