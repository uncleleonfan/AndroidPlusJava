package com.leon.androidplus.ui.activity;

import android.view.View;

import com.avos.avoscloud.AVUser;
import com.leon.androidplus.R;
import com.leon.androidplus.adapter.BaseListAdapter;
import com.leon.androidplus.adapter.UserQuestionListAdapter;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.QuestionDataSource;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.utils.TransitionUtils;

import java.util.List;

import javax.inject.Inject;

public class UserQuestionsActivity extends BaseListViewActivity<Question> {

    @Inject
    QuestionDataSource mQuestionDataSource;
    private String mUserId;

    @Override
    protected void startRefresh() {
        mQuestionDataSource.refreshUserQuestions(mUserId, new LoadCallback<Question>(){
            @Override
            public void onLoadFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onLoadSuccess(List<Question> list) {
                mSwipeRefreshLayout.setRefreshing(false);
                getAdapter().replaceData(list);
            }
        });
    }

    @Override
    protected void startLoadMoreData() {
        mQuestionDataSource.getMoreUserQuestions(mUserId, new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errorMsg) {

            }
        });
    }

    private void initUser() {
        mUserId = getIntent().getStringExtra(Constant.EXTRA_USER_ID);
        if (mUserId == null) {
            mUserId = AVUser.getCurrentUser(User.class).getObjectId();
        }
        if (mUserId.equals(AVUser.getCurrentUser().getObjectId())) {
            setTitle(R.string.my_questions);
        } else {
            setTitle(R.string.his_questions);
        }
    }

    @Override
    protected void startLoadData() {
        initUser();
        mQuestionDataSource.getUserQuestions(mUserId, new LoadCallback<Question>() {
            @Override
            public void onLoadSuccess(List<Question> list) {
                mSwipeRefreshLayout.setRefreshing(false);
                getAdapter().replaceData(list);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    BaseListAdapter<Question> onCreateAdapter() {
        return new UserQuestionListAdapter(this);
    }

    @Override
    protected void onListItemClick(View view, int position) {
        Question question = getAdapter().getItem(position);
        View title = view.findViewById(R.id.question_title);
        View des = view.findViewById(R.id.question_description);
        TransitionUtils.transitionToQuestionDetail(this, question, title, des);
    }

}
