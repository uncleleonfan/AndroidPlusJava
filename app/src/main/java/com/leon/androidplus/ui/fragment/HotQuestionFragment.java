package com.leon.androidplus.ui.fragment;

import com.leon.androidplus.adapter.BaseLoadingListAdapter;
import com.leon.androidplus.adapter.QuestionListAdapter;
import com.leon.androidplus.contract.HotQuestionContract;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.di.FragmentScoped;
import com.leon.androidplus.presenter.HotQuestionPresenter;

import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class HotQuestionFragment extends BaseRefreshableListFragment<Question> implements HotQuestionContract.View{

    @Inject
    HotQuestionPresenter mHotQuestionPresenter;

    @Override
    public BaseLoadingListAdapter onCreateAdapter() {
        return new QuestionListAdapter(getContext(), null);
    }

    @Override
    protected void init() {
        super.init();
        mHotQuestionPresenter.takeView(this);
        mSwipeRefreshLayout.setRefreshing(true);
        mHotQuestionPresenter.loadHotQuestions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHotQuestionPresenter.dropView();
    }

    @Override
    protected void startRefresh() {
        mHotQuestionPresenter.loadHotQuestions();
    }

    @Override
    protected void startLoadMoreData() {
        mHotQuestionPresenter.loadMoreHotQuestions();
    }

    @Override
    public void onLoadHotQuestionSuccess(List<Question> list) {
        mSwipeRefreshLayout.setRefreshing(false);
        getAdapter().replaceData(list);
    }

    @Override
    public void onLoadHotQuestionFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoadMoreHotQuestionSuccess() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreHotQuestionFailed() {

    }
}
