package com.leon.androidplus.ui.activity;

import android.content.Intent;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.leon.androidplus.R;
import com.leon.androidplus.adapter.BaseListAdapter;
import com.leon.androidplus.adapter.UserArticleListAdapter;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.ArticleDataSource;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.data.model.User;

import java.util.List;

import javax.inject.Inject;

public class UserFavourArticleActivity extends BaseListViewActivity<Article> {

    @Inject
    ArticleDataSource mArticleDataSource;
    private String mUserId;

    private void initUser() {
        mUserId = getIntent().getStringExtra(Constant.EXTRA_USER_ID);
        if (mUserId == null) {
            mUserId = AVUser.getCurrentUser(User.class).getObjectId();
        }
        if (mUserId.equals(AVUser.getCurrentUser().getObjectId())) {
            setTitle(R.string.my_favour_articles);
        } else {
            setTitle(R.string.his_favour_articles);
        }
    }

    @Override
    protected void startRefresh() {
        mArticleDataSource.refreshUserFavouredArticles(mUserId, new LoadCallback<Article>(){
            @Override
            public void onLoadFailed(String errorMsg) {
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onLoadSuccess(List<Article> list) {
                mSwipeRefreshLayout.setRefreshing(false);
                getAdapter().replaceData(list);
            }
        });
    }

    @Override
    protected void startLoadMoreData() {
        mArticleDataSource.getMoreUserFavouredArticles(mUserId, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
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
        mArticleDataSource.getUserFavouredArticles(mUserId, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
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
    BaseListAdapter<Article> onCreateAdapter() {
        return new UserArticleListAdapter(this, null);
    }

    @Override
    protected void onListItemClick(View view, int position) {
        Intent intent = new Intent(this, ArticleDetailActivity.class);
        Article article = getAdapter().getItem(position);
        intent.putExtra(Constant.EXTRA_ARTICLE, article.toString());
        startActivity(intent);
    }
}
