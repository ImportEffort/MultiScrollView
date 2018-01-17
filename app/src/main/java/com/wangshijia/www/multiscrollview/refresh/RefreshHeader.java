package com.wangshijia.www.multiscrollview.refresh;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangshijia.www.multiscrollview.R;

/**
 * Created by dingboyang on 2017/3/3.
 */

public class RefreshHeader implements PullHeader{

    private ImageView imageView;
    private TextView textView;
    private MaterialProgressDrawable materialProgressDrawable;
    private ImageView progressImageView;
    private RotateAnimation mFlipAnimation;
    private RotateAnimation mReverseFlipAnimation;
    private int mRotateAniTime = 150;
    private View headerView;
//    private int backgroundColor = Color.parseColor("#989898");
//
//    public void setBackgroundColor(int color) {
//        backgroundColor = color;
//        if (headerView != null) {
//            headerView.setBackgroundColor(color);
//        }
//    }

    @Override
    public View createHeaderView(RefreshLayout refreshLayout) {
        Context context = refreshLayout.getContext();
        headerView = LayoutInflater.from(context).inflate(R.layout.view_refresh_header, refreshLayout, false);
        imageView = (ImageView) headerView.findViewById(R.id.refresh_defaultheader_imageView);
        textView = (TextView) headerView.findViewById(R.id.refresh_defaultheader_textView);
        progressImageView = (ImageView) headerView.findViewById(R.id.refresh_defaultheader_progress_imageView);

        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(mRotateAniTime);
        mFlipAnimation.setFillAfter(true);

        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(mRotateAniTime);
        mReverseFlipAnimation.setFillAfter(true);

        imageView.setAnimation(mFlipAnimation);

        materialProgressDrawable = new MaterialProgressDrawable(context, progressImageView);
        materialProgressDrawable.setColorSchemeColors(0xFFFF7700);
        materialProgressDrawable.setAlpha(255);
        progressImageView.setImageDrawable(materialProgressDrawable);
        return headerView;
    }

    @Override
    public void onPullBegin(RefreshLayout refreshLayout) {
        progressImageView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        textView.setText(getResources().getString(R.string.refreshview_pull_down_to_refresh));
        isDownArrow = true;
    }

    private boolean isDownArrow = true;

    @Override
    public void onPositionChange(RefreshLayout refreshLayout, int status, int dy, int currentDistance) {
        int offsetToRefresh = getConfig().offsetToRefresh(refreshLayout, headerView);
        if (status == RefreshLayout.PULL_STATUS_TOUCH_MOVE) {
            if (currentDistance < offsetToRefresh) {
                if (!isDownArrow) {
                    textView.setText(getResources().getString(R.string.refreshview_pull_down_to_refresh));
                    imageView.clearAnimation();
                    imageView.startAnimation(mReverseFlipAnimation);
                    isDownArrow = true;
                }
            } else {
                if (isDownArrow) {
                    textView.setText(getResources().getString(R.string.refreshview_release_to_refresh));
                    imageView.clearAnimation();
                    imageView.startAnimation(mFlipAnimation);
                    isDownArrow = false;
                }
            }
        }
    }

    @Override
    public void onRefreshing(RefreshLayout refreshLayout) {
        textView.setText(getResources().getString(R.string.refreshview_refreshing));
        imageView.clearAnimation();
        materialProgressDrawable.start();
        imageView.setVisibility(View.GONE);
        progressImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onReset(RefreshLayout refreshLayout, boolean pullRelease) {
        textView.setText(getResources().getString(R.string.refreshview_pull_down_to_refresh));
        materialProgressDrawable.stop();
        imageView.setVisibility(View.GONE);
        progressImageView.setVisibility(View.GONE);
        imageView.clearAnimation();
    }

    @Override
    public void onPullRefreshComplete(RefreshLayout refreshLayout) {
        textView.setText(getResources().getString(R.string.refreshview_complete));
        materialProgressDrawable.stop();
        imageView.setVisibility(View.GONE);
        progressImageView.setVisibility(View.GONE);
        imageView.clearAnimation();
    }

    private Resources getResources() {
        return headerView.getResources();
    }


    @Override
    public Config getConfig() {
        return config;
    }

    private DefaultConfig config = new DefaultConfig() {
        @Override
        public int offsetToRefresh(RefreshLayout refreshLayout, View headerView) {
            return (int) (headerView.getMeasuredHeight() / 3 * 1.2f);
        }

        @Override
        public int offsetToKeepHeaderWhileLoading(RefreshLayout refreshLayout, View headerView) {
            return headerView.getMeasuredHeight() / 3;
        }

        @Override
        public int totalDistance(RefreshLayout refreshLayout, View headerView) {
            return headerView.getMeasuredHeight();
        }
    };
}

