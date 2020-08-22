package com.tufusi.mvvmaltas.app;

import android.app.Application;

import com.tufusi.core.utils.ToastUtil;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description
 */
public class AltasApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initSdkOrUtils();
    }

    private void initSdkOrUtils() {
        ToastUtil.init(this);
    }
}