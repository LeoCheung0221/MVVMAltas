package com.tufusi.core.preference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description
 */
public class PreferencesUtil extends BasePreferences {

    public static void init(Application application) {
        sApplication = application;
    }


    private static volatile PreferencesUtil singleton = null;

    private PreferencesUtil() {
    }

    public static PreferencesUtil getInstance() {
        if (singleton == null) {
            synchronized (PreferencesUtil.class) {
                if (singleton == null) {
                    singleton = new PreferencesUtil();
                }
            }
        }
        return singleton;
    }

    @Override
    protected String getSPFileName() {
        return "common_data";
    }

    private static final String CONFIG = "config";
    private static final String APP_CONFIG = "app_config";
    private static SharedPreferences sp;

    private static SharedPreferences appConfigSp;

    /**
     * 写入boolean变量至sp中
     *
     * @param key   存储节点名称
     * @param value 存储节点的值string
     */
    public void putCodeString(String key, String value) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = sApplication.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 读取boolean标示从sp中
     *
     * @param key      存储节点名称
     * @param defValue 没有此节点默认值
     * @return 默认值或者此节点读取到的结果
     */
    public String getCodeString(String key, String defValue) {
        //(存储节点文件名称,读写方式)
        if (sp == null) {
            sp = sApplication.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    public void putAppStatusBoolean(String key, boolean value) {
        if (appConfigSp == null) {
            appConfigSp = sApplication.getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
        }
        appConfigSp.edit().putBoolean(key, value).commit();
    }

    public boolean getAppStatusBoolean(String key, boolean defValue) {
        //(存储节点文件名称,读写方式)
        if (appConfigSp == null) {
            appConfigSp = sApplication.getSharedPreferences(APP_CONFIG, Context.MODE_PRIVATE);
        }
        return appConfigSp.getBoolean(key, defValue);
    }
}