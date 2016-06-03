package com.broil.support.widget.refresh;

public abstract class MaterialRefreshListener {
    public void onFinish(){}
    public abstract void onRefresh(MaterialRefreshLayout materialRefreshLayout);
    public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout){}
}
