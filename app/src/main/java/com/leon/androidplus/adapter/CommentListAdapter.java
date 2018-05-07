package com.leon.androidplus.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leon.androidplus.data.model.Comment;
import com.leon.androidplus.widget.CommentItemView;
import com.leon.androidplus.widget.LoadingView;

import java.util.List;

public class CommentListAdapter extends BaseLoadingListAdapter<Comment> {


    public CommentListAdapter(Context context) {
        super(context);
    }

    public CommentListAdapter(Context context, List<Comment> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new CommentListItemViewHolder(new CommentItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((CommentItemView)holder.itemView).bindView(getDataList().get(position));
    }

    public static class CommentListItemViewHolder extends RecyclerView.ViewHolder {

        public CommentListItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    protected LoadingViewHolder onCreateLoadingViewHolder() {
        return new LoadingViewHolder(new LoadingView(getContext()));
    }
}
