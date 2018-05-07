package com.leon.androidplus.presenter;

import com.leon.androidplus.contract.AddQuestionContract;
import com.leon.androidplus.data.QuestionDataSource;
import com.leon.androidplus.data.SaveCallback;
import com.leon.androidplus.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class AddQuestionPresenter implements AddQuestionContract.Presenter {

    private AddQuestionContract.View mView;
    private QuestionDataSource mQuestionDataSource;


    @Inject
    public AddQuestionPresenter(QuestionDataSource questionDataSource) {
        mQuestionDataSource = questionDataSource;
    }

    @Override
    public void takeView(AddQuestionContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void publishQuestion(String title, String des) {
        mQuestionDataSource.addQuestion(title, des, new SaveCallback() {
            @Override
            public void onSaveSuccess() {
                mView.onPublishSuccess();
            }

            @Override
            public void onSaveFailed(String errorMsg) {
                mView.onPublishFailed();
            }
        });
    }
}
