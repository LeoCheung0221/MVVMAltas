package com.tufusi.networklib;

import com.tufusi.networklib.base.AbsNetworkApi;
import com.tufusi.networklib.bean.TencentBaseResponse;
import com.tufusi.networklib.exception.ExceptionHandle;
import com.tufusi.networklib.utils.TencentUtil;

import java.io.IOException;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public class TencentNetworkApi extends AbsNetworkApi {

    private static volatile TencentNetworkApi singleton = null;

    private TencentNetworkApi() {
    }

    public static TencentNetworkApi getInstance() {
        if (singleton == null) {
            synchronized (TencentNetworkApi.class) {
                if (singleton == null) {
                    singleton = new TencentNetworkApi();
                }
            }
        }
        return singleton;
    }

    public static <T> T getService(Class<T> api) {
        return getInstance().getRetrofit(api).create(api);
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                if (response instanceof TencentBaseResponse
                        && ((TencentBaseResponse) response).showapiResCode != 0) {
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((TencentBaseResponse) response).showapiResCode;
                    exception.message = ((TencentBaseResponse) response).showapiResError != null
                            ? ((TencentBaseResponse) response).showapiResError : "";
                    throw exception;
                }
                return response;
            }
        };
    }

    @Override
    protected Interceptor getInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String timeStr = TencentUtil.getTimeStr();
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Source", "source");
                builder.addHeader("Authorization", TencentUtil.getAuthorization(timeStr));
                builder.addHeader("Date", timeStr);
                return chain.proceed(builder.build());
            }
        };
    }

    @Override
    public String getRelease() {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }

    @Override
    public String getDebug() {
        return "http://service-o5ikp40z-1255468759.ap-shanghai.apigateway.myqcloud.com/";
    }
}