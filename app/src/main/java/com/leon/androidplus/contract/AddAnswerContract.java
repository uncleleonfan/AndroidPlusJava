package com.leon.androidplus.contract;

import com.leon.androidplus.data.model.Question;

public interface AddAnswerContract {

    interface View extends BaseView {
        void onPublishAnswerSuccess();
        void onPublishAnswerFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void publishAnswer(String answer, Question question);
    }
}
