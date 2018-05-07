package com.leon.androidplus.adapter;

import android.content.Context;
import android.view.View;

import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.widget.UserQuestionItemView;

import java.util.List;

public class UserQuestionListAdapter extends BaseListAdapter<Question>{

    public UserQuestionListAdapter(Context context) {
        super(context);
    }

    public UserQuestionListAdapter(Context context, List<Question> dataList) {
        super(context, dataList);
    }

    @Override
    View onCreateItemView(int position) {
        return new UserQuestionItemView(getContext());
    }

    @Override
    void onBindItemView(View itemView, int position) {
        ((UserQuestionItemView)itemView).bindView(getDataList().get(position), position);
    }
}
