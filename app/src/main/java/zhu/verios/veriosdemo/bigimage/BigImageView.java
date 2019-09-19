package zhu.verios.veriosdemo.bigimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qinxiang.zhu on 2018/8/29.
 * Copyright (C) 2018 Phicomm.
 */
public class BigImageView extends View {
    private static final String TAG = "BigImageView";

    private BitmapRegionDecoder mDecoder;
    private int mImgWidth, mImgHeight;
    private BitmapFactory.Options mOptions;
    private Rect mRect;

    float mDownX, mDownY;
    int mDownRectLeft, mDownRectTop;
    int mViewHeight, mViewWidth;


    public BigImageView(Context context) {
        super(context);
        init();
    }

    public BigImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mOptions = new BitmapFactory.Options();
        mOptions.inPreferredConfig = Bitmap.Config.RGB_565;
        mRect = new Rect();
    }

    public void setBitmapInputStream(InputStream bitmapStream) {
        try {
            mDecoder = BitmapRegionDecoder.newInstance(bitmapStream, false);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bitmapStream, null, options);
            mImgWidth = options.outWidth;
            mImgHeight = options.outHeight;
            requestLayout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bitmapStream != null) {
                try {
                    bitmapStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        mRect.left = 0;
        mRect.right = width;
        mRect.top = 0;
        mRect.bottom = height;
        mViewHeight = height;
        mViewWidth = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDecoder != null) {
//            Log.i(TAG, "onDraw: " + mRect.left + "," + mRect.right + "," + mRect.top + "," + mRect.bottom);
            Bitmap bitmap = mDecoder.decodeRegion(mRect, mOptions);
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                mDownRectLeft = mRect.left;
                mDownRectTop = mRect.top;
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
//                mRect.offset(getMove(mRect.left, mRect.right, -(x - mDownX), mImgWidth),
//                        getMove(mRect.top, mRect.bottom, -(y - mDownY), mImgHeight));
//                mDownX = x;
//                mDownY = y;
                move((int)(mDownX - x), (int)(mDownY - y));
                invalidate();
            default:
        }
            return true;
    }

    private int getMove(int floor, int ceil , float move, int bound) {
        if (ceil + move > bound) {
            return bound - ceil;
        } else if (floor + move < 0) {
            return 0;
        } else {
            return (int)move;
        }
    }

    private void move(int moveX, int moveY) {
        int left = 0, top = 0;
        if (mImgWidth > mViewWidth) {
            if (mDownRectLeft + moveX <= 0) {
                left = 0;
            } else if (mDownRectLeft + mViewWidth + moveX > mImgWidth) {
                left = mImgWidth - mViewWidth;
            } else {
                left = mDownRectLeft + moveX;
            }
        }

        if (mImgHeight > mViewHeight) {
            if (mDownRectTop + moveY <= 0) {
                top = 0;
            } else if (mDownRectTop + mViewHeight + moveY > mImgHeight) {
                top = mImgHeight - mViewHeight;
            } else {
                top = mDownRectTop + moveY;
            }
        }
        mRect.set(left, top, left + mViewWidth, top + mViewHeight);
    }

}
