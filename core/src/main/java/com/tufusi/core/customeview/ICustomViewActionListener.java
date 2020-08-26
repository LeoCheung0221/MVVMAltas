package com.tufusi.core.customeview;

import android.view.View;

/**
 * Created by 鼠夏目 on 2020/8/26.
 *
 * @author 鼠夏目
 * @description
 */
public interface ICustomViewActionListener {

    String ACTION_ROOT_VIEW_CLICKED = "action_root_view_clicked";

    void onAction(String action, View view, BaseCustomViewModel viewModel);
}
