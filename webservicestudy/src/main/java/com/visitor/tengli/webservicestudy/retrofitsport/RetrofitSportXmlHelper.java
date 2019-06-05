package com.visitor.tengli.webservicestudy.retrofitsport;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitSportXmlHelper {
    private ISport mSport;

    public void build() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://host.gymparty.net:81")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        mSport = retrofit.create(ISport.class);
    }

    final String brandcode = "Demo";
    final String clubId = "b0b91277-690c-4c83-97eb-c5a6da46edad";
    final String mobile = "17357331892";
    final String key = "e052f3b0164b9beede9a6ef452c69019";

    public void getContract() {

        Call<ContractResponse> call = mSport.queryContractWithxml(brandcode, clubId, mobile, "", key);
        call.enqueue(new Callback<ContractResponse>() {
            @Override
            public void onResponse(Call<ContractResponse> call, Response<ContractResponse> response) {
                if (response.isSuccessful()) {
                    String shit = "";
                }
                if (response.code() == 200) {
                    String shit = "";
                }
                ContractResponse xml = response.body();
                String value =xml.Text;
                Gson gson = new Gson();
                List<ContractInfo> list = gson.fromJson(value, new TypeToken<List<ContractInfo>>() {
                }.getType());

                if (list != null && list.size() > 0) {

                    for (ContractInfo contract :
                            list) {

                        Log.d("ysj", String.format("id:%s MemberID:%s MemberName:%s CardNo:%s StartDate:%s EndDate:%s ContractStatus:%s",
                                contract.ysj,
                                contract.MemberID,
                                contract.MemberName,
                                contract.CardNo,
                                contract.StartDate,
                                contract.EndDate,
                                contract.ContractStatus == 1 ? "有效" : "欠款"));
                    }
                }
            }

            @Override
            public void onFailure(Call<ContractResponse> call, Throwable t) {

                String error = t.getMessage();
                String debug = "";
            }
        });
    }

}
