package com.leon.androidplus.ui.fragment;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.leon.androidplus.R;
import com.leon.androidplus.adapter.HomePagerAdapter;
import com.leon.androidplus.di.ActivityScoped;
import com.leon.androidplus.ui.activity.AddQuestionActivity;

import javax.inject.Inject;

@ActivityScoped
public class HomeFragment extends BaseMainFragment {

    @Inject
    public HomeFragment() {
    }


    @Override
    protected void init() {
        super.init();
        mToolBar.inflateMenu(R.menu.home_menu);
        mToolBar.setOnMenuItemClickListener(mOnMenuItemClickListener);
    }
    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Intent intent = new Intent(getContext(), AddQuestionActivity.class);
            startActivity(intent);
            return true;
        }
    };

    /**
     * @return ViewPager的适配器
     */
    @Override
    PagerAdapter getPagerAdapter() {
        return new HomePagerAdapter(getChildFragmentManager(),
                getResources().getStringArray(R.array.home_category));
    }
}
