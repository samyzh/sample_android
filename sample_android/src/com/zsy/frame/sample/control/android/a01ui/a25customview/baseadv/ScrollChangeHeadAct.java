package com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv;

import com.zsy.frame.sample.R;
import com.zsy.frame.sample.control.android.a01ui.a25customview.baseadv.views.ScrollChangeHeadView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ScrollChangeHeadAct extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a01ui_a25customview_baseadv_scroll_change_head);

        LinearLayout topBar = (LinearLayout) findViewById(R.id.topBar);
        ScrollChangeHeadView schv = (ScrollChangeHeadView) findViewById(R.id.schv);

        schv.setTopBar(topBar);

        schv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv = new TextView(ScrollChangeHeadAct.this);
                tv.setText("+" + position);
                return tv;
            }
        });
    }
}
