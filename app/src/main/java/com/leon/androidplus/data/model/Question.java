package com.leon.androidplus.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Question")
public class Question extends AVObject {

    public static final String FAVOUR_COUNT = "favour_count";

    public static final String ANSWER_COUNT = "answer_count";

    public static final String TITLE = "title";

    public static final String DESC = "desc";

    public static final String USER = "user";

    public static final String USER_NAME = "user.username";

    public static final String USER_AVATAR = "user.avatar";

    public void setTitle(String title) {
        put(TITLE, title);
    }

    public void setDescription(String desc) {
        put(DESC, desc);
    }

    public String getTitle() {
        return getString(TITLE);
    }

    public String getDescription() {
        return getString(DESC);
    }


    public void addAnswerCount() {
        increment(ANSWER_COUNT);
        saveInBackground();
    }

    public int getAnswerCount() {
        return getInt(ANSWER_COUNT);
    }

    public void addFavourCount() {
        increment(FAVOUR_COUNT);
        saveInBackground();
    }

    public void minusFavourCount() {
        increment(FAVOUR_COUNT, -1);
        saveInBackground();
    }

    public int getFavourCount() {
        return getInt(FAVOUR_COUNT);
    }

    public void setUser(User dependent) {
        put(USER, dependent);
    }

    public User getUser() {
        return getAVUser(USER, User.class);
    }

}
