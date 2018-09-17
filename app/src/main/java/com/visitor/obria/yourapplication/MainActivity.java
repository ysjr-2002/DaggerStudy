package com.visitor.obria.yourapplication;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.visitor.obria.yourapplication.camera.CameraPreviewData;
import com.visitor.obria.yourapplication.cameraEx.CameraEx;
import com.visitor.obria.yourapplication.cameraEx.TextureViewEx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements CameraEx.CameraListener {

    @BindView(R.id.myView)
    TextureViewEx myView;

    CameraEx cameraEx;
    @BindView(R.id.btn_take)
    Button btnTake;
    @BindView(R.id.faceview)
    FaceView faceview;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void create() {

        cameraEx = new CameraEx();
        cameraEx.setTexture(myView);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    boolean bTake = false;

    @OnClick({R.id.button, R.id.btn_take})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.button:
                cameraEx.open(true);
                cameraEx.setListener(this);
                cameraEx.setFaceView(faceview);
                cameraEx.startPreview();
                break;
            case R.id.btn_take:
                bTake = true;
                break;
        }
    }

    @Override
    public void onPictureTaken(CameraPreviewData data) {

        if (bTake) {
            bTake = false;
            int width = data.width;
            int height = data.height;
            YuvImage yuvImage = new YuvImage(data.nv21Data, ImageFormat.NV21, width, height, null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, width, height), 80, byteArrayOutputStream);
            byte[] jpegData = byteArrayOutputStream.toByteArray();

            try {
                File file = new File("/sdcard/kk.jpg");
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(jpegData);
                String path = file.getAbsolutePath();
                Intent intent = new Intent(this, PictureActivity.class);
                intent.putExtra("path", path);
                intent.putExtra("len", jpegData.length);
                startActivity(intent);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
