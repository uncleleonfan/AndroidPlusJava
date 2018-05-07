package com.leon.androidplus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.widget.QuestionItemView;

import java.util.List;

public class QuestionListAdapter extends BaseLoadingListAdapter<Question> {


    public QuestionListAdapter(Context context) {
        super(context);
    }

    public QuestionListAdapter(Context context, List<Question> list) {
        super(context, list);
    }

    @Override
    RecyclerView.ViewHolder onCreateNormalViewHolder() {
        return new QuestionItemViewHolder(new QuestionItemView(getContext()));
    }

    @Override
    void onBindNormalViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((QuestionItemView)holder.itemView).bindView(getDataList().get(position));
    }

    private static class QuestionItemViewHolder extends RecyclerView.ViewHolder{

        public QuestionItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
