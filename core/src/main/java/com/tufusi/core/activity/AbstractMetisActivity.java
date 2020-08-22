package com.tufusi.core.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.tufusi.core.R;
import com.tufusi.core.loadsir.EmptyCallback;
import com.tufusi.core.loadsir.ErrorCallback;
import com.tufusi.core.loadsir.LoadingCallback;
import com.tufusi.core.utils.ToastUtil;
import com.tufusi.core.viewmodel.MBaseViewModel;
import com.tufusi.core.viewmodel.ViewStatus;

import java.util.Observable;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description 墨提斯 Metis 巧智女神 -古希腊神话中第一代智慧女神 - 代表最原始的基类Activity
 */
public abstract class AbstractMetisActivity<V extends ViewDataBinding, VM extends MBaseViewModel>
        extends AppCompatActivity implements Observer {

    protected VM viewModel;
    protected V viewDataBinding;

    private LoadService mLoadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModel();
        performDataBinding();
        if (viewModel != null) {
            getLifecycle().addObserver(viewModel);
        }
    }

    private void performDataBinding() {
        //  生成数据绑定类的基类
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.viewModel = viewModel == null ? getViewModel() : viewModel;

        // 判断是否需要在 Binding 类中设置变量
        if (getBindingVariable() > 0) {
            // Set a value value in the Binding class.
            // 参数 variableId：变量的BR id值；(类似于R.id.XX)
            // 参数 value：变量值（可以是整个实体对象）
            viewDataBinding.setVariable(getBindingVariable(), viewModel);
        }
        // 强制执行绑定数据
        viewDataBinding.executePendingBindings();
    }

    private void initViewModel() {
        viewModel = getViewModel();
    }

    public void setLoadSir(View view) {
        mLoadService = LoadSir.getDefault()
                .register(view, new Callback.OnReloadListener() {
                    @Override
                    public void onReload(View v) {
                        onRetryBtnClick();
                    }
                });
    }

    /**
     * 尝试重新刷新
     */
    protected void onRetryBtnClick() {
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  覆写方法 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    @Override
    public void onChanged(Object o) {
        if (o instanceof ViewStatus && mLoadService != null) {
            switch ((ViewStatus) o) {
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case EMPTY:
                    mLoadService.showCallback(EmptyCallback.class);
                    break;
                case DISPLAY_CONTENT:
                    mLoadService.showSuccess();
                    break;
                case NO_MORE_DATA:
                    ToastUtil.show(getString(R.string.no_more_data));
                    break;
                case REFRESH_FAILED:
                    if (((ObservableArrayList) viewModel.dataList.getValue()).size() == 0) {
                        mLoadService.showCallback(ErrorCallback.class);
                    } else {
                        ToastUtil.show(viewModel.errorMessage.getValue().toString());
                    }
                    break;
                case LOAD_MORE_FAILED:
                    ToastUtil.show(viewModel.errorMessage.getValue().toString());
                    break;
                default:
                    break;
            }
        }
    }


    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  ABSTRACT ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    /**
     * 获取ViewModel对象
     *
     * @return 返回具体 ViewModel
     */
    protected abstract VM getViewModel();

    /**
     * 获取布局
     *
     * @return 布局资源ID
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * @return
     */
    protected abstract int getBindingVariable();

}