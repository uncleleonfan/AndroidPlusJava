package com.leon.androidplus.widget;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;

import com.leon.androidplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CardLoadingView extends CardView {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public CardLoadingView(Context context) {
        this(context, null);
    }

    public CardLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.card_view_loading, this);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        int margin = getResources().getDimensionPixelSize(R.dimen.default_margin);
        layoutParams.setMargins(0, margin, 0, margin);
        setLayoutParams(layoutParams);
        ButterKnife.bind(this, this);

        mProgressBar.getIndeterminateDrawable().setColorFilter(
                ContextCompat.getColor(getContext(), R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
    }


}
