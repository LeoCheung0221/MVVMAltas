package com.tufusi.networklib.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public class TencentBaseResponse {

    @SerializedName("showapi_res_code")
    @Expose
    public Integer showapiResCode;

    @SerializedName("showapi_res_error")
    @Expose
    public String showapiResError;

} 