package com.leon.androidplus.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.leon.androidplus.R;

import butterknife.BindView;
public abstract class BaseMainFragment extends BaseFragment {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_base_main;
    }

    @Override
    protected void init() {
        super.init();
        mViewPager.setAdapter(getPagerAdapter());
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(getTabScrollMode());
    }

    abstract PagerAdapter getPagerAdapter();

    public int getTabScrollMode() {
        return TabLayout.MODE_FIXED;
    }
}
