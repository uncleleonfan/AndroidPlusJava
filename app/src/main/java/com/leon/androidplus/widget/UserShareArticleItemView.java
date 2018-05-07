package com.leon.androidplus.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leon.androidplus.R;
import com.leon.androidplus.data.model.Article;
import com.leon.androidplus.utils.DateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserShareArticleItemView extends RelativeLayout {

    @BindView(R.id.article_title)
    TextView mArticleTitle;
    @BindView(R.id.article_description)
    TextView mArticleDescription;
    @BindView(R.id.favour_count)
    TextView mFavourCount;
    @BindView(R.id.publish_date)
    TextView mPublishDate;

    public UserShareArticleItemView(Context context) {
        this(context, null);
    }

    public UserShareArticleItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_user_share_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(Article article) {
        mArticleTitle.setText(article.getTitle());
        if (article.getDes() == null || article.getDes().length() == 0) {
            mArticleDescription.setVisibility(GONE);
        } else {
            mArticleDescription.setVisibility(View.VISIBLE);
            mArticleDescription.setText(article.getDes());
        }
        mFavourCount.setText(String.valueOf(article.getFavourCount()));
        mPublishDate.setText(DateUtils.getDisplayString(getContext(), article.getCreatedAt()));
    }
}
