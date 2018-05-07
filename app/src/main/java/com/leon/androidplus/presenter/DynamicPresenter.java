package com.leon.androidplus.presenter;

import com.leon.androidplus.contract.DynamicContract;
import com.leon.androidplus.data.AnswerDataSource;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.di.ChildFragmentScope;

import java.util.List;

import javax.inject.Inject;

@ChildFragmentScope
public class DynamicPresenter implements DynamicContract.Presenter {

    private DynamicContract.View mView;
    private AnswerDataSource mAnswerDataSource;

    @Inject
    public DynamicPresenter(AnswerDataSource dataRepository) {
        mAnswerDataSource = dataRepository;
    }

    @Override
    public void takeView(DynamicContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadRecentAnswer() {
        mAnswerDataSource.getRecentAnswerList(new LoadCallback<Answer>() {
            @Override
            public void onLoadSuccess(List<Answer> list) {
                if (mView != null) {
                    mView.onLoadRecentAnswerSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadRecentAnswerFailed();
                }
            }
        });
    }

    @Override
    public void loadMoreRecentAnswer() {
        mAnswerDataSource.getMoreRecentAnswerList(new LoadCallback<Answer>() {
            @Override
            public void onLoadSuccess(List<Answer> list) {
                mView.onLoadMoreRecentAnswerSuccess(list);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mView.onLoadMoreRecentAnswerFailed();
            }
        });
    }
}