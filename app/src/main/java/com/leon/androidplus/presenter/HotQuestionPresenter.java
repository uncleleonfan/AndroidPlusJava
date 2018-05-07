package com.leon.androidplus.presenter;

import com.leon.androidplus.contract.HotQuestionContract;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.QuestionDataSource;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.di.ChildFragmentScope;

import java.util.List;

import javax.inject.Inject;

@ChildFragmentScope
public class HotQuestionPresenter implements HotQuestionContract.Presenter {

    private QuestionDataSource mDataRepository;
    private HotQuestionContract.View mView;

    @Inject
    public HotQuestionPresenter(QuestionDataSource dataRepository) {
        mDataRepository = dataRepository;
    }

    @Override
    public void takeView(HotQuestionContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadHotQuestions() {
        mDataRepository.getHotQuestionList(new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                if (mView != null) {
                    mView.onLoadHotQuestionSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadHotQuestionFailed();
                }
            }
        });
    }

    @Override
    public void loadMoreHotQuestions() {
        mDataRepository.getMoreHotQuestionList(new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                if (mView != null) {
                    mView.onLoadMoreHotQuestionSuccess();
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadMoreHotQuestionFailed();
                }
            }
        });
    }
}
