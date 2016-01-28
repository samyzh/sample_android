/*
 * Copyright (C) 2013 The Android Open Source Project
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.helps;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for
 * demonstration purposes.
 */
public class SampleGattAttributes {
	private static HashMap<String, String> attributes = new HashMap();
	public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
	public static String APPEAREANCE = "00002a01-0000-1000-8000-00805f9b34fb";
	public static String CERTIFICATION_DATA_LIST = "00002a2a-0000-1000-8000-00805f9b34fb";
	public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
	public static String DEVICE_NAME = "00002a00-0000-1000-8000-00805f9b34fb";
	public static String DIS = "0000180a-0000-1000-8000-00805f9b34fb";
	public static String FIRMWARE_REVISION_STRING = "00002a26-0000-1000-8000-00805f9b34fb";
	public static String GAP = "00001800-0000-1000-8000-00805f9b34fb";
	public static String GATT = "00001801-0000-1000-8000-00805f9b34fb";
	public static String HARDWARE_REVISION_STRING = "00002a27-0000-1000-8000-00805f9b34fb";
	public static String MANUFACTURER_NAME_STRING = "00002a23-0000-1000-8000-00805f9b34fb";
	public static String MODLE_NUMBER_STRING = "00002a24-0000-1000-8000-00805f9b34fb";
	public static String PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS = "00002a04-0000-1000-8000-00805f9b34fb";
	public static String PERIPHERAL_PRIVACY_FALG = "00002a02-0000-1000-8000-00805f9b34fb";
	public static String PNP_ID = "00002a50-0000-1000-8000-00805f9b34fb";
	public static String RECONNECTION_ADDRESS = "00002a03-0000-1000-8000-00805f9b34fb";
	public static String SERAL_NUMBER_STRING = "00002a25-0000-1000-8000-00805f9b34fb";
	public static String SERVICE_CHANGED = "00002a05-0000-1000-8000-00805f9b34fb";
	public static String SOFTWARE_REVISION_STRING = "00002a28-0000-1000-8000-00805f9b34fb";
	public static String SPPLE_NOTIFY = "0000fff2-0000-1000-8000-00805f9b34fb";
	public static String SPPLE_POUT = "0000fff3-0000-1000-8000-00805f9b34fb";
	public static String SPPLE_SERV = "0000fff0-0000-1000-8000-00805f9b34fb";
	public static String SPPLE_WRITE = "0000fff1-0000-1000-8000-00805f9b34fb";
	public static String SYSTEM_ID = "00002a29-0000-1000-8000-00805f9b34fb";
	static {
		// Sample Services.
		attributes.put("0000180d-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
		attributes.put(DIS, "Device Information Service");
		// Sample Characteristics.
		attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
		attributes.put(SYSTEM_ID, "Manufacturer Name String");

		attributes.put(GAP, "Generic Access Service");
		attributes.put(GATT, "Generic Attribute Service");
		attributes.put(DIS, "Device Information Service");
		attributes.put(SPPLE_SERV, "SPPLE Service");
		attributes.put(DEVICE_NAME, "Device name");
		attributes.put(APPEAREANCE, "Appearance");
		attributes.put(PERIPHERAL_PRIVACY_FALG, "Peripheral privacy flag");
		attributes.put(RECONNECTION_ADDRESS, "Reconnection address");
		attributes.put(PERIPHERAL_PREFERRED_CONNECTION_PARAMETERS, "peripheral preferred connection_parameters");
		attributes.put(SERVICE_CHANGED, "Service changed");
		attributes.put(MANUFACTURER_NAME_STRING, "Manufacturer name string");
		attributes.put(MODLE_NUMBER_STRING, "Model number string");
		attributes.put(SERAL_NUMBER_STRING, "Serial number string");
		attributes.put(HARDWARE_REVISION_STRING, "Hardware revision string");
		attributes.put(FIRMWARE_REVISION_STRING, "Firmware revision string");
		attributes.put(SOFTWARE_REVISION_STRING, "Software revision string");
		attributes.put(SYSTEM_ID, "System id");
		attributes.put(CERTIFICATION_DATA_LIST, "Certification data list");
		attributes.put(PNP_ID, "Pnp ID");
		attributes.put(SPPLE_WRITE, "SPPLE Write");
		attributes.put(SPPLE_NOTIFY, "SPPLE Notify");
		attributes.put(SPPLE_POUT, "SPPLE Pout");

	}

	public static String lookup(String uuid, String defaultName) {
		String name = attributes.get(uuid);
		return name == null ? defaultName : name;
	}
}
