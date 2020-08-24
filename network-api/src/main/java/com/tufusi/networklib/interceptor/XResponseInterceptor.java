package com.tufusi.networklib.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public class XResponseInterceptor implements Interceptor {

    private static final String TAG = "ResponseInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.d(TAG, "requestTime = " + (System.currentTimeMillis() - requestTime));
        return response;
    }
}