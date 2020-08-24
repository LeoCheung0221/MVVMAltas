package com.tufusi.core.model;

import com.tufusi.core.model.MBaseModel;
import com.tufusi.core.model.PagingResult;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description 数据model对外提供两个行为 一个是加载成功 一个是加载失败
 */
public interface IBaseModelListener<T> {

    /**
     * 加载成功回调
     *
     * @param model  具体业务对应的继承Model
     * @param data   获取的数据
     * @param result 数据存储的对象 用来处理分页与否，空布局加载与否
     */
    void onLoadSuccess(MBaseModel model, T data, PagingResult... result);

    /**
     * 加载失败回调
     *
     * @param model  具体业务对应的继承Model
     * @param prompt 加载失败异常或失败 提示
     * @param result 数据存储的对象 用来处理分加载失败展示
     */
    void onLoadFailure(MBaseModel model, String prompt, PagingResult... result);

}
