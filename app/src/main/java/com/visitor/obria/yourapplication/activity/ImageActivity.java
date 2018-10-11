package com.visitor.obria.yourapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.service.voice.VoiceInteractionSession;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.visitor.obria.yourapplication.R;

import java.io.IOException;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageActivity extends AppCompatActivity {

    @BindView(R.id.iv_big)
    ImageView ivBig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        boolean hasSD = hasSDcard();
        hideBottomUIMenu();

    }

    private void hideBottomUIMenu() {

        if (Build.VERSION.SDK_INT >= 19) {

            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            View view = getWindow().getDecorView();
            view.setSystemUiVisibility(uiOptions);
        }
    }

    public static boolean hasSDcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
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
                load();
                break;
            case R.id.btn_image2:
                MyTask task = new MyTask();
                task.execute();
                break;
            case R.id.btn_image3:
                asyncTaskTest();
                weak();
                break;
        }
    }

    private void weak() {
        String ysj = "sd";
        WeakReference<String> stringWeakReference = new WeakReference<>(ysj);
        String shit = stringWeakReference.get();
        if (shit == ysj) {
            String d = "";
        } else {
            String d = "";
        }

        if (TextUtils.equals(shit, ysj)) {
            String d = "";
        }
    }

    private void get() {

        Request request = new Request.Builder().get().url("").build();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient client = builder.build();
        Call call = client.newCall(request);

    }

    private void asyncTaskTest() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }
        }.execute();

        new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {

//                String data = strings[0];
//                int len = strings.length;
//                return data;

                class data {
                    public String username;
                    public String password;
                }


                data d = new data();
                d.username = "qudian@qq.com";
                d.password = "123456";

                final Gson gson = new Gson();
                String json = gson.toJson(d);
                //RequestBody body = RequestBody.create(MediaType.parse("application/json;utf-8"), json);
                FormBody body = new FormBody.Builder()
                        .add("username", d.username)
                        .add("password", d.password)
                        .build();
                final Request request = new Request.Builder()
                        .url("https://v2.koalacam.net/auth/login")
                        .post(body)
                        .addHeader("user-agent", "Koala Admin")
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .addInterceptor(new RetryInterceptor(4))
                        .build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                        String error = e.getMessage();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {

                        String shit = response.body().string();
                        koalaresponse koala = gson.fromJson(shit, koalaresponse.class);
                        if (koala != null) {

                            String session = response.header("Set-Cookie");
                            String debug = "";
                        }
                    }
                });

                return null;
            }
        }.execute("ysj");
    }

    class koalaresponse {
        private int code;
        private String desc;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public class RetryInterceptor implements Interceptor {

        public int maxRetry;//最大重试次数
        private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        public RetryInterceptor(int maxRetry) {
            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            System.out.println("retryNum=" + retryNum);
            Response response = chain.proceed(request);
            while (!response.isSuccessful() && retryNum < maxRetry) {
                retryNum++;
                System.out.println("retryNum=" + retryNum);
                response = chain.proceed(request);
            }
            return response;
        }
    }

    private class MyTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ivBig.setImageResource(R.mipmap.face1);
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            int x = 1;
            int a = 100;
            while (x < a) {
                x++;
                Log.d("cao", "" + x);
                publishProgress(x);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return a;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            ivBig.setVisibility(View.GONE);
            Toast.makeText(ImageActivity.this, "shit->" + integer, Toast.LENGTH_SHORT).show();
        }
    }
}
