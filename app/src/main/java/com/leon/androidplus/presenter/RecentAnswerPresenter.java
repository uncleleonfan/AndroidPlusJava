package com.leon.androidplus.presenter;

import com.leon.androidplus.contract.RecentAnswerContract;
import com.leon.androidplus.data.AnswerDataRepository;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.di.FragmentScoped;

import java.util.List;

import javax.inject.Inject;

@FragmentScoped
public class RecentAnswerPresenter implements RecentAnswerContract.Presenter {

    RecentAnswerContract.View mView;

    private final AnswerDataRepository mAnswerDataRepository;

    @Inject
    RecentAnswerPresenter(AnswerDataRepository answerDataRepository) {
        mAnswerDataRepository = answerDataRepository;
    }


    @Override
    public void takeView(RecentAnswerContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadRecentAnswerByQuestion(String questionId) {
        mAnswerDataRepository.getRecentAnswerListByQuestion(questionId, new LoadCallback<Answer>() {
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
    public void loadMoreRecentAnswerByQuestion(String questionId) {
        mAnswerDataRepository.getMoreRecentAnswerListByQuestion(questionId, new LoadCallback<Answer>(){
            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadMoreRecentAnswerFailed();
                }
            }

            @Override
            public void onLoadSuccess(List<Answer> list) {
                if (mView != null) {
                    mView.onLoadMoreRecentAnswerSuccess(list);
                }
            }
        });
    }
}
