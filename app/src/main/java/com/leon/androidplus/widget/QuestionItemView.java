package com.leon.androidplus.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.leon.androidplus.R;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.app.GlideApp;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.ui.activity.ProfileActivity;
import com.leon.androidplus.utils.DateUtils;
import com.leon.androidplus.utils.TransitionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionItemView extends CardView {

    @BindView(R.id.question_title)
    TextView mQuestionTitle;
    @BindView(R.id.question_description)
    TextView mQuestionDesc;
    @BindView(R.id.favour_count)
    TextView mFavourCount;
    @BindView(R.id.answer_count)
    TextView mAnswerCount;
    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.publish_date)
    TextView mPublishDate;

    private Question mQuestion;

    public QuestionItemView(Context context) {
        this(context, null);
    }

    public QuestionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_question_item, this);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        layoutParams.setMargins(0, margin, 0, 0);
        setLayoutParams(layoutParams);

        ButterKnife.bind(this, this);
    }

    public void bindView(Question question) {
        mQuestion = question;
        mUserName.setText(question.getUser().getUsername());
        mQuestionTitle.setText(question.getTitle());
        if (question.getDescription() == null || question.getDescription().length() == 0) {
            mQuestionDesc.setVisibility(View.GONE);
        } else {
            mQuestionDesc.setVisibility(View.VISIBLE);
            mQuestionDesc.setText(question.getDescription());
        }
        mAnswerCount.setText(String.valueOf(question.getAnswerCount()));
        mFavourCount.setText(String.valueOf(question.getFavourCount()));
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), question.getCreatedAt()));
        GlideApp.with(this)
                .load(question.getUser().getAvatar())
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(new CircleCrop())
                .into(mAvatar);
    }

    @OnClick({R.id.avatar, R.id.user_name, R.id.question_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
            case R.id.user_name:
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, mQuestion.getUser().getObjectId());
                getContext().startActivity(intent);
                break;
            case R.id.question_item:
                navigateToQuestionDetail();
                break;

        }
    }

    private void navigateToQuestionDetail() {
        TransitionUtils.transitionToQuestionDetail((Activity) getContext(),
                mQuestion, mQuestionTitle, mQuestionDesc);
    }

}
