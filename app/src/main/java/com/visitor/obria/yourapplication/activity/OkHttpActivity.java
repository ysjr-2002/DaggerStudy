package com.visitor.obria.yourapplication.activity;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.visitor.obria.yourapplication.R;
import com.visitor.obria.yourapplication.api.HSRetrofitHelper;
import com.visitor.obria.yourapplication.bean.StudenBean;
import com.visitor.obria.yourapplication.response.HSResponse;
import com.visitor.obria.yourapplication.util.CharUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpActivity extends BaseActivity {

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final String url = "http://192.168.3.54:10001/api/wg/check/";

    private final String TAG = "kaka";

    @Inject
    HSRetrofitHelper mHSRetrofitHelper;

    @Override
    protected int getViewId() {
        return R.layout.activity_ok_http;
    }

    @Override
    protected void create() {
        oktest();
    }

    @Override
    protected void initInject() {

        getActivityComponent().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHSRetrofitHelper.init();
    }

    @OnClick({R.id.btn_okhttp, R.id.btn_okretrofit, R.id.btn_block_test, R.id.btn_block_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_okhttp:
                oktest();
                break;
            case R.id.btn_okretrofit:
                postERP("test");
                break;
            case R.id.btn_block_test:
                ProductThread.start();
                okThread = new RecognizeThread();
                okThread.start();
                break;
            case R.id.btn_block_stop:
                stop();
                break;
        }
    }

    private void stop() {

        okThread.interrupt();
    }

    private Thread ProductThread = new Thread(new Runnable() {
        @Override
        public void run() {
            int i = 0;
            while (true) {
                block.offer(String.valueOf(i));

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    RecognizeThread okThread = null;
    BlockingQueue<String> block = new ArrayBlockingQueue<String>(5);

    private class RecognizeThread extends Thread {

        private boolean isInterrupted;

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted() && !isInterrupted) {
                    String data = block.take();
                    //printDate("start");
                    blockTest(data);
                    //printDate("end");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void interrupt() {
            isInterrupted = true;
            super.interrupt();
        }
    }

    private void printDate(String prefix) {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String time = prefix + ":" + df.format(date);
        Log.d(TAG, time);
    }

    private void blockTest(String data) {

        postERP(data);
    }

    private void postERP(String name) {

        retrofit2.Call<HSResponse> call = mHSRetrofitHelper.postERP(name);
        call.enqueue(new retrofit2.Callback<HSResponse>() {
            @Override
            public void onResponse(retrofit2.Call<HSResponse> call, retrofit2.Response<HSResponse> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    HSResponse hsResponse = response.body();
                    int code = hsResponse.getCode();
                    String message = hsResponse.getMessage();
                    printDate("back");
                }
            }

            @Override
            public void onFailure(retrofit2.Call<HSResponse> call, Throwable t) {

                String error = t.getMessage();
                if (error == null) {
                    error = "错误";
                }
                Log.d(TAG, error);
            }
        });
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
