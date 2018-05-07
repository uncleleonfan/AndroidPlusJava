package com.leon.androidplus.di;

import com.leon.androidplus.ui.activity.AddAnswerActivity;
import com.leon.androidplus.ui.activity.AddQuestionActivity;
import com.leon.androidplus.ui.activity.AnswerDetailActivity;
import com.leon.androidplus.ui.activity.ArticleDetailActivity;
import com.leon.androidplus.ui.activity.CommentActivity;
import com.leon.androidplus.ui.activity.LoginActivity;
import com.leon.androidplus.ui.activity.MainActivity;
import com.leon.androidplus.ui.activity.UserFavourAnswerActivity;
import com.leon.androidplus.ui.activity.UserFavourArticleActivity;
import com.leon.androidplus.ui.activity.UserFavourQuestionActivity;
import com.leon.androidplus.ui.activity.SettingsActivity;
import com.leon.androidplus.ui.activity.UserAnswerActivity;
import com.leon.androidplus.ui.activity.UserQuestionsActivity;
import com.leon.androidplus.ui.activity.UserShareArticleActivity;
import com.leon.androidplus.ui.activity.ProfileActivity;
import com.leon.androidplus.ui.activity.QuestionDetailActivity;
import com.leon.androidplus.ui.activity.RegisterActivity;
import com.leon.androidplus.ui.activity.ShareArticleActivity;
import com.leon.androidplus.ui.activity.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SplashActivity splashActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract LoginActivity loginActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract RegisterActivity registerActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MainModule.class)
    abstract MainActivity mainActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract AddQuestionActivity addQuestionActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = QuestionDetailModule.class)
    abstract QuestionDetailActivity questionDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract AddAnswerActivity addAnswerActivity();


    @ActivityScoped
    @ContributesAndroidInjector
    abstract AnswerDetailActivity answerDetailActivity();


    @ActivityScoped
    @ContributesAndroidInjector
    abstract CommentActivity commentActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ShareArticleActivity shareArticleActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ArticleDetailActivity articleDetailActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract ProfileActivity profileActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserShareArticleActivity userShareActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserQuestionsActivity userQuestionActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserAnswerActivity userAnswerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserFavourArticleActivity myFavourArticleActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserFavourQuestionActivity myFavourQuestionActivity();


    @ActivityScoped
    @ContributesAndroidInjector
    abstract UserFavourAnswerActivity myFavourAnswerActivity();

    @ActivityScoped
    @ContributesAndroidInjector
    abstract SettingsActivity settingsActivity();

}
