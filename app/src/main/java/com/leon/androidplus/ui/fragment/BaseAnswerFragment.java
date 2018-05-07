package com.leon.androidplus.ui.fragment;

import com.leon.androidplus.adapter.AnswerListAdapter;
import com.leon.androidplus.adapter.BaseLoadingListAdapter;
import com.leon.androidplus.data.model.Answer;

public abstract class BaseAnswerFragment extends BaseRefreshableListFragment<Answer> {

    @Override
    protected BaseLoadingListAdapter<Answer> onCreateAdapter() {
        return new AnswerListAdapter(getContext());
    }

    @Override
    public boolean isEnableScrollEvent() {
        return false;
    }

}
