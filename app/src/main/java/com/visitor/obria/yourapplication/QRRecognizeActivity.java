package com.visitor.obria.yourapplication;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.visitor.obria.yourapplication.camera.CameraManager;
import com.visitor.obria.yourapplication.camera.CameraPreviewData;
import com.visitor.obria.yourapplication.camera.CameraTextureView;
import com.visitor.obria.yourapplication.camera.YuvUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRRecognizeActivity extends AppCompatActivity {

    @BindView(R.id.ct_preview)
    CameraTextureView ctPreview;
    CameraManager cameraManager;

    BlockingQueue<CameraPreviewData> mFrameQueue = new ArrayBlockingQueue<>(3);
    @BindView(R.id.capture_scan_line)
    ImageView captureScanLine;
    Button btnTake;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.tv_code)
    TextView tvCode;
    private TranslateAnimation mFrameTranslate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrrecognize);
        ButterKnife.bind(this);
        initCamera();

        initLayoutAnimation();
    }

    private void initLayoutAnimation() {
        mFrameTranslate = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f);
        mFrameTranslate.setDuration(3000);
        mFrameTranslate.setRepeatCount(-1);
        mFrameTranslate.setRepeatMode(Animation.RESTART);
    }

    private void initCamera() {

        cameraManager = new CameraManager(this);
        cameraManager.setListener(cameraListener);
        cameraManager.setTexture(ctPreview);
    }

    String qrcode = "";
    private CameraManager.CameraListener cameraListener = new CameraManager.CameraListener() {
        @Override
        public void onPictureTaken(CameraPreviewData data) {
//
            mFrameQueue.offer(data);
//            if (take) {
//                long s = System.currentTimeMillis();
//                YuvImage yuvImage = new YuvImage(data.nv21Data, ImageFormat.NV21, data.width, data.height, null);
//                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.nv21Data.length);
//                yuvImage.compressToJpeg(new Rect(0, 0, data.width, data.height), 100, byteArrayOutputStream);
//                byte[] temp = byteArrayOutputStream.toByteArray();
//
//                long c = System.currentTimeMillis() - s;
//                Log.d("ysj", "time->" + c);
//
//                try {
//                    File file = new File(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
//                    if (file.exists() == false) {
//                        file.createNewFile();
//                    }
//                    FileOutputStream fos = new FileOutputStream(file);
//                    fos.write(temp);
//                    fos.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            take = false;
        }
    };

    private Thread mThreadCompressImage = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    CameraPreviewData data = mFrameQueue.take();
                    Bitmap bitmap = YuvUtils.NV21ToBitmap(data);
                    if (bitmap != null) {
                        String content = YuvUtils.recognzieQR(bitmap);
                        if (TextUtils.isEmpty(content) == false) {
                            String shit = "";
                            if (TextUtils.equals(qrcode, content) == false) {
                                qrcode = content;
                                Log.d("test", "code->" + content);

                                QRRecognizeActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        tvCode.setText(qrcode);
                                    }
                                });
                            }
                        }
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager.open(getWindowManager(), true, 1920, 1080);
        mThreadCompressImage.start();
        captureScanLine.startAnimation(mFrameTranslate);
    }

    boolean take = false;

//    @OnClick(R.id.btn_take)
//    public void onViewClicked() {
////        take = true;
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.mipmap.qr);
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//        String content = YuvUtils.recognzieQR(bitmap);
//        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
//    }
}
