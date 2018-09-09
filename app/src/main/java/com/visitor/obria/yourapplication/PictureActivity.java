package com.visitor.obria.yourapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureActivity extends AppCompatActivity {

    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        Intent bundle = this.getIntent();
        String path = bundle.getStringExtra("path");
        int len = bundle.getIntExtra("len", 0);
        byte[] data = new byte[len];

        try {
            FileInputStream fis = new FileInputStream(path);
            fis.read(data);

            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            long s = System.currentTimeMillis();
            Bitmap temp = BitmapFactory.decodeByteArray(data, 0, data.length);
            Log.d("ok1", (System.currentTimeMillis() - s) + "ms");
            s = System.currentTimeMillis();
            Bitmap bitmap = Bitmap.createBitmap(temp, 0, 0, temp.getWidth(), temp.getHeight(), matrix, true);
            Log.d("ok1", (System.currentTimeMillis() - s) + "ms");
            s = System.currentTimeMillis();
            temp.recycle();
            Log.d("ok1", (System.currentTimeMillis() - s) + "ms");

            img.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
