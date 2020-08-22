package com.tufusi.core.loadsir;

import android.content.Context;
import android.view.View;

import com.kingja.loadsir.callback.Callback;
import com.tufusi.core.R;

/**
 * Created by 鼠夏目 on 2020/8/18.
 *
 * @author 鼠夏目
 * @description
 * @see
 */
public class PlaceholderCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_placeholder;
    }

    @Override
    protected boolean onReloadEvent(Context context, View view) {
        return true;
    }
}
