package com.visitor.obria.yourapplication.camera;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.TextureView;
import android.widget.FrameLayout;

/**
 * created by yangshaojie  on 2018/9/5
 * email: ysjr-2002@163.com
 */
public class CameraTextureView extends TextureView implements TextureView.SurfaceTextureListener {

    private boolean isCameraInit;
    private boolean isViewInit;
    private CameraPreviewListener listener;
    private SurfaceTexture mSurface;

    public CameraTextureView(Context context) {
        super(context);
    }

    public CameraTextureView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSurfaceTextureListener(this);
    }

    public CameraTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        mSurface = surface;
        setLayoutParams(new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
        Matrix transform = new Matrix();
        transform.setTranslate(width, 0);
        transform.preScale(-1, 1);
        setTransform(transform);

        isViewInit = true;
        if (isCameraInit) {
            listener.onStartPreview();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    public void onPause() {
        isCameraInit = false;
    }

    public interface CameraPreviewListener {
        /**
         * 开始预览
         */
        void onStartPreview();
    }

    public void setListener(CameraPreviewListener listener) {

        this.listener = listener;
    }

    public void onCameraReady() {

        isCameraInit = true;
        if (isViewInit) {
            listener.onStartPreview();
        }
    }
}
