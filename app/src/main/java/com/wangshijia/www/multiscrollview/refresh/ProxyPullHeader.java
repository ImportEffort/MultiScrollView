package com.wangshijia.www.multiscrollview.refresh;

import android.view.View;

import java.util.HashSet;

class ProxyPullHeader implements PullHeader {

    private HashSet<OnPullListener> listeners = new HashSet<>(3);

    private PullHeader reaPullHeader;

    public ProxyPullHeader(PullHeader reaPullHeader) {
        this.reaPullHeader = reaPullHeader;
    }

    public void setPullHandler(PullHeader pullHeader) {
        reaPullHeader = pullHeader;
    }

    @Override
    public View createHeaderView(RefreshLayout refreshLayout) {
        return reaPullHeader.createHeaderView(refreshLayout);
    }

    @Override
    public Config getConfig() {
        return reaPullHeader.getConfig();
    }

    @Override
    public void onPullBegin(RefreshLayout refreshLayout) {
        reaPullHeader.onPullBegin(refreshLayout);
        for (OnPullListener listener : listeners) {
            listener.onPullBegin(refreshLayout);
        }
    }

    @Override
    public void onPositionChange(RefreshLayout refreshLayout, int status, int dy, int currentDistance) {
        reaPullHeader.onPositionChange(refreshLayout, status, dy, currentDistance);
        for (OnPullListener listener : listeners) {
            listener.onPositionChange(refreshLayout, status, dy, currentDistance);
        }
    }

    @Override
    public void onRefreshing(RefreshLayout refreshLayout) {
        reaPullHeader.onRefreshing(refreshLayout);
        for (OnPullListener listener : listeners) {
            listener.onRefreshing(refreshLayout);
        }
    }

    @Override
    public void onReset(RefreshLayout refreshLayout, boolean pullRelease) {
        reaPullHeader.onReset(refreshLayout, pullRelease);
        for (OnPullListener listener : listeners) {
            listener.onReset(refreshLayout, pullRelease);
        }
    }

    @Override
    public void onPullRefreshComplete(RefreshLayout refreshLayout) {
        reaPullHeader.onPullRefreshComplete(refreshLayout);
        for (OnPullListener listener : listeners) {
            listener.onPullRefreshComplete(refreshLayout);
        }
    }

    public void addListener(OnPullListener onPullListener) {
        listeners.add(onPullListener);
    }

    public void removeListener(OnPullListener onPullListener) {
        listeners.remove(onPullListener);
    }
}
