package com.leon.androidplus.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;

import java.util.Arrays;

@AVClassName("UserAnswerFavourMap")
public class UserAnswerFavourMap extends AVObject {

    public static final String USER = "user";
    public static final String ANSWER = "answer";
    public static final String ANSWER_QUESTION = ANSWER +"." + Answer.QUESTION;
    public static final String ANSWER_USER = ANSWER +"." + Answer.USER;
    public static final String ANSWER_USER_USERNAME = ANSWER + "." + Answer.USER_NAME;
    public static final String ANSWER_QUESTION_TITLE = ANSWER + "." + Answer.QUESTION_TITLE;
    public static final String ANSWER_CONTENT = ANSWER + "." + Answer.CONTENT;
    public static final String ANSWER_LIKE_COUNT = ANSWER + "." + Answer.LIKE_COUNT;
    public static final String ANSWER_COMMENT_COUNT = ANSWER + "." + Answer.COMMENT_COUNT;

    public void setUser(User user) {
        put(USER, user);
    }

    public void setAnswer(Answer answer) {
        put(ANSWER, answer);
    }

    public static void buildFavourMap(User user, Answer answer) {
        user.addLikedAnswer(answer.getObjectId());
        answer.addLikeCount();
        try {
            User userWithOutData = AVObject.createWithoutData(User.class, user.getObjectId());
            Answer answerWithOutData = AVObject.createWithoutData(Answer.class, answer.getObjectId());
            UserAnswerFavourMap userAnswerFavourMap = new UserAnswerFavourMap();
            userAnswerFavourMap.setUser(userWithOutData);
            userAnswerFavourMap.setAnswer(answerWithOutData);
            userAnswerFavourMap.saveInBackground();
        } catch (AVException e) {
            e.printStackTrace();
        }

    }

    public static void breakFavourMap(User user, Answer answer) {
        user.removeLikedAnswer(answer.getObjectId());
        answer.minusLikeCount();
        AVQuery<UserAnswerFavourMap> userQuery = AVQuery.getQuery(UserAnswerFavourMap.class);
        userQuery.whereEqualTo(USER, user);
        AVQuery<UserAnswerFavourMap> answerQuery = AVQuery.getQuery(UserAnswerFavourMap.class);
        answerQuery.whereEqualTo(ANSWER, answer);
        AVQuery<UserAnswerFavourMap> query = AVQuery.and(Arrays.asList(userQuery, answerQuery));
        query.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {

            }
        });

    }
}
