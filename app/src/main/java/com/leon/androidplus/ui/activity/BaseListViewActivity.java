package com.leon.androidplus.ui.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.leon.androidplus.R;
import com.leon.androidplus.adapter.BaseListAdapter;
import com.leon.androidplus.app.Constant;

import butterknife.BindView;

public abstract class BaseListViewActivity<T> extends BaseActivity {

    @BindView(R.id.list_view)
    ListView mListView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private BaseListAdapter<T> mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_base_list_view;
    }

    @Override
    protected void init() {
        super.init();
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        initListView();
        initSwipeRefreshLayout();
        startLoadData();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
    }

    private void initListView() {
        mAdapter = onCreateAdapter();
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(mOnScrollListener);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            onListItemClick(view, position);
        }
    };


    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            startRefresh();
        }
    };

    private AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE) {
                if (mListView.getLastVisiblePosition() == mListView.getAdapter().getCount() - 1) {
                    if (mListView.getAdapter().getCount() >= Constant.DEFAULT_PAGE_SIZE) {
                        startLoadMoreData();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    };

    public BaseListAdapter<T> getAdapter() {
        return mAdapter;
    }


    abstract BaseListAdapter<T> onCreateAdapter();//创建适配器

    abstract void startLoadData();//加载数据

    abstract void startLoadMoreData();//加载更多数据

    abstract void startRefresh();//开始刷新

    abstract void onListItemClick(View view, int position);//处理条目点击事件
}
