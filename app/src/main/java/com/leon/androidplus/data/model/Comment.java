package com.leon.androidplus.data.model;
import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

@AVClassName("Comment")
public class Comment extends AVObject{

    public static final String CONTENT = "content";
    public static final String ANSWER = "answer";
    public static final String USER = "user";

    public static final String USER_NAME = "user.username";
    public static final String USER_AVATAR = "user.avatar";


    public Comment() {
    }

    public void setContent(String comment) {
        put(CONTENT, comment);
    }

    public String getContent() {return getString(CONTENT);}

    public void setAnswer(Answer answer) {
        put(ANSWER, answer);
    }

    public Answer getAnswer() {
        return getAVObject(ANSWER);
    }

    public void setUser(User user) {
        put(USER, user);
    }

    public User getUser() {
        return getAVUser(USER, User.class);
    }
}
