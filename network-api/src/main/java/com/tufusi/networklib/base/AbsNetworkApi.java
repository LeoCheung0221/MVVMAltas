package com.tufusi.networklib.base;

import com.tufusi.networklib.env.EnvironmentActivity;
import com.tufusi.networklib.env.IEnvironment;
import com.tufusi.networklib.exception.HttpErrorHandle;
import com.tufusi.networklib.interceptor.XRequestInterceptor;
import com.tufusi.networklib.interceptor.XResponseInterceptor;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public abstract class AbsNetworkApi implements IEnvironment {

    /**
     * 是否是正式环境 默认是
     */
    private static boolean mIsFormal = true;

    /**
     * 用于存储请求的 Retrofit实例对象，键值是请求的网络链接路径
     */
    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();

    /**
     * 网络请求信息相关接口类
     */
    private static INetworkRequiredInfo iNetworkRequiredInfo;

    /**
     * 请求URL 基路径 这里可以考虑多域名分发请求
     */
    private String mBaseUrl;

    /**
     * 网络请求客户端实例对象
     */
    private OkHttpClient mOkHttpClient;

    public AbsNetworkApi() {
        // 如果是测试环境
        if (!mIsFormal) {
            mBaseUrl = getDebug();
        }
        mBaseUrl = getRelease();
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
        mIsFormal = EnvironmentActivity.isReleaseEnvironment(networkRequiredInfo.getApplicationContext());
    }

    protected Retrofit getRetrofit(Class apiService) {
        if (retrofitHashMap.get(mBaseUrl + apiService.getName()) != null) {
            return retrofitHashMap.get(mBaseUrl + apiService.getName());
        }

        Retrofit retrofit = new Retrofit.Builder().baseUrl(mBaseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofitHashMap.put(mBaseUrl + apiService.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                builder.addInterceptor(getInterceptor());
            }

            builder.addInterceptor(new XRequestInterceptor(iNetworkRequiredInfo));
            builder.addInterceptor(new XResponseInterceptor());

            if (iNetworkRequiredInfo != null && iNetworkRequiredInfo.isDebug()) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(httpLoggingInterceptor);
            }

            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                Observable<T> observable = upstream.
                        subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());

                observable.map(getAppErrorHandler());
                observable.subscribe(observer);
                observable.onErrorResumeNext(new HttpErrorHandle<T>());
                return observable;
            }
        };
    }

    protected abstract <T> Function<T, T> getAppErrorHandler();

    protected abstract Interceptor getInterceptor();

}