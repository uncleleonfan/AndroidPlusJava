package com.leon.androidplus.presenter;

import com.leon.androidplus.contract.CommentContract;
import com.leon.androidplus.data.CommentDataSource;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.SaveCallback;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.Comment;
import com.leon.androidplus.di.ActivityScoped;

import java.util.List;

import javax.inject.Inject;

@ActivityScoped
public class CommentPresenter implements CommentContract.Presenter {

    private CommentContract.View mView;

    private CommentDataSource mDataRepository;

    @Inject
    public CommentPresenter(CommentDataSource dataRepository) {
        mDataRepository = dataRepository;
    }

    @Override
    public void takeView(CommentContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadComments(String answerId) {
        mDataRepository.loadComments(answerId, new LoadCallback<Comment>() {
            @Override
            public void onLoadSuccess(List<Comment> list) {
                mView.onLoadCommentsSuccess(list);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mView.onLoadCommentFailed();
            }
        });
    }

    @Override
    public void sendComment(Answer answer, String replayTo, String comment) {
        mDataRepository.sendComment(answer, replayTo, comment, new SaveCallback() {
            @Override
            public void onSaveSuccess() {
                mView.onSendCommentSuccess();
            }

            @Override
            public void onSaveFailed(String errorMsg) {
                mView.onSendCommentFailed();
            }
        });
    }

    @Override
    public void loadMoreComments(String answerId) {
        mDataRepository.loadMoreComments(answerId, new LoadCallback<Comment>(){
            @Override
            public void onLoadSuccess(List<Comment> list) {
                mView.onLoadMoreCommentsSuccess();
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mView.onLoadMoreCommentFailed();
            }
        });
    }
}
