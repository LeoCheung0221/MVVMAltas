package com.tufusi.networklib.exception;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description HttpResponseFunc 处理以下两种网络错误
 * 1、http请求相关的错误，例如：404，403，socket timeout等等；
 * 2、应用数据的错误会抛RuntimeException，最后也会走到这个函数来统一处理；
 */
public class HttpErrorHandle<T> implements Function<Throwable, Observable<T>> {

    @Override
    public Observable<T> apply(Throwable throwable) {
        return io.reactivex.Observable.error(ExceptionHandle.handleException(throwable));
    }
}