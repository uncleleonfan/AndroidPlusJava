package com.leon.androidplus.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leon.androidplus.R;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.app.GlideApp;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.ui.activity.AnswerDetailActivity;
import com.leon.androidplus.ui.activity.ProfileActivity;
import com.leon.androidplus.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnswerItemView extends CardView {

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.answer)
    TextView mAnswerText;
    @BindView(R.id.favour_count)
    TextView mFavourCount;
    @BindView(R.id.comment_count)
    TextView mCommentCount;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.publish_date)
    TextView mPublishDate;
    @BindView(R.id.answer_container)
    LinearLayout mAnswerContainer;

    private Answer mAnswer;

    public AnswerItemView(Context context) {
        this(context, null);
    }

    public AnswerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_answer_item, this);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        layoutParams.setMargins(0, margin, 0, 0);
        setLayoutParams(layoutParams);

        ButterKnife.bind(this, this);
    }

    public void bindView(Answer answer) {
        mAnswer = answer;
        mAnswerText.setText(answer.getContent());
        mUserName.setText(answer.getUser().getUsername());
        Date createAt = answer.getCreatedAt();
        mFavourCount.setText(String.valueOf(answer.getLikeCount()));
        mCommentCount.setText(String.valueOf(answer.getCommentCount()));
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), createAt));
        GlideApp.with(this)
                .load(answer.getUser().getAvatar())
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(new CircleCrop())
                .transition(new DrawableTransitionOptions().crossFade())
                .into(mAvatar);
    }

    @OnClick({R.id.avatar, R.id.user_name, R.id.answer_container})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
            case R.id.user_name:
                navigateToProfileActivity();
                break;
                case R.id.answer_container:
                navigateToAnswerDetail();
                break;
        }
    }

    private void navigateToProfileActivity() {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        intent.putExtra(Constant.EXTRA_USER_ID, mAnswer.getUser().getObjectId());
        getContext().startActivity(intent);
    }

    private void navigateToAnswerDetail() {
        Intent intent = new Intent(getContext(), AnswerDetailActivity.class);
        intent.putExtra(Constant.EXTRA_ANSWER, mAnswer.toString());
        getContext().startActivity(intent);
    }
}
