package com.leon.androidplus.data;

import android.util.SparseArray;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.data.model.UserArticleFavourMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ArticleDataRepository implements ArticleDataSource {


    private SparseArray<List<Article>> mArticleByTagCache = new SparseArray<>();

    private List<Article> mArticlesCache;

    private List<Article> mUserFavouredArticlesCache;

    private List<UserArticleFavourMap> mUserArticleFavourMaps;

    @Inject
    public ArticleDataRepository() {
    }

    @Override
    public void saveArticle(Article article, final SaveCallback callback) {
        article.saveInBackground(new com.avos.avoscloud.SaveCallback() {
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

    @Override
    public void getArticlesByTag(int tagId, LoadCallback<Article> callback) {
        getArticlesFromServer(tagId, callback, false);
    }

    private void getArticlesFromServer(final int tagId, final LoadCallback<Article> callback, final boolean isLoadMore) {
        //获取文章查询对象
        final AVQuery<Article> articleAVQuery = AVQuery.getQuery(Article.class);
        //配置查询条件
        articleAVQuery.include(Article.USER)
                .selectKeys(Arrays.asList(Article.TITLE, Article.DESC,
                        Article.FAVOUR_COUNT, Article.TAG, Article.URL,
                        Article.USER_NAME, Article.USER_AVATAR))
                .limit(Constant.DEFAULT_PAGE_SIZE);
        //如果是热门标签，则按文章点赞数排序，以创建时间为第二排序条件，并且点赞次数大于0
        if (tagId == Article.TAG_HOT) {
            articleAVQuery.orderByDescending(Article.FAVOUR_COUNT)
                    .addDescendingOrder(Article.CREATED_AT)
                    .whereGreaterThan(Article.FAVOUR_COUNT, 0);
        } else {//其他标签，以文章创建时间排序
            articleAVQuery.whereEqualTo(Article.TAG, tagId)
                    .orderByDescending(Article.CREATED_AT);
        }
        //加载更多数据的处理
        if (isLoadMore) {
            articleAVQuery.whereLessThan(Article.CREATED_AT, getLastTagArticleCreatedAt(tagId));

        }
        //开始查询
        articleAVQuery.findInBackground(new FindCallback<Article>() {
            @Override
            public void done(List<Article> list, AVException e) {
                if (e == null) {
                    if (isLoadMore) {
                        mArticleByTagCache.get(tagId).addAll(list);
                    } else {
                        mArticleByTagCache.put(tagId, list);
                    }
                    callback.onLoadSuccess(list);
                } else {
                    callback.onLoadFailed(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    public void getMoreArticlesByTag(final int tagId, final LoadCallback<Article> callback) {
        getArticlesFromServer(tagId, callback, true);
    }

    private Date getLastTagArticleCreatedAt(int tagId) {
        List<Article> articles = mArticleByTagCache.get(tagId);
        return articles.get(articles.size() - 1).getCreatedAt();
    }


    @Override
    public void getUserSharedArticles(String userId, final LoadCallback<Article> callback) {
        getUserSharedArticlesFromServer(userId, callback, false);
    }

    private void getUserSharedArticlesFromServer(String userId, final LoadCallback<Article> callback, final boolean isLoadMore) {
        AVQuery<Article> articleAVQuery = AVQuery.getQuery(Article.class);
        try {
            User user = AVObject.createWithoutData(User.class, userId);
            articleAVQuery.whereEqualTo(Article.USER, user)
                    .orderByDescending(Article.CREATED_AT)
                    .limit(Constant.DEFAULT_PAGE_SIZE);
            if (isLoadMore) {
                articleAVQuery.whereLessThan(Article.CREATED_AT, getLastArticleCreatedAt());
            }
            articleAVQuery.findInBackground(new FindCallback<Article>() {
                @Override
                public void done(List<Article> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mArticlesCache.addAll(list);
                        } else {
                            mArticlesCache = list;
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
    public void getMoreUserSharedArticles(String userId, final LoadCallback<Article> callback) {
        getUserSharedArticlesFromServer(userId, callback, true);
    }

    @Override
    public void refreshUserShareArticles(String userId, LoadCallback<Article> callback) {
        if (mArticlesCache != null) {
            mArticlesCache.clear();
        }
        getUserSharedArticles(userId, callback);
    }

    public Date getLastArticleCreatedAt() {
        return mArticlesCache.get(mArticlesCache.size() - 1).getCreatedAt();
    }

    @Override
    public void getUserFavouredArticles(String userId, final LoadCallback<Article> callback) {
        getUserFavouredArticlesFromServer(userId, callback, false);
    }

    private void getUserFavouredArticlesFromServer(String userId, final LoadCallback<Article> callback, final boolean isLoadMore) {
        AVQuery<UserArticleFavourMap> query = AVQuery.getQuery(UserArticleFavourMap.class);
        try {
            AVObject avObject = AVObject.createWithoutData(User.class, userId);
            query.whereEqualTo(UserArticleFavourMap.USER, avObject)
                    .include(UserArticleFavourMap.ARTICLE)
                    .limit(Constant.DEFAULT_PAGE_SIZE)
                    .orderByDescending(UserArticleFavourMap.CREATED_AT);
            if (isLoadMore) {
                query.whereLessThan(UserArticleFavourMap.CREATED_AT, getLastUserArticleFavourMapCreatedAt());
            }
            query.findInBackground(new FindCallback<UserArticleFavourMap>() {
                @Override
                public void done(List<UserArticleFavourMap> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mUserArticleFavourMaps.addAll(list);
                        } else {
                            mUserArticleFavourMaps = list;
                            mUserFavouredArticlesCache = new ArrayList<>();
                        }

                        for (int i = 0; i < list.size(); i++) {
                            try {
                                Article article = list.get(i).getAVObject("article", Article.class);
                                mUserFavouredArticlesCache.add(article);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                                callback.onLoadFailed(e1.getLocalizedMessage());
                            }
                        }
                        callback.onLoadSuccess(mUserFavouredArticlesCache);
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

    @Override
    public void refreshUserFavouredArticles(String userId, LoadCallback<Article> callback) {
        if (mUserFavouredArticlesCache != null) {
            mUserFavouredArticlesCache.clear();
        }
        getUserFavouredArticles(userId, callback);
    }

    @Override
    public void getMoreUserFavouredArticles(String userId, LoadCallback<Article> callback) {
        getUserFavouredArticlesFromServer(userId, callback, true);
    }

    public Date getLastUserArticleFavourMapCreatedAt() {
        return mUserArticleFavourMaps.get(mUserArticleFavourMaps.size() - 1).getCreatedAt();
    }
}
