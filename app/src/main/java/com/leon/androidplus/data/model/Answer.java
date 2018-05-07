package com.leon.androidplus.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Answer")
public class Answer extends AVObject {

    public static final String CONTENT = "content";

    public static final String USER = "user";

    public static final String USER_NAME = "user.username";

    public static final String USER_AVATAR = "user.avatar";

    public static final String QUESTION = "question";

    public static final String LIKE_COUNT = "like_count";

    public static final String COMMENT_COUNT = "comment_count";

    public static final String QUESTION_TITLE = "question.title";

    public void setAnswer(String answer) {
        put(CONTENT, answer);
    }

    public void setQuestion(Question question) {
        put(QUESTION, question);
    }

    public Question getQuestion() {
        return getAVObject(QUESTION);
    }

    public void setUser(User user) {
        put(USER, user);
    }

    public User getUser() {
        return getAVUser(USER, User.class);
    }

    public String getContent() {
        return getString(CONTENT);
    }

    public void addLikeCount() {
        increment(LIKE_COUNT);
        saveInBackground();
    }

    public void addCommentCount() {
        increment(COMMENT_COUNT);
    }

    public void minusLikeCount() {
        increment(LIKE_COUNT, -1);
        saveInBackground();
    }

    public int getLikeCount() {
        return getInt(LIKE_COUNT);
    }

    public int getCommentCount() {
        return getInt(COMMENT_COUNT);
    }
}
