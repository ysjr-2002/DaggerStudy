package com.visitor.obria.yourapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

/**
 * created by yangshaojie  on 2018/9/14
 * email: ysjr-2002@163.com
 */
public class MySurfaceView extends View {

    private int mRedColor = getResources().getColor(R.color.colorRed);
    private int mBlueColor = getResources().getColor(R.color.colorBlue);

    private Paint mPaint;

    public MySurfaceView(Context context) {
        super(context);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorPrimary));
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect(10, 10, 110, 110);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        if (mRed) {
            mPaint.setColor(mRedColor);
            rect.offsetTo(30,30);
        }
        if (mBlue) {
            mPaint.setColor(mBlueColor);
        }
//        mPaint.setAlpha(60);
//        mPaint.setTextSize(40);
//        mPaint.setStyle(Paint.Style.FILL);
//        canvas.drawText("你我是的宝贝", 0,200, mPaint);
        canvas.drawRect(rect, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);

        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
//            result = (int) mTextPaint.measureText(mText) + getPaddingLeft()
//                    + getPaddingRight();
//            if (specMode == MeasureSpec.AT_MOST) {
//                // Respect AT_MOST value if that was what is called for by measureSpec
//                result = Math.min(result, specSize);
//            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    private int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        //mAscent = (int) mTextPaint.ascent();
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
//            result = (int) (-mAscent + mTextPaint.descent()) + getPaddingTop()
//                    + getPaddingBottom();
//            if (specMode == MeasureSpec.AT_MOST) {
//                // Respect AT_MOST value if that was what is called for by measureSpec
//                result = Math.min(result, specSize);
//            }
        }
        return result;
    }

    boolean mRed = true;
    boolean mBlue = false;

    public void change() {

        mRed = !mRed;
        mBlue = !mBlue;
        this.invalidate();
    }

    public void drawRed() {

        mRed = true;
        mBlue = false;
        super.invalidate();
    }

    public void drawBlue() {

        mRed = false;
        mBlue = true;
        super.invalidate();
    }
}
