package com.leon.androidplus.contract;

import com.leon.androidplus.data.model.Question;

import java.util.List;

public interface HotQuestionContract {

    interface View extends BaseView {
        void onLoadHotQuestionSuccess(List<Question> list);
        void onLoadHotQuestionFailed();

        void onLoadMoreHotQuestionSuccess();
        void onLoadMoreHotQuestionFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadHotQuestions();
        void loadMoreHotQuestions();
    }
}
