package com.visitor.tengli.webservicestudy.retrofitsport;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * created by yangshaojie  on 2019/6/5
 * email: ysjr-2002@163.com
 */
public interface ISport {

    @GET("/Vein/SCHWebService.asmx/GetContractInfo")
    Call<String> queryContract(@Query("BrandCode") String brandCode,
                               @Query("ClubID") String clubId,
                               @Query("Mobile") String mobile,
                               @Query("CardNo") String cardno,
                               @Query("Key") String key);

    @GET("/Vein/SCHWebService.asmx/GetContractInfo")
    Call<ContractResponse> queryContractWithxml(@Query("BrandCode") String brandCode,
                               @Query("ClubID") String clubId,
                               @Query("Mobile") String mobile,
                               @Query("CardNo") String cardno,
                               @Query("Key") String key);
}
