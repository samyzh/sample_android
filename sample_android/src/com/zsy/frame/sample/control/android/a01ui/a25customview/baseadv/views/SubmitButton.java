package com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv.views;


import com.zsy.frame.sample.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 自定义提交按钮
 */
public class SubmitButton extends Button {
    public SubmitButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.a01ui_a25customview_baseadv_btn_submit);
        setText("提交");
        setTextSize(18);
        setTextColor(0xffffffff);
    }
}
