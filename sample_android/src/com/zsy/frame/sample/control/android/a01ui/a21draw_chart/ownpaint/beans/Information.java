package com.zsy.frame.sample.control.android.a01ui.a21draw_chart.ownpaint.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Jenny.Hu on 11/21/2014.
 */
public class Information implements Parcelable {

	private String info_id;
	private String info_title;
	private String info_content;
	private String info_logo_url;

	public String getInfo_id() {
		return info_id;
	}

	public void setInfo_id(String info_id) {
		this.info_id = info_id;
	}

	public String getInfo_title() {
		return info_title;
	}

	public void setInfo_title(String info_title) {
		this.info_title = info_title;
	}

	public String getInfo_content() {
		return info_content;
	}

	public void setInfo_content(String info_content) {
		this.info_content = info_content;
	}

	public String getInfo_logo_url() {
		return info_logo_url;
	}

	public void setInfo_logo_url(String info_logo_url) {
		this.info_logo_url = info_logo_url;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {

	}
}
