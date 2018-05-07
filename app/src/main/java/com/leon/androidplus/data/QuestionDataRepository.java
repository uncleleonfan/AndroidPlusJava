package com.leon.androidplus.data;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.data.model.UserArticleFavourMap;
import com.leon.androidplus.data.model.UserQuestionFavourMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class QuestionDataRepository implements QuestionDataSource {

    private List<Question> mRecentQuestionListCache;

    private List<Question> mHotQuestionListCache;

    private List<Question> mUserQuestionListCache;

    private List<UserQuestionFavourMap> mUserQuestionFavourMaps;

    private List<Question> mUserFavouredQuestionsCache;

    @Inject
    public QuestionDataRepository() {
    }

    @Override
    public void getRecentQuestionList(final LoadCallback<Question> callback) {
        getRecentQuestionListFromServer(callback, false);
    }

    private void getRecentQuestionListFromServer(final LoadCallback<Question> callback, final boolean isLoadMore) {
        //获取查询对象
        final AVQuery<Question> questionAVQuery = AVObject.getQuery(Question.class);
        questionAVQuery.limit(Constant.DEFAULT_PAGE_SIZE)//限制结果个数，默认10条数据
                .include(Question.USER)//结果包含提问用户数据
                .selectKeys(Arrays.asList(Question.TITLE,
                        Question.DESC, Question.FAVOUR_COUNT, Question.ANSWER_COUNT,
                        Question.USER_NAME, Question.USER_AVATAR))//过滤用户数据字段
                .orderByDescending(AVObject.CREATED_AT);//按创建时间降序

        //查询更多时的处理
        if (isLoadMore) {
            //查询数据的创建时间小于已有数据列表最后一条数据的创建时间
            questionAVQuery.whereLessThan(AVObject.CREATED_AT, getLastQuestionCreateAt());
        }
        //开始查询
        questionAVQuery.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> list, AVException e) {
                if (e == null) {
                    if (isLoadMore) {
                        mRecentQuestionListCache.addAll(list);
                    } else {
                        mRecentQuestionListCache = list;
                    }
                    callback.onLoadSuccess(list);
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMoreRecentQuestionList(final LoadCallback<Question> callback) {
        getRecentQuestionListFromServer(callback, true);
    }

    @Override
    public void getHotQuestionList(final LoadCallback<Question> callback) {
        getHotQuestionListFromServer(callback, false);
    }

    private void getHotQuestionListFromServer(final LoadCallback<Question> callback, final boolean isLoadMore) {
        final AVQuery<Question> questionAVQuery = AVObject.getQuery(Question.class);
        questionAVQuery.limit(Constant.DEFAULT_PAGE_SIZE).include(Question.USER)
        .selectKeys(Arrays.asList(Question.USER_AVATAR, Question.USER_NAME, Question.TITLE, Question.DESC, Question.FAVOUR_COUNT, Question.ANSWER_COUNT))
                .orderByDescending(Question.FAVOUR_COUNT)
                .addDescendingOrder(Question.CREATED_AT)
                .whereGreaterThan(Question.FAVOUR_COUNT, 0);
        if (isLoadMore) {
            questionAVQuery.whereLessThan(Question.CREATED_AT, getLastHotQuestionCreatedAt());
        }
        questionAVQuery.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> list, AVException e) {
                if (e == null) {
                    if (isLoadMore) {
                        mHotQuestionListCache.addAll(list);
                    } else {
                        mHotQuestionListCache = list;
                    }
                    callback.onLoadSuccess(list);
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMoreHotQuestionList(final LoadCallback<Question> callback) {
        getHotQuestionListFromServer(callback, true);
    }

    private Date getLastQuestionCreateAt() {
        return mRecentQuestionListCache.get(mRecentQuestionListCache.size() - 1).getCreatedAt();
    }


    public Date getLastHotQuestionCreatedAt() {
        return mHotQuestionListCache.get(mHotQuestionListCache.size() - 1).getCreatedAt();
    }

    @Override
    public void getUserQuestions(String userId, final LoadCallback<Question> callback) {
        getUserQuestionsFromServer(userId, callback, false);
    }

    private void getUserQuestionsFromServer(String userId, final LoadCallback<Question> callback, final boolean isLoadMore) {
        AVQuery<Question> questionAVQuery = AVQuery.getQuery(Question.class);
        try {
            User user = AVUser.createWithoutData(User.class, userId);
            questionAVQuery.whereEqualTo(Question.USER, user)
                    .orderByDescending(Question.CREATED_AT)
                    .limit(Constant.DEFAULT_PAGE_SIZE);
            if (isLoadMore) {
                questionAVQuery.whereLessThan(Question.CREATED_AT, getLastUserQuestionCreatedAt());
            }
            questionAVQuery.findInBackground(new FindCallback<Question>() {
                @Override
                public void done(List<Question> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserQuestionListCache.addAll(list);
                        } else {
                            mUserQuestionListCache = list;
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
    public void getMoreUserQuestions(String userId, final LoadCallback<Question> callback) {
        getUserQuestionsFromServer(userId, callback, true);
    }

    public Date getLastUserQuestionCreatedAt() {
        return mUserQuestionListCache.get(mUserQuestionListCache.size() - 1).getCreatedAt();
    }

    @Override
    public void refreshUserQuestions(String userId, LoadCallback<Question> callback) {
        mUserQuestionListCache.clear();
        mUserQuestionListCache = null;
        getUserQuestions(userId, callback);
    }

    @Override
    public void getUserFavouredQuestions(String userId, LoadCallback<Question> callback) {
        getUserFavouredQuestionsFromServer(userId, callback, false);
    }

    @Override
    public void getMoreUserFavouredQuestions(String userId, LoadCallback<Question> callback) {
        getUserFavouredQuestionsFromServer(userId, callback, true);
    }


    private void getUserFavouredQuestionsFromServer(String userId, final LoadCallback<Question> callback, final boolean isLoadMore) {
        final AVQuery<UserQuestionFavourMap> query = AVQuery.getQuery(UserQuestionFavourMap.class);
        try {
            AVObject avObject = AVObject.createWithoutData(User.class, userId);
            query.whereEqualTo(UserQuestionFavourMap.USER, avObject)
                    .include(UserQuestionFavourMap.QUESTION)
                    .limit(Constant.DEFAULT_PAGE_SIZE)
                    .orderByDescending(UserQuestionFavourMap.CREATED_AT);
            if (isLoadMore) {
                query.whereLessThan(UserArticleFavourMap.CREATED_AT, getLastUserQuestionFavourMapCreatedAt());
            }
            query.findInBackground(new FindCallback<UserQuestionFavourMap>() {
                @Override
                public void done(List<UserQuestionFavourMap> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserQuestionFavourMaps.addAll(list);
                        } else {
                            mUserQuestionFavourMaps = list;
                            mUserFavouredQuestionsCache = new ArrayList<>();
                        }
                        for (int i = 0; i < list.size(); i++) {
                            try {
                                Question question = list.get(i).getAVObject(UserQuestionFavourMap.QUESTION, Question.class);
                                mUserFavouredQuestionsCache.add(question);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                callback.onLoadFailed(e1.getLocalizedMessage());
                            }
                        }
                        callback.onLoadSuccess(mUserFavouredQuestionsCache);
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

    public Date getLastUserQuestionFavourMapCreatedAt() {
        return mUserFavouredQuestionsCache.get(mUserFavouredQuestionsCache.size() - 1).getCreatedAt();
    }

    @Override
    public void refreshUserFavouredQuestions(String userId, LoadCallback<Question> callback) {
        if (mUserFavouredQuestionsCache != null) {
            mUserFavouredQuestionsCache.clear();
            mUserFavouredQuestionsCache = null;
        }
        getUserFavouredQuestions(userId, callback);
    }

    @Override
    public void addQuestion(String title, String des, final SaveCallback callback) {
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(des);
        User user = AVUser.getCurrentUser(User.class);
        question.setUser(user);
        question.saveInBackground(new com.avos.avoscloud.SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    callback.onSaveSuccess();
                } else {
                    callback.onSaveFailed(e.getLocalizedMessage());
                }
            }
        });
    }
}
