package com.leon.androidplus.data;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.Comment;
import com.leon.androidplus.data.model.User;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommentDataRepository implements CommentDataSource {

    private List<Comment> mCommentsCache;

    @Inject
    public CommentDataRepository() {
    }

    @Override
    public void sendComment(Answer answer, String replayTo, String commentString, final SaveCallback callback) {
        Comment comment = new Comment();
        User user = AVUser.getCurrentUser(User.class);
        comment.setContent(commentString);
        answer.addCommentCount();
        comment.setAnswer(answer);
        comment.setUser(user);
        comment.saveInBackground(new com.avos.avoscloud.SaveCallback() {
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
    public void loadComments(String answerId, final LoadCallback<Comment> callback) {
        loadCommentsFromServer(answerId, callback, false);
    }

    private void loadCommentsFromServer(String answerId, final LoadCallback<Comment> loadCallback, final boolean isLoadMore) {
        AVQuery<Comment> commentAVQuery = AVQuery.getQuery(Comment.class);
        try {
            Answer answer = Answer.createWithoutData(Answer.class, answerId);
            commentAVQuery.whereEqualTo(Comment.ANSWER, answer)
                    .include(Comment.USER)
                    .selectKeys(Arrays.asList(Comment.USER_NAME, Comment.USER_AVATAR, Comment.CONTENT))
                    .orderByDescending(AVObject.CREATED_AT)
                    .limit(Constant.DEFAULT_PAGE_SIZE);
            if (isLoadMore) {
                commentAVQuery.whereLessThan(AVObject.CREATED_AT, getLastCommentCrateAt());
            }
            commentAVQuery.findInBackground(new FindCallback<Comment>() {
                @Override
                public void done(List<Comment> list, AVException e) {
                    if (e == null) {
                        if (isLoadMore) {
                            mCommentsCache.addAll(list);
                        } else {
                            mCommentsCache = list;
                        }
                        loadCallback.onLoadSuccess(list);
                    } else {
                        loadCallback.onLoadFailed(e.getLocalizedMessage());
                    }
                }
            });
        } catch (AVException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadMoreComments(String answerId, final LoadCallback<Comment> loadCallback) {
        loadCommentsFromServer(answerId, loadCallback, true);
    }

    private Date getLastCommentCrateAt() {
        return mCommentsCache.get(mCommentsCache.size() - 1).getCreatedAt();
    }
}
