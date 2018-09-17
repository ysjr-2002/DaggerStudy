package com.visitor.obria.yourapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.visitor.obria.yourapplication.R;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * created by yangshaojie  on 2018/9/17
 * email: ysjr-2002@163.com
 */
public class MatrixActivity extends AppCompatActivity {

    @BindView(R.id.btn_translate)
    Button btnTranslate;
    @BindView(R.id.btn_rotate)
    Button btnRotate;
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matrix);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_translate, R.id.btn_rotate, R.id.btn_scale})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_translate:
                dotranslate();
                break;
            case R.id.btn_rotate:
                dorotate();
                break;
            case R.id.btn_scale:
                doscale();
                break;
        }
    }

    private void doscale() {

        Random random = new Random();
        int x = random.nextInt(5);
        int y = random.nextInt(5);
        if (x == 0) {
            x = 1;
        }
        if (y == 0) {
            y = 1;
        }

        angle += 90;
        Matrix matrix = new Matrix();
        matrix.preScale(x, y);
        matrix.preRotate(angle);
        Bitmap bitmapSource = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        Bitmap bitmapDest = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), matrix, true);

        img.setImageBitmap(bitmapDest);
        if (bitmapSource != bitmapDest && bitmapSource.isRecycled() == false) {
            bitmapSource.recycle();
        }
    }

    float angle = 0f;

    private void dorotate() {

        angle += 90;

        Matrix matrix = new Matrix();
        matrix.setRotate(angle,90,90);
        Bitmap bitmapSource = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        Bitmap bitmapDest = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), matrix, true);

        img.setImageBitmap(bitmapDest);
        if (bitmapSource != bitmapDest && bitmapSource.isRecycled() == false) {
            bitmapSource.recycle();
        }

//        if (bitmapDest.isRecycled() == false) {
//            bitmapDest.recycle();
//        }

        if (angle == 360f) {
            angle = 0f;
        }
    }

    private void dotranslate() {

        Matrix matrix = new Matrix();
        matrix.setTranslate(200, 50);
        Bitmap bitmapSource = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher_round);
        Bitmap bitmapDest = Bitmap.createBitmap(bitmapSource, 0, 0, bitmapSource.getWidth(), bitmapSource.getHeight(), matrix, true);

        img.setImageBitmap(bitmapDest);
        if (bitmapSource != bitmapDest && bitmapSource.isRecycled() == false) {
            bitmapSource.recycle();
        }
    }
}
