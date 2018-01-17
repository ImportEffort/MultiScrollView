package com.wangshijia.www.multiscrollview.refresh;

/**
 * 下拉监听
 */
public interface OnPullListener {

    /**
     * 开始拖动
     */
    void onPullBegin(RefreshLayout refreshLayout);

    /**
     * 位置变化
     *
     * @param refreshLayout
     * @param status          状态 /没有任何操作
     *                        public final static byte PULL_STATUS_INIT = 1;
     *                        //开始下拉
     *                        public final static byte PULL_STATUS_TOUCH_MOVE = 2;
     *                        //回到原始位置
     *                        public final static byte PULL_STATUS_RESET = 3;
     *                        //刷新中
     *                        public final static byte PULL_STATUS_REFRESHING = 4;
     *                        //刷新完成
     *                        public final static byte PULL_STATUS_COMPLETE = 5;
     * @param dy              下拉事件的位移
     * @param currentDistance 当前位移的距离
     */
    void onPositionChange(RefreshLayout refreshLayout, int status, int dy, int currentDistance);

    /**
     * 刷新中
     */
    void onRefreshing(RefreshLayout refreshLayout);

    /**
     * 没有刷新的释放回去
     */
    void onReset(RefreshLayout refreshLayout, boolean pullRelease);

    /**
     * 设置刷新完成，并且释放回去
     */
    void onPullRefreshComplete(RefreshLayout refreshLayout);
}
