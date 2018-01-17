package com.wangshijia.www.multiscrollview;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wangshijia on 2018/1/12.
 */

public class AdjustScrollView extends NestedScrollView {
    private int mLastXIntercept = 0;
    private int mLastYIntercept = 0;
    private boolean isEnableScroll = true;

    public AdjustScrollView(Context context) {
        super(context);
    }

    public AdjustScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AdjustScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                super.onInterceptTouchEvent(ev);
                break;
            }
            case MotionEvent.ACTION_MOVE: {

                int deltaX = x - mLastXIntercept;
                //纵坐标位移增量
                int deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    intercepted = false;
                } else {
                    intercepted = isEnableScroll;
                }

                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
            default:
                break;
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }

    public boolean isEnableScroll() {
        return isEnableScroll;
    }

    public void setEnableScroll(boolean enableScroll) {
        isEnableScroll = enableScroll;
    }

    @Override
    public void requestChildFocus(View child, View focused) {
    }
}
