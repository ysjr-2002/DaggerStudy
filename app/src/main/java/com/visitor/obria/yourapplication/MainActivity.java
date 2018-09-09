package com.visitor.obria.yourapplication;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.visitor.obria.yourapplication.camera.CameraPreviewData;
import com.visitor.obria.yourapplication.cameraEx.CameraEx;
import com.visitor.obria.yourapplication.cameraEx.TextureViewEx;
import com.visitor.obria.yourapplication.model.Student;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements CameraEx.CameraListener {

    @BindView(R.id.myView)
    TextureViewEx myView;

    CameraEx cameraEx;
    @BindView(R.id.btn_take)
    Button btnTake;

    @Override
    void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    void onCreate() {

        cameraEx = new CameraEx();
        cameraEx.setTexture(myView);
    }

    @Override
    int getViewId() {
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
                Student student1 = new Student();
                student1.setName("杨绍杰");

                Gson gson = new Gson();
                String json = gson.toJson(student1);
                Toast.makeText(this, json, Toast.LENGTH_SHORT).show();

                Student student2 = gson.fromJson(json, Student.class);

                if (TextUtils.equals(student1.getName(), student2.getName())) {

                    String y = "";
                } else {
                    String x = "";
                }

                cameraEx.open(true);
                cameraEx.setListener(this);
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
}
