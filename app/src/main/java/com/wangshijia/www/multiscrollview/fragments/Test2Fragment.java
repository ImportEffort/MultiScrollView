package com.wangshijia.www.multiscrollview.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wangshijia.www.multiscrollview.R;
import com.wangshijia.www.multiscrollview.adapter.MineArticleAdapter;
import com.wangshijia.www.multiscrollview.bean.MineArticleBean;

import java.util.ArrayList;
import java.util.List;

public class Test2Fragment extends Fragment {

    RecyclerView recyclerView;
    private MineArticleAdapter adapter;

    public static Test2Fragment getInstance() {
        return new Test2Fragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);

        final List<MineArticleBean> data = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            final MineArticleBean articleBean = new MineArticleBean();
            articleBean.setContent("使用 NestedScrollView + ViewPager + RecyclerView + SmartRefreshLayout解决各种滑动冲突" + i);

            data.add(articleBean);
        }


        adapter = new MineArticleAdapter(data);
        recyclerView.setAdapter(adapter);
        adapter.setEnableLoadMore(false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
