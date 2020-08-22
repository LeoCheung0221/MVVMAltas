package com.tufusi.core.preference;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description
 */
public class BasicDataPreferenceUtil extends BasePreferences {

    private static final String BASIC_DATA_PREFERENCE_FILE_NAME = "network_api_module_basic_data_preference";

    private static volatile BasicDataPreferenceUtil singleton = null;

    private BasicDataPreferenceUtil() {
    }

    public static BasicDataPreferenceUtil getInstance() {
        if (singleton == null) {
            synchronized (BasicDataPreferenceUtil.class) {
                if (singleton == null) {
                    singleton = new BasicDataPreferenceUtil();
                }
            }
        }
        return singleton;
    }

    @Override
    protected String getSPFileName() {
        return BASIC_DATA_PREFERENCE_FILE_NAME;
    }
}