package com.zsy.frame.sample.control.android.a08net.parse;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.frame.lib.extend.ui.base.BaseAct;
import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a08net.parse.helps.HttpUtils;
import com.zsy.frame.sample.control.android.a08net.parse.helps.JsonTools;
import com.zsy.frame.sample.support.bean.Person;

public class JsonParseAct extends BaseAct {
	private static final String TAG = "JsonParseAct";
	private Button person, persons, liststring, listmap;
	private TextView parse_des;

	public JsonParseAct() {
		super();
		setHiddenActionBar(false);
	}

	@Override
	protected void initWidget(Bundle savedInstanceState) {
		super.initWidget(savedInstanceState);
		person = (Button) this.findViewById(R.id.person);
		persons = (Button) this.findViewById(R.id.persons);
		liststring = (Button) this.findViewById(R.id.liststring);
		listmap = (Button) this.findViewById(R.id.listmap);
		parse_des = (TextView) this.findViewById(R.id.parse_des);
		person.setOnClickListener(this);
		persons.setOnClickListener(this);
		liststring.setOnClickListener(this);
		listmap.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.person:
				String path = "http://127.0.0.1:8080/jsonProject/servlet/JsonAction?action_flag=person";
				String jsonString = HttpUtils.getJsonContent(path);
				Person person = JsonTools.getPerson("person", jsonString);
				Log.i(TAG, person.toString());
				parse_des.setText(person.toString());
				break;
			case R.id.persons:
				String path2 = "http://127.0.0.1:8080/jsonProject/servlet/JsonAction?action_flag=persons";
				String jsonString2 = HttpUtils.getJsonContent(path2);
				List<Person> list2 = JsonTools.getPersons("persons", jsonString2);
				Log.i(TAG, list2.toString());
				parse_des.setText(list2.toString());
				break;
			case R.id.liststring:
				String path3 = "http://127.0.0.1:8080/jsonProject/servlet/JsonAction?action_flag=liststring";
				String jsonString3 = HttpUtils.getJsonContent(path3);
				List<String> list3 = JsonTools.getList("liststring", jsonString3);
				Log.i(TAG, list3.toString());
				parse_des.setText(list3.toString());
				break;
			case R.id.listmap:
				String path4 = "http://127.0.0.1:8080/jsonProject/servlet/JsonAction?action_flag=listmap";
				String jsonString4 = HttpUtils.getJsonContent(path4);
				List<Map<String, Object>> list4 = JsonTools.listKeyMaps("listmap", jsonString4);
				Log.i(TAG, list4.toString());
				parse_des.setText(list4.toString());
				break;
		}
	}

	@Override
	public void setRootView() {
		setContentView(R.layout.activity_a08net_parse_jsonparse);		
	}
}