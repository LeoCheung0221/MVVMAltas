package com.tufusi.core.model;

/**
 * Created by 鼠夏目 on 2020/8/22.
 *
 * @author 鼠夏目
 * @description 页面结果对象
 */
public class PagingResult {

    private boolean isEmpty;
    private boolean isFirstPage;
    private boolean hasNextPage;

    public PagingResult(boolean isEmpty, boolean isFirstPage, boolean hasNextPage) {
        this.isEmpty = isEmpty;
        this.isFirstPage = isFirstPage;
        this.hasNextPage = hasNextPage;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }
}