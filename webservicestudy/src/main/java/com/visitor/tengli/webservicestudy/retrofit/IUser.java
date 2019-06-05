package com.visitor.tengli.webservicestudy.retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * created by yangshaojie  on 2019/6/4
 * email: ysjr-2002@163.com
 */
public interface IUser {
    @GET("test/")
    Call<UserResponse> queryByparam(@Query("userid") int id, @Query("name") String name);

    @GET("test/{id}")
    Call<JsonObject> queryBypath(@Path("id")int id);
}
