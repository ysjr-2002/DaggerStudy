package com.visitor.obria.yourapplication.api;

import com.visitor.obria.yourapplication.bean.HSFaceBean;
import com.visitor.obria.yourapplication.response.HSResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

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

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.0.5:10001")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        api = retrofit.create(HSApiService.class);
    }

    public Call<HSResponse> postERP() {

        HSFaceBean bean = new HSFaceBean(100, "杨绍杰", "job101", "我是一个新兵");
        Call<HSResponse> responseCall = api.postERP(bean);
        return  responseCall;
    }
}
