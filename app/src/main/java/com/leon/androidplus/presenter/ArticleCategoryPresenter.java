package com.leon.androidplus.presenter;

import com.leon.androidplus.contract.ArticleCategoryContract;
import com.leon.androidplus.data.ArticleDataSource;
import com.leon.androidplus.data.LoadCallback;
import com.leon.androidplus.data.model.Article;

import java.util.List;

import javax.inject.Inject;

public class ArticleCategoryPresenter implements ArticleCategoryContract.Presenter {

    private ArticleCategoryContract.View mView;

    private ArticleDataSource mArticleDataSource;

    @Inject
    ArticleCategoryPresenter(ArticleDataSource articleDataSource) {
        mArticleDataSource = articleDataSource;
    }

    @Override
    public void takeView(ArticleCategoryContract.View view) {
        mView = view;
    }

    @Override
    public void dropView() {
        mView = null;
    }

    @Override
    public void loadArticleByTag(int tag) {
        mArticleDataSource.getArticlesByTag(tag, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
                if (mView != null) {
                    mView.onLoadArticleSuccess(list);
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadArticleFailed();
                }
            }
        });
    }

    @Override
    public void loadMoreArticleByTag(int tag) {
        mArticleDataSource.getMoreArticlesByTag(tag, new LoadCallback<Article>() {
            @Override
            public void onLoadSuccess(List<Article> list) {
                if (mView != null) {
                    mView.onLoadMoreArticleSuccess();
                }
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                if (mView != null) {
                    mView.onLoadMoreArticleFailed();
                }
            }
        });
    }
}
