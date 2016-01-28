
package com.zsy.frame.sample.control.android.a01ui.a12gallery.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @description：跑马灯
 * @author samy
 * @date 2015-3-16 下午11:42:13
 */
public class MarqueeText extends TextView {
    public MarqueeText(Context context) {
        super(context);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
