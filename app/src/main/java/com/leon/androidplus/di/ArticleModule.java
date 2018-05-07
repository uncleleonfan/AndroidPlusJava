package com.leon.androidplus.di;

import com.leon.androidplus.ui.fragment.ArticleCategoryFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class ArticleModule {

    @ChildFragmentScope
    @ContributesAndroidInjector
    abstract ArticleCategoryFragment articleFragment();
}
