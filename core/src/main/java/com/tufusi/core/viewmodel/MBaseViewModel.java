package com.tufusi.core.viewmodel;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tufusi.core.IBaseModelListener;
import com.tufusi.core.model.MBaseModel;
import com.tufusi.core.model.PagingResult;

import java.util.List;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description ViewModel基类 用于实现 View 与 Model 的生命周期统一管理
 */
public class MBaseViewModel<M extends MBaseModel, D> extends ViewModel
        implements LifecycleObserver, IBaseModelListener<List<D>> {

    protected M model;

    /**
     * 和 LiveData 概念一样 都是用于：负责暂存数据，只是作用域不一样
     * LiveData 包裹被观察的数据实体类，可以用于指定某字段数据更新
     * MutableLiveData 则是作用在整个实体类或者数据类型变化才会通知下发，不会细节到某个字段
     */
    public MutableLiveData<ObservableList<D>> dataList = new MutableLiveData<>();
    public MutableLiveData<ViewStatus> viewStatusLiveData = new MutableLiveData<>();
    public MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public MBaseViewModel() {
        // 初始化数据源
        dataList.setValue(new ObservableArrayList<>());
        // 初始化加载动作
        viewStatusLiveData.setValue(ViewStatus.LOADING);
        // 初始化错误信息
        errorMessage.setValue("");
    }

    @Override
    public void onLoadSuccess(MBaseModel model, List<D> data, PagingResult... result) {
        // 确保子类传递进来的model 与 加载获得的 model 为同一个
        if (model == this.model) {
            // 判断 model 是否支持分页
            if (model.isPaging()) {
                // 判断 页面数据 是否为第一页
                if (result[0].isFirstPage()) {
                    // 清空数据
                    dataList.getValue().clear();
                }

                // 判断页面数据是否为空
                if (result[0].isEmpty()) {
                    // 判断 页面数据 是否为第一页
                    if (result[0].isFirstPage()) {
                        viewStatusLiveData.postValue(ViewStatus.EMPTY);
                    } else {
                        viewStatusLiveData.postValue(ViewStatus.NO_MORE_DATA);
                    }
                } else {
                    // 往观察数据对象里丢数据
                    dataList.getValue().addAll(data);
                    // 更新MutableLiveData对象
                    dataList.postValue(dataList.getValue());
                    // 更改 viewStatus
                    viewStatusLiveData.postValue(ViewStatus.DISPLAY_CONTENT);
                }
            } else {
                dataList.getValue().clear();
                dataList.getValue().addAll(data);
                dataList.postValue(dataList.getValue());
                viewStatusLiveData.postValue(ViewStatus.DISPLAY_CONTENT);
            }
        }
    }

    @Override
    public void onLoadFailure(MBaseModel model, String prompt, PagingResult... result) {
        errorMessage.setValue(prompt);

        // 分别展示 加载失败 以及 刷新失败
        if (model.isPaging() && !result[0].isFirstPage()) {
            viewStatusLiveData.setValue(ViewStatus.LOAD_MORE_FAILED);
        } else {
            viewStatusLiveData.setValue(ViewStatus.REFRESH_FAILED);
        }
    }

    @Override
    protected void onCleared() {
        if (model != null) {
            model.cancel();
        }
    }
}