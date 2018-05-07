package com.leon.androidplus.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.leon.androidplus.R;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.data.model.UserAnswerFavourMap;
import com.leon.androidplus.utils.DateUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class AnswerDetailActivity extends BaseActivity {

    @BindView(R.id.question_title)
    TextView mQuestionTitle;
    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.answer)
    TextView mAnswerText;
    @BindView(R.id.publish_date)
    TextView mPublishDate;
    @BindView(R.id.thumb_up)
    ImageButton mThumbUp;
    @BindView(R.id.comment)
    ImageButton mComment;

    private boolean isUp = false;
    private Answer mAnswer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_answer_detail;
    }

    @Override
    protected void init() {
        super.init();
        String serialised = getIntent().getStringExtra(Constant.EXTRA_ANSWER);
        try {
            mAnswer = (Answer) AVObject.parseAVObject(serialised);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mQuestionTitle.setTransitionName(getString(R.string.question_title_transition));
            mAnswerText.setTransitionName(getString(R.string.answer_transition));
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setPathMotion(new ArcMotion());
            getWindow().setSharedElementEnterTransition(changeBounds);
        }

        initActionBar();
        initViews();
    }

    private void initViews() {
        User user = AVUser.getCurrentUser(User.class);
        if (user.isLikedAnswer(mAnswer.getObjectId())) {
            isUp = true;
            mThumbUp.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }

        mQuestionTitle.setText(mAnswer.getQuestion().getTitle());
        mAnswerText.setText(mAnswer.getContent());
        mPublishDate.setText(DateUtils.getDateTimeFormat(mAnswer.getCreatedAt()));
    }

    private void initActionBar() {
        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        String userName = mAnswer.getUser().getUsername();
        String title = String.format(getString(R.string.user_answer), userName);
        supportActionBar.setTitle(title);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
        }
        return true;
    }

    @OnClick({R.id.thumb_up, R.id.comment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.thumb_up:
                handleThumbUp();
                break;
            case R.id.comment:
                navigateToComment();
                break;
        }
    }

    private void navigateToComment() {
        Intent intent = new Intent(this, CommentActivity.class);
        intent.putExtra(Constant.EXTRA_ANSWER, getIntent().getStringExtra(Constant.EXTRA_ANSWER));
        startActivity(intent);
    }

    private void handleThumbUp() {
        isUp = !isUp;
        User user = AVUser.getCurrentUser(User.class);
        if (isUp) {
            mThumbUp.setColorFilter(getResources().getColor(R.color.colorPrimary));
            UserAnswerFavourMap.buildFavourMap(user, mAnswer);

        } else {
            mThumbUp.setColorFilter(getResources().getColor(R.color.primary_light));
            UserAnswerFavourMap.breakFavourMap(user, mAnswer);
        }
    }
}
