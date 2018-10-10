package com.visitor.obria.yourapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.visitor.obria.yourapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_big)
    ImageView ivBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        load();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // 原图片的宽高
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;


            // 计算inSampleSize值
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    private void load() {

        int w = ivBig.getWidth();
        int h = ivBig.getHeight();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.qh, options);


        int imgWidth = options.outWidth;
        int imgHeight = options.outHeight;
        String imageType = options.outMimeType;

        int sampleSize = calculateInSampleSize(options, 400, 400);

        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.qh, options);
        ivBig.setImageBitmap(bitmap);
    }

    @OnClick({R.id.btn_image1, R.id.btn_image2, R.id.btn_image3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_image1:
                break;
            case R.id.btn_image2:
                break;
            case R.id.btn_image3:
                break;
        }
    }
}
