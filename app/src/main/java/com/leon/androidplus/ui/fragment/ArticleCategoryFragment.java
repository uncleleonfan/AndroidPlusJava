package com.leon.androidplus.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.leon.androidplus.adapter.ArticleListAdapter;
import com.leon.androidplus.adapter.BaseLoadingListAdapter;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.contract.ArticleCategoryContract;
import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.presenter.ArticleCategoryPresenter;

import java.util.List;

import javax.inject.Inject;
public class ArticleCategoryFragment extends BaseRefreshableListFragment<Article>
        implements ArticleCategoryContract.View{

    @Inject
    ArticleCategoryPresenter mArticleCategoryPresenter;

    private int mTag;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTag = getArguments().getInt(Constant.ARGUMENT_TYPE, -1);
    }

    @Override
    protected BaseLoadingListAdapter<Article> onCreateAdapter() {
        return new ArticleListAdapter(getContext());
    }

    @Override
    protected void init() {
        super.init();
        mArticleCategoryPresenter.takeView(this);
        mArticleCategoryPresenter.loadArticleByTag(mTag);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mArticleCategoryPresenter.dropView();
    }

    @Override
    protected void startRefresh() {
        mArticleCategoryPresenter.loadArticleByTag(mTag);
    }

    @Override
    protected void startLoadMoreData() {
        mArticleCategoryPresenter.loadMoreArticleByTag(mTag);
    }

    public static ArticleCategoryFragment newInstance(int tag) {
        ArticleCategoryFragment articleCategoryFragment = new ArticleCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.ARGUMENT_TYPE, tag);
        articleCategoryFragment.setArguments(bundle);
        return articleCategoryFragment;
    }


    @Override
    public void onLoadArticleSuccess(List<Article> articleList) {
        mSwipeRefreshLayout.setRefreshing(false);
        getAdapter().replaceData(articleList);
    }

    @Override
    public void onLoadArticleFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        mError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadMoreArticleSuccess() {
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreArticleFailed() {

    }
}
