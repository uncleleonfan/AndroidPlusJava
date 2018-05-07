package com.leon.androidplus.ui.activity;

import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.avos.avoscloud.feedback.FeedbackAgent;
import com.leon.androidplus.R;
import com.leon.androidplus.event.LogoutEvent;
import com.leon.androidplus.event.ScrollEvent;
import com.leon.androidplus.ui.fragment.ArticleFragment;
import com.leon.androidplus.ui.fragment.HomeFragment;
import com.leon.androidplus.ui.fragment.MeFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Lazy;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fragment_frame)
    FrameLayout mFragmentFrame;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView mBottomNavigation;

    @Inject
    Lazy<HomeFragment> homeFragmentProvider;

    @Inject
    Lazy<ArticleFragment> articleFragmentProvider;

    @Inject
    Lazy<MeFragment> meFragmentProvider;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();
        //监听底部导航条切换事件
        mBottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBottomNavigation.setSelectedItemId(R.id.main_home);
        EventBus.getDefault().register(this);

        //sync feedback
        FeedbackAgent feedbackAgent = new FeedbackAgent(this);
        feedbackAgent.sync();


/*        feedbackAgent.getDefaultThread().sync(new FeedbackThread.SyncCallback() {
            @Override
            public void onCommentsSend(List<Comment> list, AVException e) {

            }

            @Override
            public void onCommentsFetch(List<Comment> list, AVException e) {

            }
        });*/
    }

    //底部导航条切换监听器
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switchPage(item.getItemId());
            return true;
        }
    };

    /**
     * 切换页面
     * @param itemId 底部导航条菜单选项的id
     */
    private void switchPage(int itemId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (itemId) {
            case R.id.main_home:
                fragmentTransaction.replace(R.id.fragment_frame, homeFragmentProvider.get());
                break;
            case R.id.main_article:
                fragmentTransaction.replace(R.id.fragment_frame, articleFragmentProvider.get());
                break;
            case R.id.main_me:
                fragmentTransaction.replace(R.id.fragment_frame, meFragmentProvider.get());
                break;
        }
        fragmentTransaction.commit();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScrollChange(ScrollEvent scrollEvent) {
        if (scrollEvent.getDirection() == ScrollEvent.Direction.UP) {
            hideNavigationView();
        } else {
            showNavigationView();
        }
    }

    private void showNavigationView() {
        animationNavigationView(mBottomNavigation.getHeight(), 0);
    }


    private void hideNavigationView() {
        animationNavigationView(0, mBottomNavigation.getHeight());
    }

    private void animationNavigationView(float from ,float to) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mBottomNavigation, "translationY",
                from, to);
        objectAnimator.setDuration(500);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLogout(LogoutEvent logoutEvent) {
        finish();
    }
}
