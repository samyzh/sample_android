package com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BaseEvent;
import com.zsy.frame.sample.control.android.a26setting.bluetooth.projects.bloodpressure.events.BaseEvent.UploadBloodPresureEvent;

/**
 * @description：血压检测中的手动输入
 * @author samy
 * @date 2015-2-24 下午5:00:20
 */
public class ManualInputFragment extends IntelligentManualFragment implements OnClickListener {
	EditText sbpEt;
	EditText dbpEt;
	EditText hrEt;
	TextView confirmTv;

	private int manualMode;

	@Override
	protected int inflateContentView() {
		return R.layout.fragment_a26setting_bluetooth_projects_bloodpressure_manual_input;
	}

	@Override
	protected void _initData() {
		super._initData();

		sbpEt = (EditText) rootView.findViewById(R.id.mi_sbpEt);
		dbpEt = (EditText) rootView.findViewById(R.id.mi_dbpEt);
		hrEt = (EditText) rootView.findViewById(R.id.mi_hrEt);

		confirmTv = (TextView) rootView.findViewById(R.id.mi_confirmTv);
		confirmTv.setOnClickListener(this);

		manualMode = 0;

		sbpEt.addTextChangedListener(sbpWatcher);
		dbpEt.addTextChangedListener(dbpWatcher);
		hrEt.addTextChangedListener(hrWatcher);
	}

	TextWatcher sbpWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable editable) {
			if (manualMode == 1 && !sbpString.equals(sbpEt.getText().toString().trim())) {
				initConfirmStatus();
			}
			else if (manualMode == 0 && !TextUtils.isEmpty(sbpString) && sbpString.equals(sbpEt.getText().toString().trim())) {
				if (dbpString.equals(dbpEt.getText().toString().trim()) && hrString.equals(hrEt.getText().toString().trim())) {
					initSaveStatus();
				}
			}
		}
	};

	TextWatcher dbpWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable editable) {
			if (manualMode == 1 && !dbpString.equals(dbpEt.getText().toString().trim())) {
				initConfirmStatus();
			}
			else if (manualMode == 0 && !TextUtils.isEmpty(dbpString) && dbpString.equals(dbpEt.getText().toString().trim())) {
				if (sbpString.equals(sbpEt.getText().toString().trim()) && hrString.equals(hrEt.getText().toString().trim())) {
					initSaveStatus();
				}
			}
		}
	};

	TextWatcher hrWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {

		}

		@Override
		public void afterTextChanged(Editable editable) {
			if (manualMode == 1 && !hrString.equals(hrEt.getText().toString().trim())) {
				initConfirmStatus();
			}
			else if (manualMode == 0 && !TextUtils.isEmpty(hrString) && hrString.equals(hrEt.getText().toString().trim())) {
				if (sbpString.equals(sbpEt.getText().toString().trim()) && dbpString.equals(dbpEt.getText().toString().trim())) {
					initSaveStatus();
				}
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.mi_confirmTv:
				if (manualMode == 0) {
					if (editDataStatus()) {
						// CommonUtils.hideInputMethod(getActivity());
						execHttpMethod(MANUALINPUT);
					}
				}
				else {
					// CommonUtils.hideInputMethod(getActivity());
					saveBloodDataToDB();
					initConfirmStatus();
				}

				break;

			case R.id.im_hintmoreTv:

				break;

			default:
				break;
		}

	}

	void initConfirmStatus() {
		manualMode = 0;
		confirmTv.setText(getString(R.string.confirm_data));
	}

	void initSaveStatus() {
		manualMode = 1;
		confirmTv.setText("保存至本地");
	}

	private boolean editDataStatus() {
		sbpString = sbpEt.getText().toString().trim();
		dbpString = dbpEt.getText().toString().trim();
		hrString = hrEt.getText().toString().trim();

		if (TextUtils.isEmpty(sbpString)) {
			// CommonUtils.showToastMessage(getActivity(), getString(R.string.pls_input_systolic));
			return false;
		}

		if (TextUtils.isEmpty(dbpString)) {
			// CommonUtils.showToastMessage(getActivity(), getString(R.string.pls_input_diastolic));
			return false;
		}

		if (TextUtils.isEmpty(hrString)) {
			// CommonUtils.showToastMessage(getActivity(), getString(R.string.pls_input_heart));
			return false;
		}

		return true;
	}

	public void onEventMainThread(UploadBloodPresureEvent baseEvent) {
		// CommonUtils.dismissDialog();

		if (baseEvent.httpRequestSuccess && baseEvent.resultCode == BaseEvent.REQUESTCODE_SUCCESS) {
			// CommonUtils.showToastMessage(getActivity(), baseEvent.resultMessage.toString());
		}
		else {
			// CommonUtils.showToastMessage(getActivity(), baseEvent.resultMessage.toString());
		}
		initSaveStatus();
	}
}
