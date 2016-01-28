package com.zsy.frame.sample.control.android.a30designmode.mvp.view;

import com.zsy.frame.sample.control.android.a30designmode.mvp.bean.DoterBean;

/**
 * @description：其实确认的说这里的view是包含UI 显示界面的
 * @author samy
 * @date 2015-4-24 下午4:21:19
 */
public interface IDoterInfoView extends ILoadDataView {
    public void showDoterInfo(DoterBean info);
}
