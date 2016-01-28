package com.zsy.frame.sample.control.android.a06fourcomponents.contentproviders.base.provider;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zsy.frame.sample.R;

/**
 * @description：每个Item编辑界面；
 * @author samy
 * @date 2014年7月13日 下午8:50:11
 */
public class UpdateCPAct extends Activity {
	private EditText edt_item_name;
	private EditText edt_item_age;
	private EditText edt_item_id;
	private Button btndel, btnupdate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a06fourcomponents_contentproviders_base_update_cp);
		edt_item_id = (EditText) this.findViewById(R.id.edt_item_id);
		// id 不可以编辑；
		edt_item_id.setEnabled(false);
		edt_item_name = (EditText) this.findViewById(R.id.edt_item_name);
		edt_item_age = (EditText) this.findViewById(R.id.edt_item_age);
		btndel = (Button) this.findViewById(R.id.btndel);
		btnupdate = (Button) this.findViewById(R.id.btnupdate);

		Bundle bundle = getIntent().getBundleExtra("item");
		int id = bundle.getInt("id");
		System.out.println("id----" + id);
		String name = bundle.getString("name");
		int age = bundle.getInt("age");

		edt_item_id.setText(String.valueOf(id));
		edt_item_name.setText(name);
		edt_item_age.setText(String.valueOf(age));

		btndel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentResolver contentResolver = UpdateCPAct.this.getContentResolver();
				// String url = "content://com.zsy.tc.PersonContentProvider/person/" + edt_item_id.getText();
				String url = BaseCPProvider.AUTH_URI_PERSON.toString() + "/" + edt_item_id.getText();
				Uri uri = Uri.parse(url);
				int result = contentResolver.delete(uri, null, null);

				System.out.println("delete result:" + result);
				if (result >= 1) {
					Toast.makeText(UpdateCPAct.this, "删除成功", Toast.LENGTH_LONG).show();

					UpdateCPAct.this.setResult(2);
					UpdateCPAct.this.finish();
				}

			}
		});
		btnupdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentResolver contentResolver = UpdateCPAct.this.getContentResolver();
				// String url = "content://com.zsy.tc.PersonContentProvider/person/" + edt_item_id.getText();
				String url = BaseCPProvider.AUTH_URI_PERSON.toString() + "/" + edt_item_id.getText();
				Uri uri = Uri.parse(url);
				ContentValues values = new ContentValues();
				values.put("name", edt_item_name.getText().toString());
				values.put("age", Integer.parseInt(edt_item_age.getText().toString()));
				int result = contentResolver.update(uri, values, null, null);

				System.out.println("update result:" + result);
				if (result >= 1) {
					Toast.makeText(UpdateCPAct.this, "更新成功", Toast.LENGTH_LONG).show();

					UpdateCPAct.this.setResult(2);
					UpdateCPAct.this.finish();
				}
			}
		});
	}
}
