package com.tufusi.core.model;

import java.io.Serializable;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description 缓存数据存储对象 实现序列化
 */
public class MBaseCacheDate<T> implements Serializable {

    /**
     * 更新时间戳
     */
    public long updateTimestamp;

    /**
     * 存储数据
     */
    public T data;

} 