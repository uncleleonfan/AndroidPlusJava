package com.leon.androidplus.contract;

public interface AddQuestionContract {


    interface View extends BaseView {
        void onPublishSuccess();
        void onPublishFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void publishQuestion(String title, String des);
    }
}
