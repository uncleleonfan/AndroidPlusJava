package com.leon.androidplus.di;

import com.leon.androidplus.ui.fragment.DynamicFragment;
import com.leon.androidplus.ui.fragment.HotQuestionFragment;
import com.leon.androidplus.ui.fragment.QuestionFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class HomeModule {

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract QuestionFragment questionFragment();

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract DynamicFragment dynamicFragment();

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract HotQuestionFragment hotFragment();
}
