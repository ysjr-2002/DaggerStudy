package com.visitor.obria.yourapplication.cameraEx;

import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.TextureView;

import com.visitor.obria.yourapplication.camera.CameraPreviewData;

import java.io.IOException;

@SuppressWarnings("ALL")
public class CameraEx {

    private Camera camera;
    private TextureView textureView;
    private CameraListener listener;
    private Camera.Size previewSize = null;

    public void open(boolean front) {

        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        try {
            Camera.Parameters param = camera.getParameters();
            param.setPreviewSize(1920, 1080);
            param.setPreviewFormat(ImageFormat.NV21);
            camera.setParameters(param);
            camera.setDisplayOrientation(90);
            camera.setPreviewTexture(textureView.getSurfaceTexture());

            PixelFormat pixelinfo = new PixelFormat();
            int pixelformat = camera.getParameters().getPreviewFormat();
            PixelFormat.getPixelFormatInfo(pixelformat, pixelinfo);
            Camera.Parameters parameters = camera.getParameters();
            Camera.Size sz = parameters.getPreviewSize();
            previewSize = sz;
            int bufSize = sz.width * sz.height * pixelinfo.bitsPerPixel / 8;
            camera.addCallbackBuffer(new byte[bufSize]);

            camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera temp) {

                    Log.d("shit", "buffer->" + data.length + "");

                    if (listener != null) {
                        CameraPreviewData previewData = new CameraPreviewData(data, previewSize.width, previewSize.height, 0, true);
                        listener.onPictureTaken(previewData);
                    }
                    temp.addCallbackBuffer(data);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startPreview() {
        camera.startPreview();
    }

    public void setTexture(TextureView textureView) {

        this.textureView = textureView;
    }

    public void setListener(CameraListener listener) {
        this.listener = listener;
    }

    public void release() {
        if (camera != null) {
            camera.stopPreview();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        }
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
