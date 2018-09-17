package com.visitor.obria.yourapplication.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("www.baidu.com")
    Call<ResponseBody> get();
}
