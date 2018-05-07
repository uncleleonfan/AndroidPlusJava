package com.leon.androidplus.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.leon.androidplus.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingView extends FrameLayout {

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_loading, this);
        ButterKnife.bind(this, this);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);;
    }


}
