package com.leon.androidplus.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leon.androidplus.R;
import com.leon.androidplus.app.GlideApp;
import com.leon.androidplus.data.model.Comment;
import com.leon.androidplus.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentItemView extends RelativeLayout {

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.publish_date)
    TextView mPublishDate;
    @BindView(R.id.comment)
    TextView mComment;

    public CommentItemView(Context context) {
        this(context, null);
    }

    public CommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_comment_item, this);
        ButterKnife.bind(this);
        int padding = getResources().getDimensionPixelSize(R.dimen.default_padding);
        setPadding(padding, padding, padding, padding);
    }

    public void bindView(Comment comment) {
        mUserName.setText(comment.getUser().getUsername());
        mComment.setText(comment.getContent());
        GlideApp.with(this).load(comment.getUser().getAvatar())
                .transform(new CircleCrop())
                .placeholder(R.mipmap.ic_launcher_round)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(mAvatar);
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), comment.getCreatedAt()));
    }
}
