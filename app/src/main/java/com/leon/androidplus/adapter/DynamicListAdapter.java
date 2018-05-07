package com.leon.androidplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.widget.DynamicItemView;

import java.util.List;

public class DynamicListAdapter extends BaseLoadingListAdapter<Answer> {

    public DynamicListAdapter(Context context) {
        this(context, null);
    }

    public DynamicListAdapter(Context context, List<Answer> list) {
        super(context);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new DynamicItemViewHolder(new DynamicItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((DynamicItemView)holder.itemView).bindView(getDataList().get(position));
    }

    public static class DynamicItemViewHolder extends RecyclerView.ViewHolder {

        public DynamicItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
