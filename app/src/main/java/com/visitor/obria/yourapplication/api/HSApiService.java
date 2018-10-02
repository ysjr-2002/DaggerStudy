package com.visitor.obria.yourapplication.api;

import com.visitor.obria.yourapplication.bean.HSFaceBean;
import com.visitor.obria.yourapplication.response.HSResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HSApiService {

    @POST("/api/wg/check/")
    Call<HSResponse> postERP(@Body HSFaceBean bean);
}
