package com.leon.androidplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.widget.AnswerItemView;

import java.util.List;

public class AnswerListAdapter extends BaseLoadingListAdapter<Answer> {

    public AnswerListAdapter(Context context) {
        this(context, null);
    }

    public AnswerListAdapter(Context context, List<Answer> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new AnswerItemViewHolder(new AnswerItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AnswerItemView)holder.itemView).bindView(getDataList().get(position));
    }

    private static class AnswerItemViewHolder extends RecyclerView.ViewHolder{
        public AnswerItemViewHolder(View itemView) {
            super(itemView);
        }
    }


}
