package com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv.views;


import com.zsy.frame.sample.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ItemView2 extends RelativeLayout {
    private RelativeLayout item_rl;
    private TextView tvLeft;
    private TextView tvRight;
    private ImageView ivArrow;

    public ItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_a01ui_a25customview_baseadv_item_view2, this);

        item_rl = (RelativeLayout) findViewById(R.id.item_rl);
        tvLeft = (TextView) findViewById(R.id.tvLeft);
        tvRight = (TextView) findViewById(R.id.tvRight);
        ivArrow = (ImageView) findViewById(R.id.ivArrow);
        
        /**自定义属性*/
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.ItemView2);
        
        String leftText = a.getString(R.styleable.ItemView2_leftText);
        String rightText = a.getString(R.styleable.ItemView2_rightText);
        boolean showArrow = a.getBoolean(R.styleable.ItemView2_showArrow,false);
        a.recycle();
        setView(leftText, rightText, showArrow);        
    }

    

	/**
	 * 设置内容
	 *
	 * @param tvLeftStr
	 *            左边内容
	 * @param tvRight
	 *            右边内容
	 * @param isShowArrow
	 *            是否显示小箭头
	 */
	public void setView(String tvLeftStr, String tvRightStr, boolean isShowArrow) {
		if (tvLeftStr != null) {
			tvLeft.setText(tvLeftStr);
		}

		if (tvRightStr != null) {
			tvRight.setText(tvRightStr);
		}

		if (isShowArrow) {
			ivArrow.setVisibility(View.VISIBLE);
		} else {
			ivArrow.setVisibility(View.GONE);
		}

	}
	
	/**自定义点击事件*/
	public void setOnItemClickListener(OnClickListener l){
		item_rl.setOnClickListener(l);
	}
	
}
