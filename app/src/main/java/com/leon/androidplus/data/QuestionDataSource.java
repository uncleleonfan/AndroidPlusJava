package com.leon.androidplus.data;

import com.leon.androidplus.data.model.Question;

public interface QuestionDataSource {

    void getRecentQuestionList(LoadCallback<Question> callback);

    void getMoreRecentQuestionList(LoadCallback<Question> callback);

    void getHotQuestionList(LoadCallback<Question> callback);

    void getMoreHotQuestionList(LoadCallback<Question> callback);

    void getUserQuestions(String userId, LoadCallback<Question> callback);

    void getMoreUserQuestions(String userId, LoadCallback<Question> callback);

    void refreshUserQuestions(String userId, LoadCallback<Question> callback);

    void getUserFavouredQuestions(String userId, LoadCallback<Question> callback);

    void getMoreUserFavouredQuestions(String userId, LoadCallback<Question> callback);

    void refreshUserFavouredQuestions(String userId, LoadCallback<Question> callback);

    void addQuestion(String title, String des, SaveCallback callback);

}
