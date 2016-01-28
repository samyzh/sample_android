package com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween;

import com.zsy.frame.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by admin on 2015/11/15.
 */
public class AllTweenAct extends Activity {
    private ImageView example;

    private Button go;
    private AnimationSet animationSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a01ui_a20animation_advance_tween_alltween);
        findview();
    }

    private void findview() {
        example = (ImageView) this.findViewById(R.id.example);

        go = (Button) this.findViewById(R.id.go);

        animationSet = (AnimationSet) AnimationUtils.loadAnimation(this, R.anim.anim_a01ui_a20animation_advance_tween_animset);

        Animation animation = new ThreeDRotateAnimation(0, 360, 0, 0, 100, 1);
        animation.setDuration(3000);
        animation.setStartOffset(6000);
        animationSet.addAnimation(animation);
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example.startAnimation(animationSet);
                v.startAnimation(animationSet);
            }
        });
    }
}
