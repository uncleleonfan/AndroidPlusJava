package com.leon.androidplus.ui.fragment;

import android.view.View;

import com.leon.androidplus.adapter.BaseLoadingListAdapter;
import com.leon.androidplus.adapter.QuestionListAdapter;
import com.leon.androidplus.contract.RecentQuestionContract;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.di.FragmentScoped;
import com.leon.androidplus.presenter.RecentQuestionPresenter;

import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class QuestionFragment extends BaseRefreshableListFragment<Question> implements RecentQuestionContract.View{

    private static final String TAG = "QuestionFragment";

    @Inject
    RecentQuestionPresenter mQuestionPresenter;

    @Override
    public BaseLoadingListAdapter<Question> onCreateAdapter() {
        return new QuestionListAdapter(getContext());
    }

    @Override
    protected void init() {
        super.init();
        mQuestionPresenter.takeView(this);
        mQuestionPresenter.loadRecentQuestions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mQuestionPresenter.dropView();
    }

    @Override
    public void onLoadRecentQuestionSuccess(List<Question> list) {
        mError.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
        getAdapter().replaceData(list);
    }

    @Override
    public void onLoadRecentQuestionFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        mError.setVisibility(View.VISIBLE);
    }


    @Override
    protected void startRefresh() {
        mQuestionPresenter.loadRecentQuestions();
    }

    @Override
    protected void startLoadMoreData() {
        mQuestionPresenter.loadMoreRecentQuestions();
    }

    @Override
    public void onLoadMoreRecentQuestionFailed() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRecentQuestionSuccess() {
        getAdapter().notifyDataSetChanged();
    }
}
