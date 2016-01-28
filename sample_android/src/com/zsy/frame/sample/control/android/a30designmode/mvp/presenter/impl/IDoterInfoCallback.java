package com.zsy.frame.sample.control.android.a30designmode.mvp.presenter.impl;

import com.zsy.frame.sample.control.android.a30designmode.mvp.bean.DoterBean;

/**
 * @description：在原来的接口上，补充添加接口onResponse
 * @author samy
 * @date 2015-4-24 下午4:16:32
 */
public interface IDoterInfoCallback extends ILoadDataCallback {
    public void onResponse(DoterBean info);
}
