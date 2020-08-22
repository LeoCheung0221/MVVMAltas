package com.tufusi.core.model;

import androidx.annotation.CallSuper;

import com.tufusi.core.IBaseModelListener;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description 数据model基类，这里主要用来对外提供注册监听 解绑监听 保存需要暂存的数据对象
 */
public class MBaseModel<T> {

    private boolean isPaging;

    protected ReferenceQueue<IBaseModelListener> mReferenceQueue;
    protected ConcurrentLinkedQueue<WeakReference<IBaseModelListener>> mWeakReferenceListenerQueue;

    public MBaseModel(boolean isPaging) {
        this.isPaging = isPaging;

        // 初始化存储 实现 IBaseModelListener 接口的对象的 引用队列，通过检测已注册的引用对象，可便于系统GC
        mReferenceQueue = new ReferenceQueue<>();
        // 初始化一个无边界线程安全的 FIFO 队列
        mWeakReferenceListenerQueue = new ConcurrentLinkedQueue<>();
    }

    /**
     * 注册数据对象监听器
     */
    public void register(IBaseModelListener listener) {
        if (listener == null) {
            return;
        }

        synchronized (MBaseModel.this) {
            // 每次注册之前 先清理已经被系统回收的对象 凡是需要监听的均需实现此接口
            Reference<? extends IBaseModelListener> releaseListener;

            // 不断遍历引用队列 poll出队列头元素并删除
            while ((releaseListener = mReferenceQueue.poll()) != null) {
                mWeakReferenceListenerQueue.remove(new WeakReference<IBaseModelListener>(releaseListener.get(), mReferenceQueue));
            }

            // 遍历存储IBaseModelListener弱引用队列 如果搜索匹配到，则返回  否则将其添加到队列中
            for (WeakReference<IBaseModelListener> weakReference : mWeakReferenceListenerQueue) {
                IBaseModelListener baseModelListener = weakReference.get();
                if (baseModelListener == listener) {
                    return;
                }
            }

            WeakReference<IBaseModelListener> weakReferenceListener = new WeakReference<>(listener, mReferenceQueue);
            mWeakReferenceListenerQueue.add(weakReferenceListener);
        }
    }

    /**
     * 解绑监听器
     */
    public void unRegister(IBaseModelListener listener) {
        if (listener == null) {
            return;
        }

        synchronized (this){
            for (WeakReference<IBaseModelListener> weakReference : mWeakReferenceListenerQueue) {
                IBaseModelListener baseModelListener = weakReference.get();

                // 找到需解绑的监听器，将其移除
                if (baseModelListener == listener){
                    mWeakReferenceListenerQueue.remove(weakReference);
                    break;
                }
            }
        }
    }

    public boolean isPaging() {
        return isPaging;
    }

    @CallSuper
    public void cancel() {

    }
}