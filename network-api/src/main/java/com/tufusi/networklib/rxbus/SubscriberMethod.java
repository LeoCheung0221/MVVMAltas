package com.tufusi.networklib.rxbus;

import java.lang.reflect.Method;

import io.reactivex.Scheduler;

/**
 * Created by 鼠夏目 on 2020/8/24.
 *
 * @author 鼠夏目
 * @description
 */
public class SubscriberMethod {

    public Scheduler getThreadMode() {
        return threadMode;
    }

    final Scheduler threadMode;
    final Method method;
    final Class<?> eventType;

    public SubscriberMethod(Method method, Class<?> eventType, Scheduler threadMode) {
        this.method = method;
        this.eventType = eventType;
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getEventType() {
        return eventType;
    }
} 