package com.tufusi.core.customeview;

/**
 * Created by 鼠夏目 on 2020/8/26.
 *
 * @author 鼠夏目
 * @description
 */
public interface ICustomView<S extends BaseCustomViewModel> {

    void setData(S data);

    void setStyle(int resId);

    void setActionListener(ICustomViewActionListener listener);
} 