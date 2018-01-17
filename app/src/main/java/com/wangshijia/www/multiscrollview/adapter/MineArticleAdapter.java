package com.wangshijia.www.multiscrollview.adapter;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wangshijia.www.multiscrollview.R;
import com.wangshijia.www.multiscrollview.bean.MineArticleBean;

import java.util.List;


/**
 * @Created SiberiaDante
 * @Describe：
 * @CreateTime: 2017/12/15
 * @UpDateTime:
 * @Email: 2654828081@qq.com
 * @GitHub: https://github.com/SiberiaDante
 */

public class MineArticleAdapter extends BaseQuickAdapter<MineArticleBean,BaseViewHolder> {


    public MineArticleAdapter(@Nullable List<MineArticleBean> data) {
        super(R.layout.item_mine_article, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MineArticleBean item) {
        helper.setText(R.id.list_item,item.getContent());
    }
}
