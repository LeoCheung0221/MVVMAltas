package com.tufusi.networklib.interceptor;

import com.tufusi.networklib.base.INetworkRequiredInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public class XRequestInterceptor implements Interceptor {

    private INetworkRequiredInfo requiredInfo;

    public XRequestInterceptor(INetworkRequiredInfo networkRequiredInfo){
        requiredInfo = networkRequiredInfo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", requiredInfo.getAppVersionCode());
        return chain.proceed(builder.build());
    }
}