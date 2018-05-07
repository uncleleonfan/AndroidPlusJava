package com.leon.androidplus.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.DeleteCallback;

import java.util.Arrays;

@AVClassName("UserArticleFavourMap")
public class UserArticleFavourMap extends AVObject {

    public static final String USER = "user";
    public static final String ARTICLE = "article";

    public void setUser(User user) {
        put(USER, user);
    }

    public void setArticle(Article article) {
        put(ARTICLE, article);
    }

    public static void buildFavourMap(User user, Article article) {
        user.addFavourArticle(article.getObjectId());
        article.increaseFavourCount();
        UserArticleFavourMap userArticleFavourMap = new UserArticleFavourMap();
        userArticleFavourMap.setUser(user);
        userArticleFavourMap.setArticle(article);
        userArticleFavourMap.saveInBackground();
    }

    public static void breakFavourMap(User user, Article article) {
        user.removeFavouredArticle(article.getObjectId());
        article.decreaseFavourCount();
        AVQuery<UserArticleFavourMap> userQuery = AVQuery.getQuery(UserArticleFavourMap.class);
        userQuery.whereEqualTo(USER, user);
        AVQuery<UserArticleFavourMap> articleQuery = AVQuery.getQuery(UserArticleFavourMap.class);
        articleQuery.whereEqualTo(ARTICLE, article);
        AVQuery<UserArticleFavourMap> query = AVQuery.and(Arrays.asList(userQuery, articleQuery));
        query.deleteAllInBackground(new DeleteCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                }
            }
        });
    }
}
