package com.leon.androidplus.app;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.leon.androidplus.BuildConfig;
import com.leon.androidplus.MyEventBusIndex;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.data.model.Comment;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.data.model.UserAnswerFavourMap;
import com.leon.androidplus.data.model.UserArticleFavourMap;
import com.leon.androidplus.data.model.UserQuestionFavourMap;
import com.leon.androidplus.di.DaggerAppComponent;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.greenrobot.eventbus.EventBus;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class AndroidPlusApp extends DaggerApplication {

    private static final String APP_ID = "e23RXU0ywb3H2hfHgPbmAL3s-gzGzoHsz";
    private static final String APP_KEY = "Hy5zNJf0I4oOAwUA7k7sf1CN";

    @Override
    public void onCreate() {
        super.onCreate();
        //Init Lean Cloud
        initSubClasses();
        AVOSCloud.initialize(this, APP_ID, APP_KEY);
        AVOSCloud.setDebugLogEnabled(BuildConfig.DEBUG);

        EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();

        Beta.enableHotfix = false;
        Beta.autoDownloadOnWifi = true;
        Bugly.init(getApplicationContext(), "033d44667d", BuildConfig.DEBUG);
    }

    private void initSubClasses() {
        AVObject.registerSubclass(Comment.class);
        AVObject.registerSubclass(Question.class);
        AVObject.registerSubclass(Answer.class);
        AVObject.registerSubclass(Article.class);
        AVObject.registerSubclass(UserArticleFavourMap.class);
        AVObject.registerSubclass(UserQuestionFavourMap.class);
        AVObject.registerSubclass(UserAnswerFavourMap.class);
        AVObject.registerSubclass(User.class);
        AVUser.alwaysUseSubUserClass(User.class);
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.create();
    }
}
