package com.visitor.obria.yourapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.visitor.obria.yourapplication.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FaceUploadActivity extends AppCompatActivity {

    @BindView(R.id.imageView1)
    ImageView imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_upload);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button2)
    public void onViewClicked() {

        String url = "http://192.168.3.54:10001/api/card/getface/";
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(3000, TimeUnit.MICROSECONDS);
        OkHttpClient okHttpClient = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                String error = e.getMessage();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.isSuccessful()) {

                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    FaceUploadActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView1.setImageBitmap(bitmap);
                            //Toast.makeText(FaceUploadActivity.this, content, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
