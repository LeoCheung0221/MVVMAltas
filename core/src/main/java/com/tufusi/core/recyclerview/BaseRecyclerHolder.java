package com.tufusi.core.recyclerview;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tufusi.core.customeview.BaseCustomViewModel;
import com.tufusi.core.customeview.ICustomView;

/**
 * Created by 鼠夏目 on 2020/8/26.
 *
 * @author 鼠夏目
 * @description 实现基类Holder
 */
public class BaseRecyclerHolder extends RecyclerView.ViewHolder {

    private ICustomView mView;

    public BaseRecyclerHolder(@NonNull ICustomView itemView) {
        super((View) itemView);
        this.mView = itemView;
    }

    public void bind(@NonNull BaseCustomViewModel item) {
        mView.setData(item);
    }
}