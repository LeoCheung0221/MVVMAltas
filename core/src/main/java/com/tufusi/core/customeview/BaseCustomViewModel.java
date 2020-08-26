package com.tufusi.core.customeview;

import java.io.Serializable;

/**
 * Created by 鼠夏目 on 2020/8/26.
 *
 * @author 鼠夏目
 * @description
 */
public class BaseCustomViewModel implements Serializable {

    private String jumpUrl;

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}