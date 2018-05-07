package com.leon.androidplus.adapter;

import android.content.Context;
import android.view.View;

import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.widget.UserShareArticleItemView;

import java.util.List;

public class UserArticleListAdapter extends BaseListAdapter<Article>{


    public UserArticleListAdapter(Context context) {
        super(context);
    }

    public UserArticleListAdapter(Context context, List<Article> dataList) {
        super(context, dataList);
    }

    @Override
    View onCreateItemView(int position) {
        return new UserShareArticleItemView(getContext());
    }

    @Override
    void onBindItemView(View itemView, int position) {
        ((UserShareArticleItemView)itemView).bindView(getDataList().get(position));
    }
}
