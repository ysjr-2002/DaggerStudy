package com.visitor.obria.yourapplication.activity;

import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
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

    @OnClick({R.id.btn_okhttp, R.id.btn_okretrofit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_okhttp:
                oktest();
                break;
            case R.id.btn_okretrofit:
                postERP();
                break;
        }
    }

    private void postERP() {

        retrofit2.Call<HSResponse> call = mHSRetrofitHelper.postERP();
        call.enqueue(new retrofit2.Callback<HSResponse>() {
            @Override
            public void onResponse(retrofit2.Call<HSResponse> call, retrofit2.Response<HSResponse> response) {

                if (response.isSuccessful() && response.code() == 200) {

                    HSResponse hsResponse = response.body();
                    int code= hsResponse.getCode();
                    String message = hsResponse.getMessage();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<HSResponse> call, Throwable t) {

                String error = t.getMessage();
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
