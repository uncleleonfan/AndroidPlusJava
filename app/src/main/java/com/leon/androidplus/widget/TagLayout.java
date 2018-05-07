package com.leon.androidplus.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.leon.androidplus.R;

public class TagLayout extends FlowLayout {

    private OnTagSelectedListener mOnTagSelectedListener;

    private int mLastSelectedPosition = -1;

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context) {
        super(context);
    }


    public void setTags(final String[] tags) {
        int padding = getResources().getDimensionPixelSize(R.dimen.default_padding);
        for (int i = 0; i < tags.length; i++) {
            TextView textView = new TextView(getContext());
            textView.setText(tags[i]);
            textView.setBackgroundResource(R.drawable.category_item_selector);
            textView.setPadding(padding, padding, padding, padding);
            textView.setText(tags[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(getResources().getColorStateList(R.color.category_item_color));
            final int position = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == mLastSelectedPosition) {
                        return;
                    }
                    v.setSelected(true);
                    if (mLastSelectedPosition > -1) {
                        getChildAt(mLastSelectedPosition).setSelected(false);
                    }
                    if (mOnTagSelectedListener != null) {
                        mOnTagSelectedListener.onTagSelected(tags[position], position);
                    }
                    mLastSelectedPosition = position;
                }
            });
            addView(textView);
        }
    }

    public interface OnTagSelectedListener {
        void onTagSelected(String tag, int position);
    }

    public void setOnTagSelectedListener(OnTagSelectedListener listener) {
        mOnTagSelectedListener = listener;
    }
}
