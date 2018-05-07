package com.leon.androidplus.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.leon.androidplus.R;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.ui.activity.AnswerDetailActivity;
import com.leon.androidplus.ui.activity.QuestionDetailActivity;

public class TransitionUtils {

    public static void transitionToQuestionDetail(Activity activity, Question question, View title, View questionDes) {
        //获取字符串标识转场的控件
        String titleTransitionName = activity.getResources().getString(R.string.question_title_transition);
        Pair<View, String> titlePair = Pair.create(title, titleTransitionName);
        ActivityOptionsCompat activityOptionsCompat = null;
        //如果问题描述控件不可见，则不执行动画效果
        if (questionDes.getVisibility() == View.VISIBLE) {
            String descTransitionName = activity.getResources().getString(R.string.question_des_transition);
            Pair<View, String> descPair = Pair.create(questionDes, descTransitionName);
            activityOptionsCompat= ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, titlePair, descPair);
        } else {
            activityOptionsCompat= ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity, titlePair);
        }
        Intent intent = new Intent(activity, QuestionDetailActivity.class);
        intent.putExtra(Constant.EXTRA_QUESTION, question.toString());
        ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle());
    }

    public static void transitionToAnswerDetail(Activity activity, Answer answer, View titleView, View answerView) {
        String titleTransitionName = activity.getResources().getString(R.string.question_title_transition);
        String answerTransitionName = activity.getResources().getString(R.string.answer_transition);
        Pair<View, String> titlePair = Pair.create(titleView, titleTransitionName);
        Pair<View, String> answerPair = Pair.create(answerView, answerTransitionName);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(activity, titlePair, answerPair);
        Intent intent = new Intent(activity, AnswerDetailActivity.class);
        intent.putExtra(Constant.EXTRA_ANSWER, answer.toString());;
        ActivityCompat.startActivity(activity, intent, activityOptionsCompat.toBundle());
    }
}
