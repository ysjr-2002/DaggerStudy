package com.visitor.obria.yourapplication.camera;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.io.IOException;
import java.util.List;

/**
 * created by yangshaojie  on 2018/9/5
 * email: ysjr-2002@163.com
 */
@SuppressWarnings("ALL")
public class CameraManager  {

    private Context context;
    private boolean front;
    private int cameraId = -1;
    private int previewDegreen = 0;
    private int manualWidth;
    private int manualHeight;
    private Camera camera;
    private Camera.Size previewSize;
    private CameraState state = CameraState.IDEL;
    private CameraListener listener;
    private CameraTextureView mCameraTextureView;
    private SurfaceHolder surfaceHolder;
    private SurfaceTexture mSurfaceTexture;

    public CameraManager(Context context) {

        this.context = context;
    }

    public void setListener(CameraListener listener) {
        this.listener = listener;
    }

    public void setTexture(CameraTextureView cameraTextureView) {
        this.mCameraTextureView = cameraTextureView;
        this.mCameraTextureView.setListener(mCameraPreviewListener);
    }

    private CameraTextureView.CameraPreviewListener mCameraPreviewListener = new CameraTextureView.CameraPreviewListener() {
        @Override
        public void onStartPreview() {
//            mSurfaceTexture = mCameraTextureView.getSurfaceTexture();
//            try {
//                camera.setPreviewTexture(mSurfaceTexture);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    };

    public boolean open(WindowManager windowManager, boolean front, int width, int height) {

        this.manualWidth = width;
        this.manualHeight = height;
        this.front = front;
        return open(windowManager);
    }

    private boolean open(final WindowManager windowManager) {
        if (state != CameraState.OPENING) {
            state = CameraState.OPENING;

            release();

            new AsyncTask<Object, Object, Object>() {
                @Override
                protected Object doInBackground(Object... objects) {

                    return openCamera(windowManager);
                }
            }.execute();

            return true;
        } else
            return false;

    }

    private Object openCamera(WindowManager windowManager) {
        cameraId = front ? Camera.CameraInfo.CAMERA_FACING_FRONT : Camera.CameraInfo.CAMERA_FACING_BACK;
        try {
            camera = Camera.open(cameraId);
        } catch (Exception ex) {
            int count = Camera.getNumberOfCameras();
            if (count > 0) {
                cameraId = 0;
                camera = Camera.open(cameraId);
            } else {
                cameraId = -1;
                camera = null;
            }
        }
        if (camera != null) {

            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(cameraId, info);
            int rotation = windowManager.getDefaultDisplay().getRotation();
            int degrees = 0;
            switch (rotation) {
                case Surface.ROTATION_0:
                    degrees = 0;
                    break;
                case Surface.ROTATION_90:
                    degrees = 90;
                    break;
                case Surface.ROTATION_180:
                    degrees = 180;
                    break;
                case Surface.ROTATION_270:
                    degrees = 270;
                    break;
                default:
                    break;
            }
            int previewRotation;
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                previewRotation = (info.orientation + degrees) % 360;
                previewRotation = (360 - previewRotation) % 360;
            } else {  // back-facing
                previewRotation = (info.orientation - degrees + 360) % 360;
            }
            previewRotation = 0;
            camera.setDisplayOrientation(previewRotation + 90);

            //设置参数
            Camera.Parameters param = camera.getParameters();
            printSuportPreviewSize(param);

            param.setFocusMode( Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            if (this.manualWidth > 0 && this.manualHeight > 0) {
                param.setPreviewSize(manualWidth, manualHeight);
            }
            param.setPreviewFormat(ImageFormat.NV21);
            camera.setParameters(param);

            PixelFormat pixelInfo = new PixelFormat();
            int pixelFormat = camera.getParameters().getPreviewFormat();
            PixelFormat.getPixelFormatInfo(pixelFormat, pixelInfo);
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size sz = parameters.getPreviewSize();
            previewSize = sz;

            int bufSize = sz.width * sz.height * pixelInfo.bitsPerPixel / 8;
            camera.addCallbackBuffer(new byte[bufSize]);
            camera.startPreview();

            camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {

                    if (listener != null) {
                        listener.onPictureTaken(
                                new CameraPreviewData(data.clone(), previewSize.width, previewSize.height, previewDegreen, front)
                        );
                    }
                    camera.addCallbackBuffer(data);
                }
            });

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {

                    if (mCameraTextureView != null) {
                        mCameraTextureView.onCameraReady();
                    }
                }
            });
        }
        return null;
    }

    private void printSuportPreviewSize(Camera.Parameters param) {
        List<Camera.Size> sizes = param.getSupportedPreviewSizes();
        for (Camera.Size size: sizes
             ) {
            Log.d("ysj", String.format( "w %d h %d", size.width, size.height));
        }
    }

    private void release() {

        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
    }

    public enum CameraState {
        IDEL,
        OPENING,
        OPENED
    }

    public interface CameraListener {
        /**
         * 预览返回
         *
         * @param cameraPreviewData
         */
        void onPictureTaken(CameraPreviewData cameraPreviewData);
    }
}
