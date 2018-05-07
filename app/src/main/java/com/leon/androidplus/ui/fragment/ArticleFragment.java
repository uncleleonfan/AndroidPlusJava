package com.leon.androidplus.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;

import com.leon.androidplus.R;
import com.leon.androidplus.adapter.ArticlePagerAdapter;
import com.leon.androidplus.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class ArticleFragment extends BaseMainFragment {

    @Inject
    public ArticleFragment() {}

    @Override
    public int getTabScrollMode() {
        return TabLayout.MODE_SCROLLABLE;
    }

    @Override
    PagerAdapter getPagerAdapter() {
        return new ArticlePagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.article_category));
    }
}
