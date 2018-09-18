package com.visitor.obria.yourapplication.cameraEx;

import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.hardware.Camera;
import android.util.Log;
import android.util.Size;
import android.view.TextureView;

import com.visitor.obria.yourapplication.activity.FaceView;
import com.visitor.obria.yourapplication.camera.CameraPreviewData;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class CameraEx implements Camera.FaceDetectionListener {

    private Camera camera;
    private TextureView textureView;
    private CameraListener listener;
    private Camera.Size previewSize = null;

    private int max_faces = 0;

    public void open(boolean front) {

        if (front) {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        } else {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        }
        try {
            Camera.Parameters param = camera.getParameters();

            List<Camera.Size> test = param.getSupportedPreviewSizes();
            for (Camera.Size temp :
                    test) {
                Log.d("shit", temp.width + " " + temp.height);
            }
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

            max_faces = param.getMaxNumDetectedFaces();
            if (max_faces > 0) {
                camera.setFaceDetectionListener(this);
            }

//            camera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
//                @Override
//                public void onPreviewFrame(byte[] data, Camera temp) {
//                    Log.d("shit", "buffer->" + data.length + "");
//                    if (listener != null) {
//                        CameraPreviewData previewData = new CameraPreviewData(data, previewSize.width, previewSize.height, 0, true);
//                        listener.onPictureTaken(previewData);
//                    }
//                    temp.addCallbackBuffer(data);
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startPreview() {
        camera.startPreview();

        if (max_faces > 0) {
            camera.startFaceDetection();
        }
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

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {

        for (Camera.Face face :
                faces) {

            float score = face.score;
            Log.d("ysj", "score->" + score);
            Log.d("ysj", "x=" + face.rect.left + " y =" + face.rect.top + " l=" + face.rect.right + " b=" + face.rect.bottom);
        }

        mFaceView.setFaces(transForm(faces));
    }

    private List<RectF> transForm(Camera.Face[] faces) {

        Matrix matrix = new Matrix();
        matrix.setScale(-1f, 1f);
        matrix.postRotate(90f);
        matrix.postScale(mFaceView.getWidth() / 2000f, mFaceView.getHeight() / 2000f);
        matrix.postTranslate(mFaceView.getWidth() / 2f, mFaceView.getHeight() / 2f);

        List<RectF> temp = new ArrayList<>();
        for (Camera.Face face :
                faces) {

            RectF srcRect = new RectF(face.rect);
            RectF dstRect = new RectF(0f, 0f, 0f, 0f);
            matrix.mapRect(dstRect, srcRect);
            temp.add(dstRect);
        }
        return temp;
    }


    private FaceView mFaceView;

    public void setFaceView(FaceView view) {

        this.mFaceView = view;
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
