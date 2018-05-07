package com.leon.androidplus.contract;

import com.leon.androidplus.data.model.Question;

import java.util.List;

public interface RecentQuestionContract {

    interface View extends BaseView {
        void onLoadRecentQuestionSuccess(List<Question> list);
        void onLoadRecentQuestionFailed();

        void onLoadMoreRecentQuestionSuccess();
        void onLoadMoreRecentQuestionFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadRecentQuestions();
        void loadMoreRecentQuestions();
    }
}
