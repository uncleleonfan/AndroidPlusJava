package com.leon.androidplus.contract;

public interface RegisterContract {

    interface View extends BaseView {
        void onRegisterSuccess();
        void onRegisterFailed();
        void onUserNameTaken();
    }

    interface Presenter extends BasePresenter<View> {
        void register(String email, String password);
    }
}
