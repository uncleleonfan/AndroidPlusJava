package com.leon.androidplus.di;

import com.leon.androidplus.ui.fragment.ArticleFragment;
import com.leon.androidplus.ui.fragment.HomeFragment;
import com.leon.androidplus.ui.fragment.MeFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainModule {

    @FragmentScoped
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment homeFragment();

    @FragmentScoped
    @ContributesAndroidInjector(modules = ArticleModule.class)
    abstract ArticleFragment articleFragment();

    @FragmentScoped
    @ContributesAndroidInjector
    abstract MeFragment meFragment();
}
