package com.wangshijia.www.multiscrollview;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.wangshijia.www.multiscrollview.adapter.ComFragmentAdapter;
import com.wangshijia.www.multiscrollview.adapter.ImageViewPagerAdapter;
import com.wangshijia.www.multiscrollview.fragments.Test2Fragment;
import com.wangshijia.www.multiscrollview.fragments.TestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.wangshijia.www.multiscrollview.refresh.RefreshLayout;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bannerPager)
    ViewPager bannerPager;
    @BindView(R.id.tab_vp_rank)
    TabLayout tabVpRank;
    @BindView(R.id.bottomPager)
    ViewPager bottomPager;
    @BindView(R.id.titleBackImg)
    ImageView titleBackImg;
    @BindView(R.id.titleBackBtn)
    RelativeLayout titleBackBtn;
    @BindView(R.id.centerTitle)
    TextView centerTitle;
    @BindView(R.id.rightText)
    TextView rightText;
    @BindView(R.id.smallTitleLayout)
    RelativeLayout smallTitleLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nestedScrollView)
    AdjustScrollView nestedScrollView;
    private int bannerPagerHeight;
    private int enableScrollHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBarTranslate();
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<Fragment> fragments = getFragments();
        ComFragmentAdapter comFragmentAdapter = new ComFragmentAdapter(getSupportFragmentManager(), fragments);
        bottomPager.setAdapter(comFragmentAdapter);
        tabVpRank.setupWithViewPager(bottomPager, false);

        ImageViewPagerAdapter bannerAdapter = new ImageViewPagerAdapter();
        bannerPager.setAdapter(bannerAdapter);

        intViewPagerHeight();
        initNestScrollView();
        initRefresh();
    }

    private void initRefresh() {


        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setEnableOverScrollBounce(false);
        refreshLayout.setEnableOverScrollDrag(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000, false);//传入false表示刷新失败
            }
        });

//        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
//        refreshLayout.addOnPullListener(new RefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefreshing(final RefreshLayout refreshLayout) {
//                refreshLayout.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        refreshLayout.setRefreshing(false);
//                    }
//                }, 2000);
//            }
//        });

    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
    }

    private void initNestScrollView() {
        final int color = 0x00ffffff;
        final int textColor = 0x00FF4081;
        int alpha = 0;
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY < enableScrollHeight) {
                    nestedScrollView.setEnableScroll(true);
                } else {
                    nestedScrollView.setEnableScroll(false);
                }

                int distance = Math.min(scrollY, enableScrollHeight);
                Log.e("TAG", "distance:: " + distance);
                Log.e("TAG", "scrollY:: " + scrollY);

                if (scrollY <= enableScrollHeight) {
                    toolbar.setBackgroundColor(((255 * distance / enableScrollHeight) << 24) | color);
                    centerTitle.setTextColor(((255 * distance / enableScrollHeight) << 24) | textColor);
                    rightText.setTextColor(((255 * distance / enableScrollHeight) << 24) | textColor);
                    int alphe = 255 * distance / enableScrollHeight;
                    titleBackImg.setImageAlpha(alphe);
                } else {
                    rightText.setTextColor(textColor);
                    centerTitle.setTextColor(textColor);
                    toolbar.setBackgroundColor(color);
                }

                if (scrollY >= enableScrollHeight / 2) {
                    titleBackImg.setImageResource(R.mipmap.back_black);
                } else {
                    titleBackImg.setImageResource(R.mipmap.back_white);
                }
            }
        });

        toolbar.setBackgroundColor(0);
        rightText.setTextColor(0);
        centerTitle.setTextColor(0);

    }

    /**
     * 隐藏状态栏
     */
    private void setStatusBarTranslate() {
        if (Build.VERSION.SDK_INT >= 23) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void intViewPagerHeight() {
        final int screenHeightPx = ScreenUtil.getScreenHeightPx(this);//1980
        final int statusBarHeight = ScreenUtil.getStatusBarHeight(this);//60
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                int toolbarHeight = toolbar.getHeight();//168
                int tabVpRankHeight = tabVpRank.getHeight();//132
                int vpHeight = screenHeightPx - toolbarHeight - tabVpRankHeight - statusBarHeight;
                int vpHeightWithTranslationBar = screenHeightPx - toolbarHeight - tabVpRankHeight;

//                enableScrollHeight = bannerPager.getHeight() - toolbarHeight - statusBarHeight;
                enableScrollHeight = bannerPager.getHeight() - toolbarHeight;

                Log.e("TAG", "bannerPagerHeight" + bannerPagerHeight);

                ViewGroup.LayoutParams layoutParams = bottomPager.getLayoutParams();
//                layoutParams.height = vpHeight;
                layoutParams.height = vpHeightWithTranslationBar;
                bottomPager.setLayoutParams(layoutParams);

            }
        });

    }

    private List<Fragment> getFragments() {
        TestFragment fragment1 = TestFragment.getInstance();
        Test2Fragment fragment2 = Test2Fragment.getInstance();
        TestFragment fragment3 = TestFragment.getInstance();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);

        return fragments;
    }
}
