package com.leon.androidplus.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;

import java.util.Arrays;

@AVClassName("UserQuestionFavourMap")
public class UserQuestionFavourMap extends AVObject {

    public static final String USER = "user";
    public static final String QUESTION = "question";

    public void setUser(User user) {
        put(USER, user);
    }

    public void setQuestion(Question question) {
        put(QUESTION, question);
    }

    public static void buildFavourMap(User user, Question question) {
        //增加问题被喜欢的计数
        question.addFavourCount();
        //将问题加入用户喜欢的问题列表
        user.addFavourQuestion(question.getObjectId());
        try {
            User userWithoutData = AVObject.createWithoutData(User.class, user.getObjectId());
            Question questionWithoutData = AVObject.createWithoutData(Question.class, question.getObjectId());
            UserQuestionFavourMap userQuestionFavourMap = new UserQuestionFavourMap();
            userQuestionFavourMap.put(USER, userWithoutData);
            userQuestionFavourMap.put(QUESTION, questionWithoutData);
            userQuestionFavourMap.saveInBackground();
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    public static void breakFavourMap(User user, Question question) {
        question.minusFavourCount();
        user.removeFavouredQuestion(question.getObjectId());
        AVQuery<UserQuestionFavourMap> userQuery = AVQuery.getQuery(UserQuestionFavourMap.class);
        userQuery.whereEqualTo(USER, user);
        AVQuery<UserQuestionFavourMap> questionQuery = AVQuery.getQuery(UserQuestionFavourMap.class);
        questionQuery.whereEqualTo(QUESTION, question);
        AVQuery<UserQuestionFavourMap> query = AVQuery.and(Arrays.asList(userQuery, questionQuery));
        query.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                }
            }
        });
    }

}

