package com.tufusi.networklib.base;

import android.app.Application;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public interface INetworkRequiredInfo {

    /**
     * 获取APP版本名称
     */
    String getAppVersionName();

    /**
     * 获取APP版本号
     */
    String getAppVersionCode();

    boolean isDebug();

    Application getApplicationContext();

}
