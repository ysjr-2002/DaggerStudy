package com.visitor.tengli.webservicestudy.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * created by yangshaojie  on 2019/6/4
 * email: ysjr-2002@163.com
 */
public class RetrofitUserHelper {

    private IUser user;

    public void build() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.3.54:9988/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        user = retrofit.create(IUser.class);
    }

    public void getUser() {

        Call<UserResponse> call = user.test(20, "ysj");
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if (response.isSuccessful()) {
                    String shit = "";
                }

                UserResponse data = response.body();
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }
}
