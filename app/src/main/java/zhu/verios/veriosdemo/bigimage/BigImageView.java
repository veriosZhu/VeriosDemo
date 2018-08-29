package zhu.verios.veriosdemo.bigimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qinxiang.zhu on 2018/8/29.
 * Copyright (C) 2018 Phicomm.
 */
public class BigImageView extends View {

    private BitmapRegionDecoder mDecoder;
    private int mImgWidth, mImgHeight;
    private BitmapFactory.Options mOptions;
    private Rect mRect;


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
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDecoder != null) {
            Bitmap bitmap = mDecoder.decodeRegion(mRect, mOptions);
            canvas.drawBitmap(bitmap, 0, 0, null);
        }
    }

    float mDownX, mDownY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                mRect.offset(getMove(mRect.left, mRect.right, x - mDownX), getMove(mRect.top, mRect.bottom, y - mDownY));
                invalidate();
            default:
        }
            return true;
    }

    private int getMove(int lowBound, int highBound , float move) {
        if (highBound + move > mImgWidth) {
            return mImgWidth - highBound;
        } else if (lowBound + move < 0) {
            return lowBound;
        } else {
            return (int)move;
        }
    }

}
