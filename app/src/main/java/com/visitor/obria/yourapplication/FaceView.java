package com.visitor.obria.yourapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FaceView extends View {

    private Paint mPaint;
    private String mCorlor = "#42ed45";
    private List<RectF> mRectFS;

    public FaceView(Context context) {
        super(context);
        init();
    }

    public FaceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FaceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(mCorlor));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mRectFS != null) {
            for (RectF face :
                    mRectFS) {
                canvas.drawRect(face, mPaint);
            }
        }
    }

    public void setFaces(List<RectF> faces) {

        this.mRectFS = faces;
        this.invalidate();
    }
}
