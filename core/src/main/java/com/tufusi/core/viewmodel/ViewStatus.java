package com.tufusi.core.viewmodel;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description
 */
public enum ViewStatus {

    /**
     * 加载中
     */
    LOADING,

    /**
     * 空布局展示
     */
    EMPTY,

    /**
     * 内容展示
     */
    DISPLAY_CONTENT,

    /**
     * 不再获得数据
     */
    NO_MORE_DATA,

    /**
     * 刷新失败
     */
    REFRESH_FAILED,

    /**
     * 加载更多失败
     */
    LOAD_MORE_FAILED
}
