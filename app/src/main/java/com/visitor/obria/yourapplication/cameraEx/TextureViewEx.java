package com.visitor.obria.yourapplication.cameraEx;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

public class TextureViewEx extends TextureView implements TextureView.SurfaceTextureListener {
    public TextureViewEx(Context context) {
        super(context);
    }

    public TextureViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSurfaceTextureListener(this);
    }

    public TextureViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextureViewEx(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private SurfaceTexture mSurfaceTexture;

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        mSurfaceTexture = surface;
        Matrix matrix = new Matrix();
        matrix.setTranslate(1920, 0);
        matrix.preScale(-1, 1);
        setTransform(matrix);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        Log.d("size", String.format("w d% h d%", width, height));
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
