package com.zsy.frame.sample.control.android.a01ui.a15dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.zsy.frame.sample.R;

public class NiftydialogeffectsAct extends Activity {

	private Effectstype effect;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a01ui_a15dialog_niftydialogeffects);

	}

	public void dialogShow(View v) {
		NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);

		switch (v.getId()) {
			case R.id.fadein:
				effect = Effectstype.Fadein;
				break;
			case R.id.slideright:
				effect = Effectstype.Slideright;
				break;
			case R.id.slideleft:
				effect = Effectstype.Slideleft;
				break;
			case R.id.slidetop:
				effect = Effectstype.Slidetop;
				break;
			case R.id.slideBottom:
				effect = Effectstype.SlideBottom;
				break;
			case R.id.newspager:
				effect = Effectstype.Newspager;
				break;
			case R.id.fall:
				effect = Effectstype.Fall;
				break;
			case R.id.sidefall:
				effect = Effectstype.Sidefill;
				break;
			case R.id.fliph:
				effect = Effectstype.Fliph;
				break;
			case R.id.flipv:
				effect = Effectstype.Flipv;
				break;
			case R.id.rotatebottom:
				effect = Effectstype.RotateBottom;
				break;
			case R.id.rotateleft:
				effect = Effectstype.RotateLeft;
				break;
			case R.id.slit:
				effect = Effectstype.Slit;
				break;
			case R.id.shake:
				effect = Effectstype.Shake;
				break;
		}

//		dialogBuilder.withTitle("Modal Dialog") // .withTitle(null) no title
		dialogBuilder.withTitle(null) // .withTitle(null) no title
				.withTitleColor("#FFFFFF") // def
				.withDividerColor("#11000000") // def
//				.withMessage("This is a modal Dialog.") // .withMessage(null) no Msg
				.withMessage(null) // .withMessage(null) no Msg
				.withMessageColor("#FFFFFF") // def
				.withIcon(getResources().getDrawable(R.drawable.ic_launcher)).isCancelableOnTouchOutside(true) // def | isCancelable(true)
				.withDuration(700) // def
				.withEffect(effect) // def Effectstype.Slidetop
				.withButton1Text("OK") // def gone
				.withButton2Text("Cancel") // def gone
//				.setContentView(null,v.getContext())
				.setCustomView(R.layout.view_a01ui_a15dialog_niftydialogeffects_custom_view, v.getContext()) // .setCustomView(View or ResId,context)
				.setButton1Click(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(v.getContext(), "i'm btn1", Toast.LENGTH_SHORT).show();
					}
				}).setButton2Click(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Toast.makeText(v.getContext(), "i'm btn2", Toast.LENGTH_SHORT).show();
					}
				}).show();

	}

}
