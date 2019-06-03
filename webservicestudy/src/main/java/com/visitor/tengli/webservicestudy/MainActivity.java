package com.visitor.tengli.webservicestudy;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.textclassifier.TextClassification;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();


//        Go go = new Go();
//        go.execute("a");

        httpGet();
    }

    private void httpGet() {

        String host = "http://host.gymparty.net:82";
        String url = "/vein/schwebservice.asmx/CheckIn?BrandCode=&ClubID=&ContractID=&Mobile=&IdentityType=&DeviceName=&Key=";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(host + url).get().build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() != 200) {
                    Log.d("shit", "request error");
                    return;
                }


                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                try {
                    DocumentBuilder builder = factory.newDocumentBuilder();

                    Document document = builder.parse(response.body().byteStream());

                    NodeList nodeList = document.getElementsByTagName("int");
                    int len = nodeList.getLength();
                    Node node = nodeList.item(0);
                    String nodeval = node.getFirstChild().getNodeValue();

                    int val = Integer.parseInt(nodeval);
                    if (val == -2) {
                        Log.d("shit", "execute ok");
                    }

                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }
            }
        });

//<?xml version="1.0" encoding="utf-8"?>
//<int xmlns="http://tempuri.org/">-2</int>
    }

    class Go extends AsyncTask<String, Integer, Void> {


        @Override
        protected Void doInBackground(String... strings) {
            String key1 = strings[0];

            String url = "http://host.gymparty.net:81/vein/schwebservice.asmx?WSDL";
            String namespace = "http://tempuri.org";
            String methodname = "CheckIn";
            final String soapaction = namespace + "/" + methodname;

            SoapObject request = new SoapObject(namespace, methodname);
            request.addProperty("BrandCode", "1");
            request.addProperty("ClubID", "1");
            request.addProperty("ContractID", "1");
            request.addProperty("IdentityType", "1");
            request.addProperty("DeviceName", "闸机入场1");
            request.addProperty("Key", "1");

            //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
            final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
            envelope.bodyOut = request;//由于是发送请求，所以是设置bodyOut
            envelope.setOutputSoapObject(request);
            envelope.dotNet = true;//由于是.net开发的webservice，所以这里要设置为true

            final HttpTransportSE httpTransportSE = new HttpTransportSE(url);
            try {
                httpTransportSE.call(soapaction, envelope);//调用
                Object data = envelope.bodyIn;
                SoapSerializationEnvelope object = (SoapSerializationEnvelope) data;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }


    private void work() {

        String url = "http://www.webxml.com.cn/WebServices/WeatherWebService.asmx?WSDL";
        String namespace = "http://WebXml.com.cn";
        String methodname = "getWeatherbyCityName";
        final String soapaction = namespace + "/" + methodname;

        SoapObject request = new SoapObject(namespace, methodname);
        request.addProperty("theCityName", "新乡");

        //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
        envelope.bodyOut = request;//由于是发送请求，所以是设置bodyOut
        //envelope.setOutputSoapObject(request);
        envelope.dotNet = true;//由于是.net开发的webservice，所以这里要设置为true

        final HttpTransportSE httpTransportSE = new HttpTransportSE(url);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    httpTransportSE.call(soapaction, envelope);//调用
//                    SoapObject object = (SoapObject) envelope.bodyIn;
//                    String result = object.getProperty(0).toString();

                    Object data = envelope.bodyIn;
                    SoapSerializationEnvelope object = (SoapSerializationEnvelope) data;

//                    Object data= envelope.getResponse();
//                    SoapSerializationEnvelope object = (SoapSerializationEnvelope)data;
//                    SoapObject indata = (SoapObject) object.bodyIn;

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
