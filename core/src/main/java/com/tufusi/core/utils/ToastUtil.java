package com.tufusi.core.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description 吐司工具类
 */
public class ToastUtil {

    private static Toast mToast;
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void show(String msg) {
        try {
            if (mContext != null && !TextUtils.isEmpty(msg)) {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(mContext, "", Toast.LENGTH_LONG);
                mToast.setText(msg);
                mToast.show();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}