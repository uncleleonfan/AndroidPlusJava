package com.leon.androidplus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.ui.fragment.ArticleCategoryFragment;

public class ArticlePagerAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;


    public ArticlePagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case Article.TAG_KOTLIN:
                return ArticleCategoryFragment.newInstance(Article.TAG_KOTLIN);
            case Article.TAG_SDK:
                return ArticleCategoryFragment.newInstance(Article.TAG_SDK);
            case Article.TAG_CUSTOM_VIEW:
                return ArticleCategoryFragment.newInstance(Article.TAG_CUSTOM_VIEW);
            case Article.TAG_THIRD_PARTY:
                return ArticleCategoryFragment.newInstance(Article.TAG_THIRD_PARTY);
            case Article.TAG_HOT:
                return ArticleCategoryFragment.newInstance(Article.TAG_HOT);
            case Article.TAG_PROJECT:
                return ArticleCategoryFragment.newInstance(Article.TAG_PROJECT);
            case Article.TAG_THINKING:
                return ArticleCategoryFragment.newInstance(Article.TAG_THINKING);
            case Article.TAG_INTERVIEW:
                return ArticleCategoryFragment.newInstance(Article.TAG_INTERVIEW);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
