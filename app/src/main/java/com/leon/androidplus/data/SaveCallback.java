package com.leon.androidplus.data;

public interface SaveCallback {
    void onSaveSuccess();
    void onSaveFailed(String errorMsg);
}
