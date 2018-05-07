package com.leon.androidplus.data;

import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.Comment;

public interface CommentDataSource {

    void sendComment(Answer answer, String replayTo, String comment, SaveCallback callback);

    void loadComments(String commentId, LoadCallback<Comment> callback);

    void loadMoreComments(String commentId, LoadCallback<Comment> loadCallback);
}
