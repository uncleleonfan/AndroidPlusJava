package com.leon.androidplus.contract;

public interface LoginContract {

    interface View extends BaseView {
        void onLoginSuccess();//登陆成功
        void onLoginFailed();//登陆失败
        void onUserNamePasswordMismatch();//用户名和密码不匹配
        void onUserNameDoesNotExist();//用户名不存在
    }

    interface Presenter extends BasePresenter<View> {
        void login(String email, String password);//登陆
    }
}
