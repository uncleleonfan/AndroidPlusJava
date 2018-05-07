package com.leon.androidplus.di;

import com.leon.androidplus.ui.fragment.HotAnswerFragment;
import com.leon.androidplus.ui.fragment.RecentAnswerFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class QuestionDetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    public abstract RecentAnswerFragment recentAnswerFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    public abstract HotAnswerFragment hotAnswerFragment();
}
