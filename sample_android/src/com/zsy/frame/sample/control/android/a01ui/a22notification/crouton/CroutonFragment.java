/*
 * Copyright 2012 - 2014 Benjamin Weiss
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

package com.zsy.frame.sample.control.android.a01ui.a22notification.crouton;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.zsy.frame.sample.R;

import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

/**
 * @description：常用配置用法:
 * 	@Override
	public void onPreExecute() {
		Crouton.makeText(this, R.string.offline_download_doing, Style.INFO).show();
	}
	@Override
	public void onPostExecute(String content, boolean isRefreshSuccess, boolean isContentSame) {

		if (!TextUtils.isEmpty(content) && content.equals("success")) {
			Crouton.makeText(this, R.string.offline_download_done, Style.INFO).show();
		}
		else {
			Crouton.makeText(this, R.string.offline_download_fail, Style.ALERT).show();
		}
	}
	@Override
	public void onFail(final Exception e) {
		if (!isFinishing()) {
			Crouton.makeText(this, R.string.offline_download_fail, Style.ALERT).show();
		}
	}
	
	@Override
	protected void onDestroy() {
		Crouton.clearCroutonsForActivity(this);
		super.onDestroy();
	}
	
 * @author samy
 * @date 2015年6月13日 下午6:11:41
 */
public class CroutonFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

	private static final Style INFINITE = new Style.Builder().setBackgroundColorValue(Style.holoBlueLight).build();
	private static final Configuration CONFIGURATION_INFINITE = new Configuration.Builder().setDuration(Configuration.DURATION_INFINITE).build();

	private CheckBox displayOnTop;
	private Spinner styleSpinner;
	private EditText croutonTextEdit;
	private EditText croutonDurationEdit;
	private Crouton infiniteCrouton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_a01ui_a22notification_crouton_demo, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		view.findViewById(R.id.button_show).setOnClickListener(this);
		croutonTextEdit = (EditText) view.findViewById(R.id.edit_text_text);
		croutonDurationEdit = (EditText) view.findViewById(R.id.edit_text_duration);
		styleSpinner = (Spinner) view.findViewById(R.id.spinner_style);
		styleSpinner.setOnItemSelectedListener(this);
		displayOnTop = (CheckBox) view.findViewById(R.id.display_on_top);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.button_show: {
				showCrouton();
				break;
			}

			default: {
				if (infiniteCrouton != null) {
					Crouton.hide(infiniteCrouton);
					infiniteCrouton = null;
				}
				break;
			}
		}
	}

	/**
	 * @description：显示提示；包括1：普通显示；2：升级版本提示；
	 * @author samy
	 * @date 2015年6月13日 上午11:10:17
	 */
	private void showCrouton() {
		Style croutonStyle = getSelectedStyleFromSpinner();
		if (croutonStyle != null) {
			showBuiltInCrouton(croutonStyle);
		}
		else {
			showAdvancedCrouton();
		}
	}

	private Style getSelectedStyleFromSpinner() {
		switch ((int) styleSpinner.getSelectedItemId()) {
			case 0: {
				return Style.ALERT;
			}

			case 1: {
				return Style.CONFIRM;
			}

			case 2: {
				return Style.INFO;
			}

			case 3: {
				return INFINITE;
			}

			default: {
				return null;
			}
		}
	}

	private void showAdvancedCrouton() {
		switch (styleSpinner.getSelectedItemPosition()) {
			case 4: {
				showCustomCrouton();
				break;
			}
			case 5: {
				showCustomViewCrouton();
				break;
			}
		}
	}

	private void showBuiltInCrouton(final Style croutonStyle) {
		String croutonText = getCroutonText();
		showCrouton(croutonText, croutonStyle, Configuration.DEFAULT);
	}

	/**
	 * @description：获取编辑的文字；
	 * @author samy
	 * @date 2015年6月13日 上午11:11:23
	 */
	private String getCroutonText() {
		String croutonText = croutonTextEdit.getText().toString().trim();
		if (TextUtils.isEmpty(croutonText)) {
			croutonText = getString(R.string.text_demo);
		}
		return croutonText;
	}

	/**
	 * @description：1：普通显示；
	 * @author samy
	 * @date 2015年6月13日 上午11:11:12
	 */
	private void showCustomCrouton() {
		String croutonDurationString = getCroutonDurationString();
		if (TextUtils.isEmpty(croutonDurationString)) {
			showCrouton(getString(R.string.warning_duration), Style.ALERT, Configuration.DEFAULT);
			return;
		}
		int croutonDuration = Integer.parseInt(croutonDurationString);
		Style croutonStyle = new Style.Builder().build();
		Configuration croutonConfiguration = new Configuration.Builder().setDuration(croutonDuration).build();

		String croutonText = getCroutonText();

		showCrouton(croutonText, croutonStyle, croutonConfiguration);
	}

	/**
	 * @description：2：升级版本提示；
	 * @author samy
	 * @date 2015年6月13日 上午11:12:11
	 */
	private void showCustomViewCrouton() {
		View view = getLayoutInflater(null).inflate(R.layout.view_a01ui_a22notification_crouton_custom_view, null);
		final Crouton crouton;
		if (displayOnTop.isChecked()) {
			crouton = Crouton.make(getActivity(), view);
		}
		else {
			crouton = Crouton.make(getActivity(), view, R.id.alternate_view_group);
		}
		crouton.show();
	}

	private String getCroutonDurationString() {
		return croutonDurationEdit.getText().toString().trim();
	}

	/**
	 * @description：加载自己默认的几个样式；
	 * @author samy
	 * @date 2015年6月13日 下午6:10:42
	 */
	private void showCrouton(String croutonText, Style croutonStyle, Configuration configuration) {
		final boolean infinite = INFINITE == croutonStyle;
		if (infinite) {
			croutonText = getString(R.string.infinity_text);
		}
		final Crouton crouton;
		if (displayOnTop.isChecked()) {
			crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle);
		}
		else {
//			添加父容器依附在父容器；
			crouton = Crouton.makeText(getActivity(), croutonText, croutonStyle, R.id.alternate_view_group);
		}
		if (infinite) {
			infiniteCrouton = crouton;
		}
		// crouton.setOnClickListener(this).setConfiguration(infinite ? CONFIGURATION_INFINITE : configuration).show();
		crouton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Crouton.hide(crouton);
			}
		}).setConfiguration(infinite ? CONFIGURATION_INFINITE : configuration).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
		switch ((int) id) {

			case 4: { // Custom Style
				croutonTextEdit.setVisibility(View.VISIBLE);
				croutonDurationEdit.setVisibility(View.VISIBLE);
				break;
			}

			case 5: { // Custom View
				croutonTextEdit.setVisibility(View.GONE);
				croutonDurationEdit.setVisibility(View.GONE);
				break;
			}

			default: {
				croutonTextEdit.setVisibility(View.VISIBLE);
				croutonDurationEdit.setVisibility(View.GONE);
				break;
			}
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> adapterView) {
		/* no-op */
	}
}
