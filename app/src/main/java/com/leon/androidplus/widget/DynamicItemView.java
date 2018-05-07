package com.leon.androidplus.widget;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.leon.androidplus.R;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.utils.DateUtils;
import com.leon.androidplus.utils.TransitionUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DynamicItemView extends CardView {

    @BindView(R.id.question_title)
    TextView mQuestionTitle;
    @BindView(R.id.answer)
    TextView mAnswerText;
    @BindView(R.id.favour_count)
    TextView mFavourCount;
    @BindView(R.id.comment_count)
    TextView mCommentCount;
    @BindView(R.id.publish_date)
    TextView mPublishDate;
    private Answer mAnswer;

    public DynamicItemView(Context context) {
        this(context, null);
    }

    public DynamicItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_dynamic_item, this);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        layoutParams.setMargins(0, margin, 0, 0);
        setLayoutParams(layoutParams);
        ButterKnife.bind(this, this);
    }

    @OnClick(R.id.dynamic_item)
    public void onViewClicked(View view) {
        navigateToAnswerDetail();
    }

    public void bindView(Answer answer) {
        mAnswer = answer;
        mQuestionTitle.setText(answer.getQuestion().getTitle());
        String answerString = String.format(getResources().getString(R.string.dynamic_user_answer),
                answer.getUser().getUsername(),
                answer.getContent());
        mAnswerText.setText(answerString);
        mCommentCount.setText(String.valueOf(answer.getCommentCount()));
        mFavourCount.setText(String.valueOf(answer.getLikeCount()));
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), answer.getCreatedAt()));
    }

    private void navigateToAnswerDetail() {
        TransitionUtils.transitionToAnswerDetail((Activity) getContext(), mAnswer, mQuestionTitle, mAnswerText);
    }
}
