package com.visitor.tengli.webservicestudy.retrofitweather;

import com.google.gson.JsonObject;
import com.visitor.tengli.webservicestudy.retrofit.IUser;
import com.visitor.tengli.webservicestudy.retrofit.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * created by yangshaojie  on 2019/6/5
 * email: ysjr-2002@163.com
 */
public class RetrofitWeatherHelper {
    private IWeather mWeather;

    public void build() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.webxml.com.cn")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        mWeather = retrofit.create(IWeather.class);
    }

    public void getWeather() {

        Call<String> call = mWeather.queryWeatherBy("新乡");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    String shit = "";
                }
                String data = response.body();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                String error = t.getMessage();
                String debug = "";
            }
        });
    }
}
