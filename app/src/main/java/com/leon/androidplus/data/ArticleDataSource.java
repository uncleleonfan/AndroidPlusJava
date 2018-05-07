package com.leon.androidplus.data;

import com.leon.androidplus.data.model.Article;

public interface ArticleDataSource {

    void saveArticle(Article article, SaveCallback callback);

    void getArticlesByTag(int tagId, LoadCallback<Article> callback);

    void getMoreArticlesByTag(int tagId, LoadCallback<Article> callback);

    void getUserSharedArticles(String userId, LoadCallback<Article> callback);

    void getMoreUserSharedArticles(String userId, LoadCallback<Article> callback);

    void refreshUserShareArticles(String userId, LoadCallback<Article> callback);

    void getUserFavouredArticles(String userId, LoadCallback<Article> callback);

    void getMoreUserFavouredArticles(String userId, LoadCallback<Article> callback);

    void refreshUserFavouredArticles(String userId, LoadCallback<Article> callback);
}
