package com.visitor.tengli.webservicestudy.retrofitsport;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * created by yangshaojie  on 2019/6/5
 * email: ysjr-2002@163.com
 */
public class RetrofitSportHelper {
    private ISport mSport;

    public void build() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://host.gymparty.net:81")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        mSport = retrofit.create(ISport.class);
    }

    final String brandcode = "Demo";
    final String clubId = "b0b91277-690c-4c83-97eb-c5a6da46edad";
    final String mobile = "17357331892";
    final String key = "e052f3b0164b9beede9a6ef452c69019";

    public void getContract() {

        Call<String> call = mSport.queryContract(brandcode, clubId, mobile, "", key);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    String shit = "";
                }
                if (response.code() == 200) {
                    String shit = "";
                }

                String xml = response.body();
                parse(xml);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

                String error = t.getMessage();
                String debug = "";
            }
        });
    }

    private void parse(String xml) {

//        StringReader stringReader = new StringReader(xml);
//        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
//            DocumentBuilder builder = factory.newDocumentBuilder();
//
//            byte[] bytes = xml.getBytes("utf-8");
//            InputStream inputStream = new ByteArrayInputStream(bytes);
//            Document document = builder.parse(inputStream);
//
//            NodeList nodeList = document.getElementsByTagName("string");
//            int count = nodeList.getLength();
//
//            Node node = nodeList.item(0);
//            String value = node.getFirstChild().getNodeValue();

            xml = xml.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>", "");
            xml = xml.replace("</xml>", "");
            xml= xml.replace("\r\n",  "");
            String value = xml.replace("<string xmlns=\"http://tempuri.org/\">", "").replace("</string>", "");

            Gson gson = new Gson();
            List<ContractInfo> list = gson.fromJson(value, new TypeToken<List<ContractInfo>>() {
            }.getType());


            if (list != null && list.size() > 0) {

                for (ContractInfo contract :
                        list) {

                    Log.d("ok", String.format("id:%s MemberID:%s MemberName:%s CardNo:%s StartDate:%s EndDate:%s ContractStatus:%s",
                            contract.ysj,
                            contract.MemberID,
                            contract.MemberName,
                            contract.CardNo,
                            contract.StartDate,
                            contract.EndDate,
                            contract.ContractStatus == 1 ? "有效" : "欠款"));
                }
            }

//        } catch (ParserConfigurationException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (SAXException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            ;
        }
    }
}
