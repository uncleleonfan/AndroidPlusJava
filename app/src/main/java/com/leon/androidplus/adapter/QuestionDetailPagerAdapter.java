package com.leon.androidplus.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.leon.androidplus.ui.fragment.HotAnswerFragment;
import com.leon.androidplus.ui.fragment.RecentAnswerFragment;

public class QuestionDetailPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles;
    private final String mQuestionId;

    public QuestionDetailPagerAdapter(FragmentManager fm, String titles[], String questionId) {
        super(fm);
        mTitles = titles;
        mQuestionId = questionId;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return RecentAnswerFragment.newInstance(mQuestionId);
        } else {
            return HotAnswerFragment.newInstance(mQuestionId);
        }
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
