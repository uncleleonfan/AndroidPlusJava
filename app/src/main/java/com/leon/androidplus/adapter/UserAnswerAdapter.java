package com.leon.androidplus.adapter;

import android.content.Context;
import android.view.View;

import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.widget.UserAnswerItemView;

import java.util.List;

public class UserAnswerAdapter extends BaseListAdapter<Answer>{


    public UserAnswerAdapter(Context context) {
        super(context);
    }

    public UserAnswerAdapter(Context context, List<Answer> dataList) {
        super(context, dataList);
    }

    @Override
    View onCreateItemView(int position) {
        return new UserAnswerItemView(getContext());
    }

    @Override
    void onBindItemView(View itemView, int position) {
        ((UserAnswerItemView)itemView).bindView(getDataList().get(position), position);
    }
}
