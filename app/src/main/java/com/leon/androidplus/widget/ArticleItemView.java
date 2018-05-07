package com.leon.androidplus.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.leon.androidplus.R;
import com.leon.androidplus.app.Constant;
import com.leon.androidplus.app.GlideApp;
import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.ui.activity.ArticleDetailActivity;
import com.leon.androidplus.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArticleItemView extends CardView {

    @BindView(R.id.avatar)
    ImageView mAvatar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.article_title)
    TextView mArticleTitle;
    @BindView(R.id.article_description)
    TextView mArticleDescription;
    @BindView(R.id.favour_count)
    TextView mFavourCount;
    @BindView(R.id.publish_date)
    TextView mPublishDate;
    private Article mArticle;


    public ArticleItemView(Context context) {
        this(context, null);
    }

    public ArticleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_article_item, this);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        layoutParams.setMargins(0, margin, 0, 0);
        setLayoutParams(layoutParams);

        ButterKnife.bind(this, this);
    }

    public void bindView(Article article) {
        mArticle = article;
        mUserName.setText(article.getUser().getUsername());
        mArticleTitle.setText(article.getTitle());
        if (article.getDes() == null || article.getDes().length() == 0) {
            mArticleDescription.setVisibility(View.GONE);
        } else {
            mArticleDescription.setVisibility(View.VISIBLE);
            mArticleDescription.setText(article.getDes());
        }
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), article.getCreatedAt()));
        mFavourCount.setText(String.valueOf(article.getFavourCount()));
        GlideApp.with(this).load(article.getUser().getAvatar())
                .placeholder(R.mipmap.ic_launcher_round)
                .transform(new CircleCrop()).transition(new DrawableTransitionOptions().crossFade()).into(mAvatar);
    }

    @OnClick({R.id.avatar, R.id.user_name, R.id.article_item})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.avatar:
            case R.id.user_name:
                break;
            case R.id.article_item:
                navigateToArticleDetailActivity();
                break;
        }
    }

    private void navigateToArticleDetailActivity() {
        Intent intent = new Intent(getContext(), ArticleDetailActivity.class);
        intent.putExtra(Constant.EXTRA_ARTICLE, mArticle.toString());
        getContext().startActivity(intent);
    }
}
