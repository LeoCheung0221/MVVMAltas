package com.tufusi.core.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by 鼠夏目 on 2020/8/26.
 *
 * @author 鼠夏目
 * @description
 */
public abstract class BaseCustomView<V extends ViewDataBinding, VM extends BaseCustomViewModel> extends LinearLayout
        implements ICustomView<VM>, View.OnClickListener {

    private V dataBinding;
    private VM viewModel;
    private ICustomViewActionListener mActionListener;

    public BaseCustomView(Context context) {
        this(context, null);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BaseCustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (getViewLayoutId() != 0) {
            dataBinding = DataBindingUtil.inflate(inflater, getViewLayoutId(), this, false);
            dataBinding.getRoot().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mActionListener != null) {
                        mActionListener.onAction(ICustomViewActionListener.ACTION_ROOT_VIEW_CLICKED, v, viewModel);
                    }
                    onRootClick(v);
                }
            });
            addView(dataBinding.getRoot());
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void setData(VM data) {
        viewModel = data;
        setDataAttach2View(viewModel);
        if (dataBinding != null) {
            dataBinding.executePendingBindings();
        }
        onUpdateData();
    }

    protected void onUpdateData() {

    }

    @Override
    public void setStyle(int resId) {
    }

    @Override
    public void setActionListener(ICustomViewActionListener listener) {
        mActionListener = listener;
    }

    @Override
    public View getRootView() {
        return dataBinding.getRoot();
    }

    protected V getDataBinding() {
        return dataBinding;
    }

    protected VM getViewModel() {
        return viewModel;
    }

    protected abstract void setDataAttach2View(VM data);

    protected abstract int getViewLayoutId();

    protected abstract void onRootClick(View view);
}