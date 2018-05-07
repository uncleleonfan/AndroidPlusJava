package com.leon.androidplus.ui.activity;

import com.avos.avoscloud.AVUser;
import com.leon.androidplus.R;
import com.leon.androidplus.data.model.User;

public class SplashActivity extends BaseActivity {

    private static final int DELAY_TIME = 2000;


    @Override
    public int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init() {
        super.init();
        //设置状态栏颜色为透明色
        setStatusBarTransparent();
        //获取当前用户
        User currentUser = AVUser.getCurrentUser(User.class);
        //如果当前用户为null,说明用户没有登陆
        if (currentUser == null) {
            //延时跳转到登陆界面
            navigateToLoginActivity();
        } else {
            //延时跳转到主界面
            navigateToMainActivity();
        }

        //Activity 转场动画，淡入淡出
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    /**
     * 延时跳转到主界面
     */
    private void navigateToMainActivity() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                //跳转到主界面
                navigateTo(MainActivity.class);
            }
        }, DELAY_TIME);
    }

    /**
     * 延时跳转到登陆界面
     */
    private void navigateToLoginActivity() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                //跳转到登陆界面
                navigateTo(LoginActivity.class);

            }
        }, DELAY_TIME);
    }

}
