package com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by admin on 2015/11/14.
 */
public class PaoAnimation extends Animation {
    private final float mPaoX;

    private Camera mCamera;
    private int type;

    public PaoAnimation(float mPaoX) {
        this.mPaoX = mPaoX;

    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        float paoX = mPaoX * interpolatedTime;
        float paoY = paoX * paoX;
        final Matrix matrix = t.getMatrix();
        matrix.setTranslate(15 * paoX, paoY);
    }

}
