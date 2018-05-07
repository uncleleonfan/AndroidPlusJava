package com.leon.androidplus.ui.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.leon.androidplus.R;
import com.leon.androidplus.adapter.CommentListAdapter;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.contract.CommentContract;
import com.leon.androidplus.data.model.Answer;
import com.leon.androidplus.data.model.Comment;
import com.leon.androidplus.presenter.CommentPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class CommentActivity extends BaseActivity implements CommentContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.send_edit)
    EditText mSendEdit;
    @BindView(R.id.send_button)
    AppCompatImageButton mSendButton;

    @Inject
    CommentPresenter mCommentPresenter;

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout mCoordinatorLayout;
    private CommentListAdapter mCommentListAdapter;
    private Answer mAnswer;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void init() {
        super.init();
        initActionBar();
        initBottomBar();
        initRecyclerView();
        initSwipeRefreshLayout();

        String serialised = getIntent().getStringExtra(Constant.EXTRA_ANSWER);
        try {
            mAnswer = (Answer) AVObject.parseAVObject(serialised);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mCommentPresenter.takeView(this);
        startLoadComments();
    }

    private void startLoadComments() {
        mSwipeRefreshLayout.setRefreshing(true);
        mCommentPresenter.loadComments(mAnswer.getObjectId());
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCommentPresenter.loadComments(mAnswer.getObjectId());
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCommentListAdapter = new CommentListAdapter(this, null);
        mRecyclerView.setAdapter(mCommentListAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    if (layoutManager.findLastVisibleItemPosition() == mCommentListAdapter.getItemCount() - 1) {
                        mCommentPresenter.loadMoreComments(mAnswer.getObjectId());
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCommentPresenter.dropView();
    }

    private void initBottomBar() {
        mSendEdit.setOnEditorActionListener(mOnEditorActionListener);
        mSendEdit.addTextChangedListener(mTextWatcher);
        mSendButton.setEnabled(false);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendComment();
            }
        });
    }

    private void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(getString(R.string.comment));
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mSendButton.setEnabled(s.length() != 0);
        }
    };

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            sendComment();
            return true;
        }
    };


    private void sendComment() {
        String commentString = mSendEdit.getText().toString().trim();
        mCommentPresenter.sendComment(mAnswer, null, commentString);
        hideSoftKeyboard();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSendCommentSuccess() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.send_success), Snackbar.LENGTH_SHORT).show();
        mSendEdit.getEditableText().clear();
        startLoadComments();
    }

    @Override
    public void onSendCommentFailed() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.send_failed), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadCommentsSuccess(List<Comment> comments) {
        mSwipeRefreshLayout.setRefreshing(false);
        mCommentListAdapter.replaceData(comments);
    }

    @Override
    public void onLoadCommentFailed() {
        mSwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(mCoordinatorLayout, getString(R.string.load_comments_failed), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreCommentsSuccess() {
        mCommentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreCommentFailed() {
        Snackbar.make(mCoordinatorLayout, getString(R.string.load_more_comments_failed), Snackbar.LENGTH_SHORT).show();
    }

}
