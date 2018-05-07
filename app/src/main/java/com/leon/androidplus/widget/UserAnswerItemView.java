package com.leon.androidplus.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leon.androidplus.R;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAnswerItemView extends RelativeLayout {

    @BindView(R.id.answer)
    TextView mAnswer;
    @BindView(R.id.favour_count)
    TextView mFavourCount;
    @BindView(R.id.comment_count)
    TextView mCommentCount;
    @BindView(R.id.publish_date)
    TextView mPublishDate;
    @BindView(R.id.question_title)
    TextView mQuestionTitle;

    public UserAnswerItemView(Context context) {
        this(context, null);
    }

    public UserAnswerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_user_answer_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(Answer answer, int position) {
        mQuestionTitle.setText(answer.getQuestion().getTitle());
        mAnswer.setText(answer.getContent());
        Date createAt = answer.getCreatedAt();
        mFavourCount.setText(String.valueOf(answer.getLikeCount()));
        mCommentCount.setText(String.valueOf(answer.getCommentCount()));
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), createAt));
    }
}
