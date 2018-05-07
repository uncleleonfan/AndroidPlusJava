package com.leon.androidplus.contract;

import com.leon.androidplus.data.model.Article;

import java.util.List;

public interface ArticleCategoryContract {

    interface View extends BaseView {
        void onLoadArticleSuccess(List<Article> articleList);
        void onLoadArticleFailed();
        void onLoadMoreArticleSuccess();
        void onLoadMoreArticleFailed();
    }

    interface Presenter extends BasePresenter<View> {
        void loadArticleByTag(int tag);
        void loadMoreArticleByTag(int tag);
    }

}
