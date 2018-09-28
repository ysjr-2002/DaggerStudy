package com.visitor.obria.yourapplication.activity;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TimeUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.common.StringUtils;
import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.bean.StudenBean;
import com.visitor.obria.yourapplication.util.CharUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends AppCompatActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final String url = "http://192.168.3.54:10001/api/wg/check/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);

        charTest();
        oktest();
    }

    private void charTest() {

        byte a = (byte) 'a';
        byte b = (byte) 'A';
        byte c = (byte) '1';
        byte d = (byte) '9';

        String e = "abcd";
        try {
            byte[] ee = e.getBytes("ascii");
            byte[] ff = e.getBytes();
            byte[] gg = e.getBytes("utf-8");
            String shit = "";
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        String hex = CharUtil.intToHex(10);
        int hex_i = Integer.parseInt("0a",16);
        String stop = "";
    }

    private void oktest() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new RetryInterceptor(10));
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(20, TimeUnit.SECONDS);
        httpClient.writeTimeout(20, TimeUnit.SECONDS);
        httpClient.retryOnConnectionFailure(true);
        OkHttpClient client = httpClient.build();

        StudenBean bean = new StudenBean();
        bean.setName("ysj");

        Gson gson = new Gson();
        final String json = gson.toJson(bean);
        RequestBody requestBody = RequestBody.create(JSON, json);

        final Request request = new Request.Builder().url(url).post(requestBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String error = e.getMessage();
                myToast(error);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200) {
                    String message = response.message();
                    final String body = response.body().string();
                    myToast(body);
                }
                if (response.isSuccessful()) {

                }
            }
        });
    }

    private void myToast(final String msg) {
        if (Looper.getMainLooper() == Looper.myLooper()) {

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        } else

        {
            OkHttpActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myToast(msg);
                }
            });
        }
    }

    public class RetryInterceptor implements Interceptor {

        private int maxRetry = 0; //最大重试次数
        private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

        public RetryInterceptor(int maxRetry) {

            this.maxRetry = maxRetry;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request request = chain.request();
            System.out.println("retryNum=" + retryNum);
            Response response = chain.proceed(request);
            while (response.isSuccessful() == false && retryNum < maxRetry) {
                retryNum++;
                System.out.println("retryNum=" + retryNum);
                response = chain.proceed(request);
            }
            return response;
        }
    }

}