package com.leon.androidplus.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leon.androidplus.R;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserQuestionItemView extends RelativeLayout {

    @BindView(R.id.question_title)
    TextView mQuestionTitle;
    @BindView(R.id.question_description)
    TextView mQuestionDesc;
    @BindView(R.id.favour_count)
    TextView mFavourCount;
    @BindView(R.id.answer_count)
    TextView mAnswerCount;
    @BindView(R.id.publish_date)
    TextView mPublishDate;

    public UserQuestionItemView(Context context) {
        this(context, null);
    }

    public UserQuestionItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_user_question_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(Question question, int position) {
        mQuestionTitle.setText(question.getTitle());
        if (question.getDescription() == null || question.getDescription().length() == 0) {
            mQuestionDesc.setVisibility(GONE);
        } else {
            mQuestionDesc.setVisibility(View.VISIBLE);
            mQuestionDesc.setText(question.getDescription());
        }
        mAnswerCount.setText(String.valueOf(question.getAnswerCount()));
        mFavourCount.setText(String.valueOf(question.getFavourCount()));
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), question.getCreatedAt()));
    }
}
