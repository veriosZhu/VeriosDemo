package zhu.verios.veriosdemo.scrollviewex;

import android.content.Context;
import android.icu.util.Measure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;
import static android.view.View.MeasureSpec.AT_MOST;

/**
 * @author qinxiang.zhu on 2018/9/18.
 * Copyright (C) 2018 Phicomm.
 */
public class HorizontalScrollViewEx extends ViewGroup {

    private static final String TAG = "HorizontalScrollViewEx";

    private int mChildrenSize;
    private int mChildWidth;
    private int mChildIndex;
    private Scroller mScroller;
    private VelocityTracker mTracker;

    private int mLastX = 0, mLastY = 0;
    private int mLastXIntercept = 0, mLastYIntercept = 0;

    public HorizontalScrollViewEx(Context context) {
        super(context);
        init(context);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
        mTracker = VelocityTracker.obtain();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        final int childCount = getChildCount();
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpaceSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSpaceSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        if (childCount == 0) {
            setMeasuredDimension(widthSpaceSize, heightSpaceSize);
        } else if (widthSpecMode == AT_MOST && heightSpecMode == AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            measureHeight = childView.getMeasuredHeight();
            setMeasuredDimension(measureWidth, measureHeight);
        } else if (heightSpecMode == AT_MOST) {
            final View childView = getChildAt(0);
            setMeasuredDimension(widthSpaceSize, childView.getMeasuredHeight());
        } else if (widthSpecMode == AT_MOST) {
            final View childView = getChildAt(0);
            measureWidth = childView.getMeasuredWidth() * childCount;
            setMeasuredDimension(measureWidth, heightSpaceSize);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildrenSize = childCount;
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft, 0, childLeft + childWidth, childView.getMeasuredHeight());
                childLeft += childWidth;

            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;
            case ACTION_MOVE:
                int deltaX = x - mLastXIntercept, deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    intercept = false;
                } else {
                    intercept = true;
                }
                break;
            case ACTION_UP:
                intercept = false;
                break;
                default:
        }
        mLastYIntercept = y; mLastXIntercept = x;
        mLastX = x; mLastY = y;
        Log.i(TAG, "onInterceptTouchEvent: " + x + ", " + y + ", " + ev.getAction() + ", " + intercept);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mTracker.addMovement(event);
        int x = (int) event.getX(), y = (int) event.getY();
        Log.i(TAG, "onTouchEvent: " + x + ", " + y);
        switch (event.getAction()) {
            case ACTION_DOWN:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            case ACTION_MOVE:
                int deltaX = x - mLastX;
                scrollBy(-deltaX, 0);
                break;
            case ACTION_UP:
                int scrollX = getScrollX();
                mTracker.computeCurrentVelocity(1000);
                float xVelocity = mTracker.getXVelocity();
                if (Math.abs(xVelocity) > 50) {
                    mChildIndex = xVelocity > 0? mChildIndex - 1: mChildIndex + 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / mChildWidth;
                }
                mChildIndex = Math.max(0, Math.min(mChildIndex, mChildrenSize - 1));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScrollBy(dx);
                mTracker.clear();
            default:
                break;
        }


        mLastX = x; mLastY = y;
        return true;
    }

    private void smoothScrollBy(int dx) {
        mScroller.startScroll(getScrollX(), 0, dx, 0, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
