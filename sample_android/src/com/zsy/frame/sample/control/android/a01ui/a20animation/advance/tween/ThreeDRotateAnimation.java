package com.zsy.frame.sample.control.android.a01ui.a20animation.advance.tween;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.graphics.Camera;
import android.graphics.Matrix;

/**
 * Created by admin on 2015/11/13.
 */
public class ThreeDRotateAnimation extends Animation {
    private final float mFromDegrees;
    private final float mToDegrees;
    private final float mCenterX;
    private final float mCenterY;
    private final float mDepthZ;
    private Camera mCamera;
    private int type;
    /**
     * @param fromDegrees the start angle of the 3D rotation
     * @param toDegrees   the end angle of the 3D rotation
     * @param centerX     the X center of the 3D rotation
     * @param centerY     the Y center of the 3D rotation
     */
    public ThreeDRotateAnimation(float fromDegrees, float toDegrees,
                                 float centerX, float centerY, float depthZ,int type) {
        mFromDegrees = fromDegrees;
        mToDegrees = toDegrees;
        mCenterX = centerX;
        mCenterY = centerY;
        mDepthZ = depthZ;
        this.type=type;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {

        if(type==1)
        {
            myTransformation(interpolatedTime,t);
            return;
        }
        float degrees = mFromDegrees + ((mToDegrees - mFromDegrees) * interpolatedTime);
        final Matrix matrix = t.getMatrix();
        mCamera.save();
        mCamera.rotateY(degrees);
        mCamera.rotateX(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);

    }

    private void myTransformation(float interpolatedTime, Transformation t) {

        float degrees = mFromDegrees + ((mToDegrees - mFromDegrees) * interpolatedTime);
        float degreespi=(float)(2*Math.PI*degrees/360);
        final Matrix matrix = t.getMatrix();
        mCamera.save();



        mCamera.translate((float) (mDepthZ * Math.sin(degreespi)), 0.0f, (float) (1 - Math.cos(degreespi)) * mDepthZ);
        mCamera.rotateY(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();
        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);

    }
}