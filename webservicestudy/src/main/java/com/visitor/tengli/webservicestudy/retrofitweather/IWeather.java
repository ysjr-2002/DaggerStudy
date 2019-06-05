package com.visitor.tengli.webservicestudy.retrofitweather;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * created by yangshaojie  on 2019/6/5
 * email: ysjr-2002@163.com
 */
public interface IWeather {

    @GET("WebServices/WeatherWebService.asmx/getWeatherbyCityName")
    Call<String> queryWeatherBy(@Query("theCityName") String cityname);
}
