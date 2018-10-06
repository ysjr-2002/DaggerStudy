package com.visitor.obria.yourapplication.api;

import com.visitor.obria.yourapplication.bean.HSFaceBean;
import com.visitor.obria.yourapplication.response.HSResponse;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class HSRetrofitHelper {

    HSApiService api;

    @Inject
    public HSRetrofitHelper() {

    }

    public void init() {

        OkHttpClient.Builder okbuilder = new OkHttpClient.Builder().readTimeout(2, TimeUnit.SECONDS);
        OkHttpClient client = okbuilder.build();

        Retrofit.Builder builder = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://192.168.0.5:10001")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        api = retrofit.create(HSApiService.class);
    }

    public Call<HSResponse> postERP(String name) {

        HSFaceBean bean = new HSFaceBean(100, name, "job101", "我是一个新兵");
        Call<HSResponse> responseCall = api.postERP(bean);
        return  responseCall;
    }
}
