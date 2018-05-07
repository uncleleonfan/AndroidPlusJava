package com.leon.androidplus.ui.activity;

import android.view.View;

import com.avos.avoscloud.AVUser;
import com.leon.androidplus.R;
import com.leon.androidplus.adapter.BaseListAdapter;
import com.leon.androidplus.adapter.UserAnswerAdapter;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.AnswerDataSource;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.utils.TransitionUtils;

import java.util.List;

import javax.inject.Inject;

public class UserFavourAnswerActivity extends BaseListViewActivity<Answer> {

    @Inject
    AnswerDataSource mAnswerDataSource;
    private String mUserId;

    private void initUser() {
        mUserId = getIntent().getStringExtra(Constant.EXTRA_USER_ID);
        if (mUserId == null) {
            mUserId = AVUser.getCurrentUser(User.class).getObjectId();
        }
        if (mUserId.equals(AVUser.getCurrentUser().getObjectId())) {
            setTitle(R.string.my_favour_answers);
        } else {
            setTitle(R.string.his_favour_questions);
        }
    }

    @Override
    protected void startRefresh() {
        mAnswerDataSource.refreshUserFavouredAnswers(mUserId, new LoadCallback<Answer>() {
            @Override
            public void onLoadSuccess(List<Answer> list) {
                getAdapter().replaceData(list);
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void startLoadMoreData() {
        mAnswerDataSource.getMoreUserFavouredAnswers(mUserId, new LoadCallback<Answer>() {
            @Override
            public void onLoadSuccess(List<Answer> list) {
                getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onLoadFailed(String errorMsg) {

            }
        });
    }

    @Override
    protected void startLoadData() {
        initUser();
        mAnswerDataSource.getUserFavouredAnswers(mUserId, new LoadCallback<Answer>() {
            @Override
            public void onLoadSuccess(List<Answer> list) {
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
    BaseListAdapter<Answer> onCreateAdapter() {
        return new UserAnswerAdapter(this, null);
    }

    @Override
    protected void onListItemClick(View view, int position) {
        Answer answer = getAdapter().getItem(position);
        View titleView = view.findViewById(R.id.question_title);
        View answerView = view.findViewById(R.id.answer);
        TransitionUtils.transitionToAnswerDetail(this, answer, titleView, answerView);
    }
}
