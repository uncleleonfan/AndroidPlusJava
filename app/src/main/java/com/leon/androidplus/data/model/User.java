package com.leon.androidplus.data.model;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.leon.androidplus.data.LoadCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

@AVClassName("User")
public class User extends AVUser {

    public void setAvatar(String avatarUrl) {
        put("avatar", avatarUrl);
    }

    public String getAvatar() {
        return getString("avatar");
    }

    public void setSlogan(String slogan) {
        put("slogan", slogan);
        saveInBackground();
    }

    public String getSlogan() {
        return getString("slogan");
    }

    public void addLikedAnswer(String answerId) {
        add("liked", answerId);
        saveInBackground();
    }

    public void addFavourQuestion(String questionId) {
        add("favoured_question", questionId);
        saveInBackground();
    }

    public void addFavourArticle(String objectId) {
        add("favoured_article", objectId);
        saveInBackground();
    }


    public boolean isLikedAnswer(String objectId) {
        JSONArray liked = getJSONArray("liked");
        return hasRecord(objectId, liked);
    }

    public boolean isFavouredQuestion(String objectId) {
        JSONArray favoured = getJSONArray("favoured_question");
        return hasRecord(objectId, favoured);
    }

    public boolean isFavouredArticle(String objectId) {
        JSONArray favoured = getJSONArray("favoured_article");
        return hasRecord(objectId, favoured);
    }

    public void removeLikedAnswer(String objectId) {
        removeAll("liked", Arrays.asList(objectId));
        saveInBackground();
    }

    public void removeFavouredQuestion(String objectId) {
        removeAll("favoured_question", Arrays.asList(objectId));
    }

    public void removeFavouredArticle(String objectId) {
        removeAll("favoured_article", Arrays.asList(objectId));
        saveInBackground();
    }

    private boolean hasRecord(String objectId, JSONArray jsonArray) {
        if (jsonArray == null) {
            return false;
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String id = jsonArray.getString(i);
                if (id.equals(objectId)) {
                    return true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void getUser(final LoadCallback<User> loadCallback, String objectId) {
        AVQuery<User> userAVQuery = AVQuery.getQuery(User.class);
        userAVQuery.whereEqualTo(AVUser.OBJECT_ID, objectId);
        userAVQuery.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e == null) {
                    loadCallback.onLoadSuccess(list);
                } else {
                    loadCallback.onLoadFailed(e.getLocalizedMessage());
                }

            }
        });

    }
}
