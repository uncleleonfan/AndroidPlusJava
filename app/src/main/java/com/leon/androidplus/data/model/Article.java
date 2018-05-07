package com.leon.androidplus.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Article")
public class Article extends AVObject{

    public static final int TAG_HOT = 0;
    public static final int TAG_THINKING = 1;
    public static final int TAG_PROJECT = 2;
    public static final int TAG_SDK = 3;
    public static final int TAG_KOTLIN = 4;
    public static final int TAG_CUSTOM_VIEW = 5;
    public static final int TAG_THIRD_PARTY = 6;
    public static final int TAG_INTERVIEW = 7;

    public static final String TITLE = "title";
    public static final String DESC = "desc";
    public static final String URL = "url";
    public static final String TAG = "tag";
    public static final String USER = "user";
    public static final String FAVOUR_COUNT = "favour_count";
    public static final String USER_NAME = "user.username";
    public static final String USER_AVATAR = "user.avatar";

    public void setTitle(String title){
        put(TITLE, title);
    }

    public void setDesc(String desc) {
        put(DESC, desc);
    }

    public void setUrl(String url) {
        put(URL, url);
    }

    public void setTag(int tag) {
        put(TAG, tag);
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public String getDes() {
        return getString(DESC);
    }

    public String getUrl() {
        return getString(URL);
    }

    public void setUser(User user) {
        put(USER, user);
    }

    public User getUser() {
        return getAVUser(USER, User.class);
    }

    public void increaseFavourCount() {
        increment(FAVOUR_COUNT);
    }

    public void decreaseFavourCount() {
        increment(FAVOUR_COUNT, -1);
        saveInBackground();
    }

    public int getFavourCount() {
        return getInt(FAVOUR_COUNT);
    }

}
