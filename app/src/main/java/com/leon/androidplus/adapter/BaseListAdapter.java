package com.leon.androidplus.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Leon on 2017-10-18.
 */

public abstract class BaseListAdapter<T> extends BaseAdapter {

    private Context mContext;
    private List<T> mDataList;

    public BaseListAdapter(Context context) {
        this(context, null);
    }

    public BaseListAdapter(Context context, List<T> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    /**
     * 返回对应位置的数据
     * @param position
     * @return
     */
    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = onCreateItemView(position);
        }
        onBindItemView(convertView, position);
        return convertView;
    }

    public Context getContext() {
        return mContext;
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public void replaceData(List<T> dataList) {
        mDataList = dataList;
        notifyDataSetChanged();
    }

    /**
     * 子类必须实现该方法来完成条目的创建
     * @return
     */
    abstract View onCreateItemView(int position);

    /**
     * 子类实现该方法来完成条目的绑定
     * @param itemView
     * @param position
     */
    abstract void onBindItemView(View itemView, int position);
}
