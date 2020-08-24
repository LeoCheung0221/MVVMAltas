package com.tufusi.networklib.observer;

import com.tufusi.core.model.MBaseModel;
import com.tufusi.networklib.exception.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public abstract class BaseObserver<T> implements Observer<T> {

    private MBaseModel baseModel;

    public BaseObserver(MBaseModel model) {
        baseModel = model;
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (baseModel != null) {
            baseModel.addDisposable(d);
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof ExceptionHandle.ResponseThrowable) {
            onFailure(e);
        } else {
            onFailure(new ExceptionHandle.ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onComplete() {
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable e);
}