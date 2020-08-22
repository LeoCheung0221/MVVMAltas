package com.tufusi.core.utils;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description
 */
public class EditorUtils {

    public static void fastCommit(final SharedPreferences.Editor editor) {
        // edit.apply could not commit your preferences changes in time on
        // Android 4.3
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            GingerbreadCompatLayer.fastCommit(editor);
        } else {
            // FIXME: there's no fast commit below GINGERBREAD.
            editor.commit();
        }
    }

    @TargetApi(9)
    private static class GingerbreadCompatLayer {
        public static void fastCommit(final SharedPreferences.Editor editor) {
            editor.apply();
        }
    }
} 