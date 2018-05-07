package com.leon.androidplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.widget.ArticleItemView;

import java.util.List;

public class ArticleListAdapter extends BaseLoadingListAdapter<Article> {

    public ArticleListAdapter(Context context) {
        this(context, null);
    }

    public ArticleListAdapter(Context context, List<Article> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new ArticleItemViewHolder(new ArticleItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ArticleItemView)holder.itemView).bindView(getDataList().get(position));
    }

    public static class ArticleItemViewHolder extends RecyclerView.ViewHolder{

        public ArticleItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
