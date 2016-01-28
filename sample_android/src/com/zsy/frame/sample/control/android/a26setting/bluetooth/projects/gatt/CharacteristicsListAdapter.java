package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.gatt;

import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zsy.frame.sample.R;

/**
 * /**
 * 如：对应BleNamesResolver出来的数据
 * 	Generic Access   （Device Name; Apperance; periphery privacy flay ；ReconnectionAddress；Perpetual preferred ConnectionParameter）
 * 	Generic Attribute （service changed）
 * 	Unkonwn Service （unknown characteristic）
 * 
 * @description：Characteristic
Characteristic可以理解为一个数据类型，它包括一个value和0至多个对次value的描述（Descriptor）。
 * @author samy
 * @date 2015-2-23 下午8:51:01
 */
public class CharacteristicsListAdapter extends BaseAdapter {
	private ArrayList<BluetoothGattCharacteristic> mCharacteristics;
	private LayoutInflater mInflater;

	public CharacteristicsListAdapter(Activity parent) {
		super();
		mCharacteristics = new ArrayList<BluetoothGattCharacteristic>();
		mInflater = parent.getLayoutInflater();
	}

	public void addCharacteristic(BluetoothGattCharacteristic ch) {
		if (mCharacteristics.contains(ch) == false) {
			mCharacteristics.add(ch);
		}
	}

	public BluetoothGattCharacteristic getCharacteristic(int index) {
		return mCharacteristics.get(index);
	}

	public void clearList() {
		mCharacteristics.clear();
	}

	@Override
	public int getCount() {
		return mCharacteristics.size();
	}

	@Override
	public Object getItem(int position) {
		return getCharacteristic(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// get already available view or create new if necessary
		FieldReferences fields;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_a26setting_bluetooth_gatt_peripheral_list_characteristic, null);
			fields = new FieldReferences();
			fields.charName = (TextView) convertView.findViewById(R.id.peripheral_list_characteristic_name);
			fields.charUuid = (TextView) convertView.findViewById(R.id.peripheral_list_characteristic_uuid);
			convertView.setTag(fields);
		}
		else {
			fields = (FieldReferences) convertView.getTag();
		}

		// set proper values into the view
		BluetoothGattCharacteristic ch = getCharacteristic(position);
		String uuid = ch.getUuid().toString().toLowerCase(Locale.getDefault());
		String name = BleNamesResolver.resolveCharacteristicName(uuid);

		fields.charName.setText(name);
		fields.charUuid.setText(uuid);

		return convertView;
	}

	private class FieldReferences {
		TextView charName;
		TextView charUuid;
	}
}
