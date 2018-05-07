package com.leon.androidplus.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.leon.androidplus.R;
import com.leon.androidplus.adapter.QuestionDetailPagerAdapter;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.data.model.UserQuestionFavourMap;

import butterknife.BindView;
import butterknife.OnClick;

public class QuestionDetailActivity extends BaseActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.collapsing_tool_bar)
    CollapsingToolbarLayout mCollapsingToolBar;
    @BindView(R.id.question_title)
    TextView mQuestionTitle;
    @BindView(R.id.question_description)
    TextView mQuestionDescription;
    @BindView(R.id.favour_question)
    ImageButton mFavourQuestion;
    @BindView(R.id.add_answer)
    FloatingActionButton mAddAnswer;

    private boolean isFavorite = false;

    private Question mQuestion;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_question_detail;
    }

    @Override
    protected void init() {
        super.init();
        String serialized = getIntent().getStringExtra(Constant.EXTRA_QUESTION);
        try {
            //反序列化
            mQuestion = (Question) Question.parseAVObject(serialized);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mQuestionTitle.setText(mQuestion.getTitle());
        if (mQuestion.getDescription() == null || mQuestion.getDescription().length() == 0) {
            mQuestionDescription.setVisibility(View.GONE);
        } else {
            mQuestionDescription.setVisibility(View.VISIBLE);
            mQuestionDescription.setText(mQuestion.getDescription());
        }

        User currentUser = AVUser.getCurrentUser(User.class);
        isFavorite = currentUser.isFavouredQuestion(mQuestion.getObjectId());
        mFavourQuestion.setSelected(isFavorite);

        //配置转场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mQuestionTitle.setTransitionName(getString(R.string.question_title_transition));
            mQuestionDescription.setTransitionName(getString(R.string.question_des_transition));
            ChangeBounds changeBounds = new ChangeBounds();
            changeBounds.setPathMotion(new ArcMotion());
            getWindow().setSharedElementEnterTransition(changeBounds);
        }

        setSupportActionBar(mToolBar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        mCollapsingToolBar.setTitle(" ");

        String[] titles = getResources().getStringArray(R.array.answer_category);
        mViewPager.setAdapter(new QuestionDetailPagerAdapter(getSupportFragmentManager(), titles, mQuestion.getObjectId()));
        mTabLayout.setupWithViewPager(mViewPager);
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

    public void onAddAnswer(View view) {
        Intent intent = new Intent(this, AddAnswerActivity.class);
        intent.putExtra(Constant.EXTRA_QUESTION, mQuestion.toString());
        startActivity(intent);
    }

    @OnClick(R.id.favour_question)
    public void onViewClicked() {
        User currentUser = AVUser.getCurrentUser(User.class);
        isFavorite = !isFavorite;
        mFavourQuestion.setSelected(isFavorite);
        if (isFavorite) {
            //构建用户和问题之间的关系，
            UserQuestionFavourMap.buildFavourMap(currentUser, mQuestion);
        }else {
            //取消用户和问题之间的关系
            UserQuestionFavourMap.breakFavourMap(currentUser, mQuestion);
        }
    }

}
