package com.visitor.obria.yourapplication.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * created by yangshaojie  on 2018/9/17
 * email: ysjr-2002@163.com
 */
public class MyImageView extends ImageView {
    public MyImageView(Context context) {
        super(context);
        init();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        Drawable drawable = this.getDrawable();
        if (drawable == null) {
            return;
        }

        int w = getWidth();
        int h = getHeight();
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        if ((drawable instanceof BitmapDrawable) == false) {
            return;
        }

//        Rect source= new Rect(0,0,300, 600);
//        Rect rect = new Rect(0, 0, w, h);
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
//        canvas.drawBitmap(b, source, rect, null);

//        int min = Math.min(w, h);
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setStrokeWidth(2);
//        paint.setColor(Color.RED);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawCircle(min / 2, min / 2, 50, paint);

        int z = Math.min(b.getWidth(), b.getHeight());
        final Rect rectSrc = new Rect(0, 0, z, z);
        final Rect rectDest = new Rect(0, 0, getWidth(), getHeight());


        Bitmap ok = getCircleBitmap(b, 12);
        canvas.drawBitmap(ok, rectSrc, rectDest, null);
    }

    private Bitmap getCircleBitmap(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        int min = Math.min(bitmap.getWidth(), bitmap.getHeight());
        final Rect rect = new Rect(0, 0, min, min);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
