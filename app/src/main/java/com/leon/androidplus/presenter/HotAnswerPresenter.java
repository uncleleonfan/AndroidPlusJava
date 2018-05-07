package com.leon.androidplus.presenter;

import com.leon.androidplus.contract.HotAnswerContract;
import com.leon.androidplus.data.AnswerDataRepository;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.di.ActivityScoped;

import java.util.List;

import javax.inject.Inject;

@ActivityScoped
public class HotAnswerPresenter implements HotAnswerContract.Presenter {


    private HotAnswerContract.View mView;
    private AnswerDataRepository mDataRepository;

    @Inject
    public HotAnswerPresenter(AnswerDataRepository dataRepository) {
        mDataRepository = dataRepository;
    }


    @Override
    public void takeView(HotAnswerContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadHotAnswerByQuestion(String questionId) {
        mDataRepository.getHotAnswerListByQuestion(questionId, new LoadCallback<Answer>() {
            @Override
            public void onLoadSuccess(List<Answer> list) {
                if (mView != null) {
                    mView.onLoadHotAnswerSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadHotAnswerFailed();
                }
            }
        });
    }

    @Override
    public void loadMoreHotAnswerByQuestion(String questionId) {
        mDataRepository.getMoreHotAnswerListByQuestion(questionId, new LoadCallback<Answer>() {
            @Override
            public void onLoadSuccess(List<Answer> list) {
                if (mView != null) {
                    mView.onLoadMoreHotAnswerSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadMoreHotAnswerFailed();
                }
            }
        });
    }
}