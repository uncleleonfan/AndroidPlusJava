package com.leon.androidplus.data;

import android.support.annotation.Nullable;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.data.model.UserAnswerFavourMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AnswerDataRepository implements AnswerDataSource {

    private List<Answer> mRecentAnswerListCache;

    private List<Answer> mHotAnswerListCache;

    private List<Answer> mUserAnswerListCache;

    private List<UserAnswerFavourMap> mUserAnswerFavourMaps;

    private List<Answer> mMyFavouredAnswerCache;

    @Inject
    public AnswerDataRepository() {
    }

    @Nullable
    private Question getQuestionWithoutData(String questionId) {
        Question question = null;
        try {
            question = AVObject.createWithoutData(Question.class, questionId);
        } catch (AVException e) {
            e.printStackTrace();
        }
        return question;
    }

    @Override
    public void addAnswerToQuestion(String answerString, final Question question, final SaveCallback callback) {
        Answer answer = new Answer();
        answer.setAnswer(answerString);
        User currentUser = AVUser.getCurrentUser(User.class);
        answer.setUser(currentUser);//设置回答者
        Question dependent = getQuestionWithoutData(question.getObjectId());
        answer.setQuestion(dependent);//设置回答的问题
        answer.saveInBackground(new com.avos.avoscloud.SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    question.addAnswerCount();//增加问题的回答计数
                    callback.onSaveSuccess();
                } else {
                    callback.onSaveFailed(e.getLocalizedMessage());
                }
            }
        });
    }


    private Date getLastRecentAnswerCreatedAt() {
        return mRecentAnswerListCache.get(mRecentAnswerListCache.size() - 1).getCreatedAt();
    }

    @Override
    public void getRecentAnswerListByQuestion(final String questionId, final LoadCallback<Answer> callback) {
        getAnswerListByQuestionFromServer(questionId, callback, false);
    }

    public void getAnswerListByQuestionFromServer(String questionId, final LoadCallback<Answer> callback, final boolean isLoadMore) {
        final AVQuery<Answer> answerAVQuery = AVObject.getQuery(Answer.class);
        Question question = getQuestionWithoutData(questionId);
        answerAVQuery.limit(Constant.DEFAULT_PAGE_SIZE)
                .include(Answer.USER)
                .include(Answer.QUESTION)
                .selectKeys(Arrays.asList(Answer.CONTENT,
                        Answer.LIKE_COUNT,
                        Answer.COMMENT_COUNT,
                        Answer.USER_NAME,
                        Answer.USER_AVATAR,
                        Answer.QUESTION_TITLE))
                .whereEqualTo(Answer.QUESTION, question)
                .orderByDescending(AVObject.CREATED_AT);
        if (isLoadMore) {
            answerAVQuery.whereLessThan(AVObject.CREATED_AT, getLastRecentAnswerCreatedAt());
        }
        answerAVQuery.findInBackground(new FindCallback<Answer>() {
            @Override
            public void done(List<Answer> list, AVException e) {
                if (e == null) {
                    if (isLoadMore) {
                        mRecentAnswerListCache.addAll(list);
                    } else {
                        mRecentAnswerListCache = list;
                    }
                    callback.onLoadSuccess(list);
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMoreRecentAnswerListByQuestion(final String questionId, final LoadCallback<Answer> callback) {
        getAnswerListByQuestionFromServer(questionId, callback, true);
    }

    @Override
    public void getRecentAnswerList(final LoadCallback<Answer> callback) {
        getRecentAnswerListFromServer(callback, false);
    }

    private void getRecentAnswerListFromServer(final LoadCallback<Answer> callback, final boolean isLoadMore) {
        final AVQuery<Answer> answerAVQuery = AVObject.getQuery(Answer.class);
        answerAVQuery.limit(Constant.DEFAULT_PAGE_SIZE)
                .include(Answer.USER)
                .include(Answer.QUESTION)
                .selectKeys(Arrays.asList(Answer.USER_NAME, Answer.QUESTION_TITLE,
                        Answer.LIKE_COUNT, Answer.COMMENT_COUNT, Answer.CONTENT))
                .orderByDescending(AVObject.CREATED_AT);
        if (isLoadMore) {
            answerAVQuery.whereLessThan(Answer.CREATED_AT, getLastRecentAnswerCreatedAt());
        }
        answerAVQuery.findInBackground(new FindCallback<Answer>() {
            @Override
            public void done(List<Answer> list, AVException e) {
                if (e == null) {
                    if (isLoadMore) {
                        mRecentAnswerListCache.addAll(list);
                    } else {
                        mRecentAnswerListCache = list;
                    }
                    callback.onLoadSuccess(list);
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMoreRecentAnswerList(final LoadCallback<Answer> callback) {
        getRecentAnswerListFromServer(callback, true);
    }


    @Override
    public void getHotAnswerListByQuestion(String questionId, final LoadCallback<Answer> callback) {
        getHotAnswerListByQuestionFromServer(questionId, callback, false);
    }

    private void getHotAnswerListByQuestionFromServer(String questionId, final LoadCallback<Answer> callback, final boolean isLoadMore) {
        final AVQuery<Answer> answerAVQuery = AVObject.getQuery(Answer.class);
        Question question = getQuestionWithoutData(questionId);
        answerAVQuery.limit(Constant.DEFAULT_PAGE_SIZE).include(Answer.USER).include(Answer.QUESTION)
                .selectKeys(Arrays.asList(Answer.USER_NAME, Answer.USER_AVATAR,
                        Answer.LIKE_COUNT, Answer.COMMENT_COUNT, Answer.CONTENT, Answer.QUESTION_TITLE))
                .whereEqualTo(Answer.QUESTION, question)
                .whereGreaterThan(Answer.LIKE_COUNT, 0)
                .orderByDescending(Answer.LIKE_COUNT)
                .addDescendingOrder(AVObject.CREATED_AT);
        if (isLoadMore) {
            answerAVQuery.whereLessThan(AVObject.CREATED_AT, getLastHotAnswerCreatedAt());
        }
        answerAVQuery.findInBackground(new FindCallback<Answer>() {
            @Override
            public void done(List<Answer> list, AVException e) {
                if (e == null) {
                    if (isLoadMore) {
                        mHotAnswerListCache.addAll(list);
                    } else {
                        mHotAnswerListCache = list;
                    }
                    callback.onLoadSuccess(list);
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMoreHotAnswerListByQuestion(String questionId, final LoadCallback<Answer> callback) {
        getHotAnswerListByQuestionFromServer(questionId, callback, true);
    }

    public Date getLastHotAnswerCreatedAt() {
        return mHotAnswerListCache.get(mHotAnswerListCache.size() - 1).getCreatedAt();
    }


    @Override
    public void getUserAnswerList(String userId, final LoadCallback<Answer> callback) {
        getUserAnswerListFromServer(userId, callback, false);
    }

    private void getUserAnswerListFromServer(String userId, final LoadCallback<Answer> callback, final boolean isLoadMore) {
        AVQuery<Answer> query = AVObject.getQuery(Answer.class);
        try {
            User user = User.createWithoutData(User.class, userId);
            query.orderByDescending(Answer.CREATED_AT)
                    .include(Answer.QUESTION)
                    .include(Answer.USER)
                    .selectKeys(Arrays.asList(Answer.QUESTION_TITLE, Answer.CONTENT, Answer.LIKE_COUNT, Answer.COMMENT_COUNT, Answer.USER_NAME))
                    .whereEqualTo(Answer.USER, user)
                    .limit(Constant.DEFAULT_PAGE_SIZE);
            if (isLoadMore) {
                query.whereLessThan(Answer.CREATED_AT, getLastUserAnswerCreatedAt());
            }
            query.findInBackground(new FindCallback<Answer>() {
                @Override
                public void done(List<Answer> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserAnswerListCache.addAll(list);
                        } else {
                            mUserAnswerListCache = list;
                        }
                        callback.onLoadSuccess(list);
                    } else {
                        callback.onLoadFailed(e.getLocalizedMessage());
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMoreUserAnswerList(String userId, final LoadCallback<Answer> callback) {
        getUserAnswerListFromServer(userId, callback, true);
    }

    public Date getLastUserAnswerCreatedAt() {
        return mUserAnswerListCache.get(mUserAnswerListCache.size() - 1).getCreatedAt();
    }

    @Override
    public void refreshUserAnswerList(String userId, LoadCallback<Answer> callback) {
        if (mUserAnswerListCache != null) {
            mUserAnswerListCache.clear();
            mUserAnswerListCache = null;
        }

        getUserAnswerList(userId, callback);
    }

    @Override
    public void getUserFavouredAnswers(String userId, LoadCallback<Answer> callback) {
        getUserFavouredAnswersFromServer(userId, callback, false);
    }

    @Override
    public void getMoreUserFavouredAnswers(String userId, LoadCallback<Answer> callback) {
        getUserFavouredAnswersFromServer(userId, callback, true);
    }

    @Override
    public void refreshUserFavouredAnswers(String userId, LoadCallback<Answer> callback) {
        if (mUserAnswerListCache != null) {
            mUserAnswerListCache.clear();
            mUserAnswerListCache = null;
        }
        getUserFavouredAnswers(userId, callback);
    }


    private void getUserFavouredAnswersFromServer(String userId, final LoadCallback<Answer> callback, final boolean isLoadMore) {
        final AVQuery<UserAnswerFavourMap> query = AVQuery.getQuery(UserAnswerFavourMap.class);
        try {
            AVObject avObject = AVObject.createWithoutData(User.class, userId);
            query.whereEqualTo(UserAnswerFavourMap.USER, avObject)
                    .include(UserAnswerFavourMap.ANSWER_USER)
                    .include(UserAnswerFavourMap.ANSWER)
                    .include(UserAnswerFavourMap.ANSWER_QUESTION)
                    .selectKeys(Arrays.asList(UserAnswerFavourMap.ANSWER_COMMENT_COUNT,
                            UserAnswerFavourMap.ANSWER_USER_USERNAME,
                            UserAnswerFavourMap.ANSWER_CONTENT,
                            UserAnswerFavourMap.ANSWER_LIKE_COUNT,
                            UserAnswerFavourMap.ANSWER_QUESTION_TITLE))
                    .limit(Constant.DEFAULT_PAGE_SIZE)
                    .orderByDescending(UserAnswerFavourMap.CREATED_AT);
            if (isLoadMore) {
                query.whereLessThan(UserAnswerFavourMap.CREATED_AT, getLastUserAnswerFavourMapCreatedAt());
            }
            query.findInBackground(new FindCallback<UserAnswerFavourMap>() {
                @Override
                public void done(List<UserAnswerFavourMap> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserAnswerFavourMaps.addAll(list);
                        } else {
                            mUserAnswerFavourMaps = list;
                            mMyFavouredAnswerCache = new ArrayList<>();
                        }
                        for (int i = 0; i < list.size(); i++) {
                            try {
                                Answer answer = list.get(i).getAVObject(UserAnswerFavourMap.ANSWER, Answer.class);
                                mMyFavouredAnswerCache.add(answer);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                callback.onLoadFailed(e1.getLocalizedMessage());
                            }
                        }
                        callback.onLoadSuccess(mMyFavouredAnswerCache);
                    } else {
                        callback.onLoadFailed(e.getLocalizedMessage());
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
            callback.onLoadFailed(e.getLocalizedMessage());
        }
    }

    public Date getLastUserAnswerFavourMapCreatedAt() {
        return mUserAnswerFavourMaps.get(mUserAnswerFavourMaps.size() - 1).getCreatedAt();
    }
}
