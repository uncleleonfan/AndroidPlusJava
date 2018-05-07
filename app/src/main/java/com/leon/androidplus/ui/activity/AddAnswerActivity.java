package com.leon.androidplus.ui.activity;

import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ScrollView;

import com.leon.androidplus.R;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.contract.AddAnswerContract;
import com.leon.androidplus.data.model.Question;
import com.leon.androidplus.presenter.AddAnswerPresenter;

import javax.inject.Inject;

import butterknife.BindView;

public class AddAnswerActivity extends BaseActivity implements AddAnswerContract.View{

    @BindView(R.id.answer)
    EditText mAnswer;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    @Inject
    AddAnswerPresenter mAddAnswerPresenter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_answer;
    }

    @Override
    protected void init() {
        super.init();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        supportActionBar.setTitle(R.string.answer);
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
                publishAnswer();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void publishAnswer() {
        String title = mAnswer.getText().toString().trim();
        if (title.length() == 0) {
            Snackbar.make(mScrollView, R.string.answer_not_null, Snackbar.LENGTH_SHORT).show();
        } else {
            String answer = mAnswer.getText().toString().trim();
            String serialised = getIntent().getStringExtra(Constant.EXTRA_QUESTION);
            try {
                //将问题数据反序列化
                Question question = (Question) Question.parseAVObject(serialised);
                mAddAnswerPresenter.publishAnswer(answer, question);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onPublishAnswerSuccess() {
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
    public void onPublishAnswerFailed() {
        Snackbar.make(mScrollView, R.string.publish_failed, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAddAnswerPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAddAnswerPresenter.dropView();
    }
}
