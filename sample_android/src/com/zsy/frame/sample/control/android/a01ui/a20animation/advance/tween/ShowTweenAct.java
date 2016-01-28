package com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween;


import com.zsy.frame.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by admin on 2015/11/13.
 */
public class ShowTweenAct extends Activity {
    private ImageView example;
    private ImageView ball;
    private Button t3d1, t3d2, skew, pao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a01ui_a20animation_advance_tween_showtween);
        findview();
    }

    private void findview() {
        example = (ImageView) this.findViewById(R.id.example);
        ball = (ImageView) this.findViewById(R.id.ball);
        t3d1 = (Button) this.findViewById(R.id.t3d1);
        t3d2 = (Button) this.findViewById(R.id.t3d2);
        skew = (Button) this.findViewById(R.id.skew);
        pao = (Button) this.findViewById(R.id.pao);

        t3d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example.setVisibility(View.VISIBLE);
                ball.setVisibility(View.GONE);
                Animation animation = new ThreeDRotateAnimation(0, 360, v.getWidth() / 2, v.getHeight() / 2, v.getWidth() / 2, 0);
                animation.setDuration(3000);
                example.startAnimation(animation);
                v.startAnimation(animation);
            }
        });
        t3d2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example.setVisibility(View.VISIBLE);
                ball.setVisibility(View.GONE);
                Animation animation = new ThreeDRotateAnimation(0, 360, v.getWidth() / 2, v.getHeight() / 2, v.getWidth() / 2, 1);
                animation.setDuration(3000);
                example.startAnimation(animation);
                v.startAnimation(animation);
            }
        });
        skew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example.setVisibility(View.VISIBLE);
                ball.setVisibility(View.GONE);
                Animation animation = new SkewAnimation(0.5f, 0.5f);
                animation.setDuration(3000);
                example.startAnimation(animation);
                v.startAnimation(animation);
            }
        });
        pao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                example.setVisibility(View.GONE);
                ball.setVisibility(View.VISIBLE);
                Animation animation = new PaoAnimation(v.getWidth());
                animation.setDuration(3000);
                ball.startAnimation(animation);
            }
        });

    }
}
