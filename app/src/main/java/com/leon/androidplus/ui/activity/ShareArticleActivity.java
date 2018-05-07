package com.leon.androidplus.ui.activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.leon.androidplus.R;
import com.leon.androidplus.data.ArticleDataSource;
import com.leon.androidplus.data.SaveCallback;
import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.data.model.User;
import com.leon.androidplus.utils.RegexUtils;
import com.leon.androidplus.widget.TagLayout;

import javax.inject.Inject;

import butterknife.BindView;

public class ShareArticleActivity extends BaseActivity {

    @BindView(R.id.article_url)
    TextView mArticleUrl;
    @BindView(R.id.article_title)
    EditText mArticleTitle;
    @BindView(R.id.article_description)
    EditText mArticleDescription;
    @BindView(R.id.tag_layout)
    TagLayout mTagLayout;
    @BindView(R.id.scroll_view)
    ScrollView mScrollView;

    private int mTag = -1;

    @Inject
    ArticleDataSource mArticleDataSource;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_share_article;
    }

    @Override
    protected void init() {
        super.init();
        User user = AVUser.getCurrentUser(User.class);
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle(R.string.share);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        String text = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        mArticleUrl.setText(RegexUtils.matchShareUrl(text));

        mTagLayout.setTags(getResources().getStringArray(R.array.article_category_without_hot));
        mTagLayout.setOnTagSelectedListener(new TagLayout.OnTagSelectedListener() {
            @Override
            public void onTagSelected(String tag, int position) {
                mTag = position + 1;
            }
        });
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
                navigateTo(MainActivity.class);
                break;
            case R.id.publish:
                publishArticle();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        navigateTo(MainActivity.class);
    }

    private void publishArticle() {
        String title = mArticleTitle.getText().toString().trim();
        if (title.length() == 0) {
            Snackbar.make(mScrollView, getString(R.string.title_not_null), Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (mTag == -1) {
            Snackbar.make(mScrollView, getString(R.string.tag_not_null), Snackbar.LENGTH_SHORT).show();
            return;
        }

        Article article = new Article();
        article.setTitle(title);
        String desc = mArticleDescription.getText().toString().trim();
        article.setDesc(desc);
        article.setUrl(mArticleUrl.getText().toString());
        article.setTag(mTag);

        User user = AVUser.getCurrentUser(User.class);
        article.setUser(user);
        mArticleDataSource.saveArticle(article, new SaveCallback() {
            @Override
            public void onSaveSuccess() {
                Snackbar.make(mScrollView, getString(R.string.publish_success), Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onSaveFailed(String errorMsg) {
                Snackbar.make(mScrollView, getString(R.string.publish_failed), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
