package com.wangshijia.www.multiscrollview.refresh;

import android.support.v4.view.ViewCompat;
import android.view.View;

public interface PullHeader extends OnPullListener {

    View createHeaderView(RefreshLayout refreshLayout);

    Config getConfig();

    abstract class Config {
        /**
         * 超出这个偏移量，松开手指就会触发刷新。
         */
        public abstract int offsetToRefresh(RefreshLayout refreshLayout, View headerView);

        /**
         * 显示刷新的位置的偏移量
         */
        public abstract int offsetToKeepHeaderWhileLoading(RefreshLayout refreshLayout, View headerView);

        /**
         * 刷新控件总共可以下拉拖动的距离
         */
        public abstract int totalDistance(RefreshLayout refreshLayout, View headerView);

        /**
         * headView 在布局中的偏移量
         */
        public abstract int headerViewLayoutOffset(RefreshLayout refreshLayout, View headerView);

        /**
         * contentView 是否可以向上滚动，用来判断是否可以下拉刷新，如果可以向上滚动就不做下拉刷新动作
         */
        public abstract boolean contentCanScrollUp(RefreshLayout refreshLayout, View contentView);

        /**
         * 拦截滑动事件
         *
         * @param refreshLayout
         * @param dy              触摸滑动的偏移量
         * @param currentDistance 当前的滑动的距离
         * @param totalDistance   总的可下拉距离
         * @return
         */
        public abstract int dispatchTouchMove(RefreshLayout refreshLayout, int dy, int currentDistance, int totalDistance);

    }

    class DefaultConfig extends Config {

        @Override
        public int offsetToRefresh(RefreshLayout refreshLayout, View headerView) {
            return (int) (headerView.getMeasuredHeight() * 1.2f);
        }

        @Override
        public int offsetToKeepHeaderWhileLoading(RefreshLayout refreshLayout, View headerView) {
            return headerView.getMeasuredHeight();
        }

        @Override
        public int totalDistance(RefreshLayout refreshLayout, View headerView) {
            return headerView.getMeasuredHeight() * 3;
        }

        @Override
        public int headerViewLayoutOffset(RefreshLayout refreshLayout, View headerView) {
            return headerView.getMeasuredHeight();
        }

        @Override
        public boolean contentCanScrollUp(RefreshLayout refreshLayout, View contentView) {
            return ViewCompat.canScrollVertically(contentView, -1);
        }

        @Override
        public int dispatchTouchMove(RefreshLayout refreshLayout, int dy, int currentDistance, int totalDistance) {
            float hDataY = dy / 2;
            float ps = (int) (-hDataY * Math.abs(currentDistance) / (float) totalDistance) - hDataY;
            float resultDy;
            resultDy = dy + ps * 0.9f;
            return (int) resultDy;
        }
    }
}
