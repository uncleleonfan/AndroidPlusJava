package com.leon.androidplus.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ScrollView;

import com.leon.androidplus.R;
import com.leon.androidplus.contract.AddQuestionContract;
import com.leon.androidplus.presenter.AddQuestionPresenter;

import javax.inject.Inject;

import butterknife.BindView;

public class AddQuestionActivity extends BaseActivity implements AddQuestionContract.View {

    @BindView(R.id.question_title)
    EditText mQuestionTitle;
    @BindView(R.id.question_description)
    EditText mQuestionDescription;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    @Inject
    AddQuestionPresenter mAddQuestionPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_question;
    }

    @Override
    protected void init() {
        super.init();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.add_question);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.publish, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.publish:
                publishQuestion();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void publishQuestion() {
        String title = mQuestionTitle.getText().toString().trim();
        if (title.length() == 0) {
            Snackbar.make(mScrollView, R.string.title_not_null, Snackbar.LENGTH_SHORT).show();
        } else {
            String des = mQuestionDescription.getText().toString().trim();
            mAddQuestionPresenter.publishQuestion(title, des);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddQuestionPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddQuestionPresenter.dropView();
    }

    @Override
    public void onPublishSuccess() {
        Snackbar.make(mScrollView, R.string.publish_success, Snackbar.LENGTH_SHORT)
                .addCallback(new Snackbar.Callback(){
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onPublishFailed() {
        Snackbar.make(mScrollView, R.string.publish_failed, Snackbar.LENGTH_SHORT).show();
    }
}
