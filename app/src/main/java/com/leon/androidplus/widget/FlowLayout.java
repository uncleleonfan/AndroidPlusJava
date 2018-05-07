package com.leon.androidplus.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class FlowLayout extends ViewGroup {

    private List<Line> mLines = new ArrayList<Line>(); // 用来记录描述有多少行View
    private int mHorizontalSpace = 10;
    private int mVerticalSpace = 10;

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context) {
        super(context);
    }

    public void setSpace(int horizontalSpace, int verticalSpace) {
        this.mHorizontalSpace = horizontalSpace;
        this.mVerticalSpace = verticalSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 清空
        mLines.clear();
        Line currentLine = null;
        //获取父容器对FlowLayout期望的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // 获取行最大的宽度
        int maxLineWidth = width - getPaddingLeft() - getPaddingRight();

        // 测量孩子，并将孩子添加到它所属的Line中
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            // 如果孩子不可见
            if (view.getVisibility() == View.GONE) {
                continue;
            }
            // 测量孩子
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
            // 往lines添加孩子
            if (currentLine == null) {// 说明还没有开始添加孩子
                currentLine = new Line(maxLineWidth, mHorizontalSpace);
                // 添加到 Lines中
                mLines.add(currentLine);
                //添加一个孩子
                currentLine.addView(view);
            } else {
                // 行不为空,行中有孩子了
                boolean canAdd = currentLine.canAdd(view);
                if (canAdd) {// 可以添加
                    currentLine.addView(view);
                } else {// 不可以添加,装不下去，需要新建行
                    currentLine = new Line(maxLineWidth, mHorizontalSpace);
                    // 添加到lines中
                    mLines.add(currentLine);
                    // 将view添加到line
                    currentLine.addView(view);
                }
            }
        }

        float allHeight = 0;
        //计算所有行高和间距的和
        for (int i = 0; i < mLines.size(); i++) {
            allHeight += mLines.get(i).mHeight;
            // 如果不是一行，则需要加间距
            if (i != 0) {
                allHeight += mVerticalSpace;
            }
        }
        //计算FlowLayout的高度
        int measuredHeight = (int) (allHeight + getPaddingTop() + getPaddingBottom() + 0.5f);
        //设置FlowLayout的宽高
        setMeasuredDimension(width, measuredHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int offsetTop = getPaddingTop();
        //布局每一行
        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);
            line.layout(paddingLeft, offsetTop);
            //增加竖直方向的偏移量
            offsetTop += line.mHeight + mVerticalSpace;
        }
    }

    /**
     * FlowLayout每一行的数据结构
     */
    class Line {
        // 属性
        private List<View> mViewsInLine = new ArrayList<View>();    // 用来记录每一行有几个View
        private float mMaxWidth;                            // 行最大的宽度
        private float mUsedWidth;                        // 已经使用了多少宽度
        private float mHeight;                            // 行的高度
        private float mMarginLeft;
        private float mMarginRight;
        private float mMarginTop;
        private float mMarginBottom;
        private float mHorizontalSpace;                    // View和view之间的水平间距

        public Line(int maxWidth, int horizontalSpace) {
            this.mMaxWidth = maxWidth;
            this.mHorizontalSpace = horizontalSpace;
        }

        /**
         * 往一行里面添加view，记录属性的变化
         */
        public void addView(View view) {
            // 加载View的方法
            int size = mViewsInLine.size();
            // 获取添加view的宽和高
            int viewWidth = view.getMeasuredWidth();
            int viewHeight = view.getMeasuredHeight();
            // size == 0 表示还没有添加View
            if (size == 0) {
                //如果添加view的宽度大于最大宽度，那么已经使用的宽度就赋值为最大宽度
                if (viewWidth > mMaxWidth) {
                    mUsedWidth = mMaxWidth;
                } else {//如果添加view的宽度小于或者等于最大宽度，那么已经使用的宽度就赋值添加view的宽度
                    mUsedWidth = viewWidth;
                }
                mHeight = viewHeight;//
            } else { // size不为0表示一行里面已经添加了一个view
                //已经使用的宽度就增加一个view的宽度和水平间隔
                mUsedWidth += viewWidth + mHorizontalSpace;
                //行的高度去所有view中最大的高度
                mHeight = mHeight < viewHeight ? viewHeight : mHeight;
            }

            // 将View记录到集合中
            mViewsInLine.add(view);
        }

        /**
         * 用来判断是否可以将View添加到行中
         *
         * @param view 要添加到行里面的view
         * @return true 表示可以添加，false 表示不可以添加
         */
        private boolean canAdd(View view) {
            // 判断是否能添加View
            int size = mViewsInLine.size();
            //如果该行没有添加过view则返回true
            if (size == 0) {
                return true;
            }

            int viewWidth = view.getMeasuredWidth();

            // 预计使用的宽度
            float planWidth = mUsedWidth + mHorizontalSpace + viewWidth;
            //预计宽度小于等于最大宽度表示可以添加
            return planWidth <= mMaxWidth;
        }

        /**
         * 给孩子布局
         *
         * @param offsetLeft 孩子左边位置的偏移量
         * @param offsetTop 孩子顶部位置的偏移量
         */
        public void layout(int offsetLeft, int offsetTop) {
            int currentLeft = offsetLeft;

            int size = mViewsInLine.size();
            float extra = 0;
            float widthAvg = 0;
            if (mUsedWidth < mMaxWidth) {// 判断已经使用的宽度是否小于最大的宽度
                extra = mMaxWidth - mUsedWidth;//计算出剩余的宽度
                widthAvg = extra / size;//将剩余宽度平均分配到一行的所有的view
            }

            for (int i = 0; i < size; i++) {
                View view = mViewsInLine.get(i);
                int viewWidth = view.getMeasuredWidth();
                int viewHeight = view.getMeasuredHeight();

                // 判断是否有富余
                if (widthAvg != 0) {
                    // 改变宽度
                    int newWidth = (int) (viewWidth + widthAvg + 0.5f);
                    int widthMeasureSpec = MeasureSpec.makeMeasureSpec(newWidth, MeasureSpec.EXACTLY);
                    int heightMeasureSpec = MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY);
                    //重新的测量孩子
                    view.measure(widthMeasureSpec, heightMeasureSpec);
                    viewWidth = view.getMeasuredWidth();
                    viewHeight = view.getMeasuredHeight();
                }

                // 布局
                int left = currentLeft;
                //为了使view能够在竖直方向摆在一行的中间，计算top
                int top = (int) (offsetTop + (mHeight - viewHeight) / 2 + 0.5f);
                int right = left + viewWidth;
                int bottom = top + viewHeight;
                //布局一个view
                view.layout(left, top, right, bottom);
                //调整水平方向的偏移量，布局下一个view
                currentLeft += viewWidth + mHorizontalSpace;
            }
        }
    }

}
