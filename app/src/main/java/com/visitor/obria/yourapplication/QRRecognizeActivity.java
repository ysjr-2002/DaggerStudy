package com.visitor.obria.yourapplication;

import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.qrcode.QRCodeReader;
import com.visitor.obria.yourapplication.camera.CameraManager;
import com.visitor.obria.yourapplication.camera.CameraPreviewData;
import com.visitor.obria.yourapplication.camera.CameraTextureView;
import com.visitor.obria.yourapplication.camera.YuvUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QRRecognizeActivity extends AppCompatActivity {

    @BindView(R.id.ct_preview)
    CameraTextureView ctPreview;
    CameraManager cameraManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrrecognize);
        ButterKnife.bind(this);
        initCamera();
    }

    private void initCamera() {

        cameraManager = new CameraManager(this);
        cameraManager.setListener(cameraListener);
        cameraManager.setTexture(ctPreview);
    }

    private CameraManager.CameraListener cameraListener = new CameraManager.CameraListener() {
        @Override
        public void onPictureTaken(CameraPreviewData data) {

            // Bitmap bitmap = YuvUtils.NV21ToBitmap(data);
            if (take) {
                long s = System.currentTimeMillis();
                YuvImage yuvImage = new YuvImage(data.nv21Data, ImageFormat.NV21, data.width, data.height, null);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(data.nv21Data.length);
                yuvImage.compressToJpeg(new Rect(0, 0, data.width, data.height), 100, byteArrayOutputStream);
                byte[] temp = byteArrayOutputStream.toByteArray();

                long c = System.currentTimeMillis() - s;
                Log.d("ysj", "time->" + c);

                try {
                    File file = new File(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
                    if (file.exists() == false) {
                        file.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(temp);
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            take = false;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        cameraManager.open(getWindowManager(), true, 1920, 1080);
    }

    boolean take = false;

    @OnClick(R.id.btn_take)
    public void onViewClicked() {
//        take = true;
        BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getResources().getDrawable(R.mipmap.qr);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        String content = YuvUtils.recognzieQR(bitmap);
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}
